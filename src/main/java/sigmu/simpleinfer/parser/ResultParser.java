// Original code by Friedrich Hudinjan of "Infer Integration"

package sigmu.simpleinfer.parser;

import sigmu.simpleinfer.model.InferBug;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;

public class ResultParser {
    private static final Logger log = Logger.getInstance(ResultParser.class);

    private Map<String, List<InferBug>> bugsMap;
    private List<InferBug> bugs;
    private PropertyChangeSupport changes = new PropertyChangeSupport( this );

    public ResultParser() {
    }

    public static ResultParser getInstance(@NotNull Project project) {
        return ServiceManager.getService(project, ResultParser.class);
    }

    /**
     * Parses a given infer result file, and saves it in the {@link ResultParser}. The result file has 'report.json' as the default name. The Infer ToolWindow is notified, so it can show the parsed results.
     * @param resultPath The Path of the file in the json format
     * @return A Map of the rearranged Results. Mainly for testing purposes. Is null when the file doesnt exist.
     */
    @Nullable
    public Map<String, List<InferBug>> parse(Path resultPath) {
        if(!Files.exists(resultPath)) {
            log.warn("report.json does not exist - aborting parsing");
            return null;
        }
        try {
            rearrangeBugList(readBugList(resultPath));
            return getBugsMap();
        } catch (IOException e) {
            log.error("Could not parse given result file", e);
        } catch(JsonSyntaxException e) {
            log.error("Invalid JSON Syntax in Infer report file");
        }
        return null;
    }

    public Map<String, List<InferBug>> parseByBugType(Path resultPath) {
        if(!Files.exists(resultPath)) {
            log.warn("report.json does not exist - aborting parsing");
            return null;
        }
        try {
            rearrangeBugListByBugType(readBugList(resultPath));
            return getBugsMap();
        } catch (IOException e) {
            log.error("Could not parse given result file", e);
        } catch(JsonSyntaxException e) {
            log.error("Invalid JSON Syntax in Infer report file");
        }
        return null;
    }

    /**
     * Reads a report.json from an Infer analysis and deserializes it into a list of InferBug objects
     * @param jsonPath The Path of the report.json
     * @return A list of InferBugs
     * @throws IOException If the json file couldn't be read
     */
    private List<InferBug> readBugList(Path jsonPath) throws IOException, JsonSyntaxException {
        final String json = new String(Files.readAllBytes(jsonPath));
        final Type targetClassType = new TypeToken<ArrayList<InferBug>>(){}.getType();
        Collection<InferBug> targetCollection = new Gson().fromJson(json, targetClassType);
        return new ArrayList<>(targetCollection);
    }

    /**
     * Rearranges the buglist from the format infer delivers to a map with the filenames as keys and a list of bugs from that file as value
     * @param bugList The buglist, deserialized from the infer report.json
     */
    private void rearrangeBugList(List<InferBug> bugList) {
        Map<String, List<InferBug>> map = new HashMap<>();
        for(InferBug bug : bugList) {
            if(map.containsKey(bug.getFileName())) {
                map.get(bug.getFileName()).add(bug);
            }
            else {
                final List<InferBug> list = new ArrayList<>();
                list.add(bug);
                map.put(bug.getFileName(), list);
            }
        }

        setBugsMap(map);
    }

    private void rearrangeBugListByBugType(List<InferBug> bugList) {
        Map<String, List<InferBug>> map = new HashMap<>();
        for(InferBug bug : bugList) {
            if(map.containsKey(bug.getBugType())) {
                map.get(bug.getBugType()).add(bug);
            }
            else {
                final List<InferBug> list = new ArrayList<>();
                list.add(bug);
                map.put(bug.getBugType(), list);
            }
        }

        setBugsMap(map);
    }

    public Map<String, List<InferBug>> getBugsMap() {
        return bugsMap;
    }

    private void setBugsMap(Map<String, List<InferBug>> bugsMap) {
        Map<String, List<InferBug>> oldMap = this.bugsMap;
        this.bugsMap = bugsMap;
        changes.firePropertyChange("bugsMap", oldMap, bugsMap);
    }

    private List<InferBug> getBugs() {
        return bugs;
    }

    private void setBugs(List<InferBug> bugs) {
        this.bugs = bugs;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        changes.removePropertyChangeListener(l);
    }
}
