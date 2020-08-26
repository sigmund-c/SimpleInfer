package sigmu.simpleinfer.parser;

import sigmu.simpleinfer.model.InferBug;
import sigmu.simpleinfer.model.InferBugFix;

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

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;

public class FixParser {
    private static final Logger log = Logger.getInstance(FixParser.class);

    private Map<String, List<InferBugFix>> bugFixesMap;
    private List<InferBugFix> bugFixes;

    public FixParser() {
    }

    public static FixParser getInstance(@NotNull Project project) {
        return ServiceManager.getService(project, FixParser.class);
    }

    public Map<String, List<InferBugFix>> parse(Path fixesPath) {
        if (!Files.exists(fixesPath)) {
            log.warn("fixes json does not exist - aborting parsing fixes");
            return null;
        }
        try {
            arrangeFixesByHash(readBugList(fixesPath));
            return getFixesMap();
        } catch (IOException e) {
            log.error("Could not parse the given fixes file", e);
        } catch (JsonSyntaxException e) {
            log.error("Invalid JSON Syntax in fixes file");
        }
        return null;
    }

    private List<InferBugFix> readBugList(Path jsonPath) throws IOException, JsonSyntaxException {
        final String json = new String(Files.readAllBytes(jsonPath));
        final Type targetClassType = new TypeToken<ArrayList<InferBugFix>>(){}.getType();
        Collection<InferBugFix> targetCollection = new Gson().fromJson(json, targetClassType);

        this.bugFixes = new ArrayList<>(targetCollection);
        return this.bugFixes;
    }

    private void arrangeFixesByHash(List<InferBugFix> fixesList) {
        Map<String, List<InferBugFix>> map = new HashMap<>();
        for (InferBugFix fix: fixesList) {
            if(map.containsKey(fix.getHash())) {
                map.get(fix.getHash()).add(fix);
            } else {
                final List<InferBugFix> list = new ArrayList<>();
                list.add(fix);
                map.put(fix.getHash(), list);
            }
        }

        setFixesMap(map);
    }

    public Map<String, List<InferBugFix>> getFixesMap() {
        return bugFixesMap;
    }

    private void setFixesMap(Map<String, List<InferBugFix>> fixesMap) {
        this.bugFixesMap = fixesMap;
    }
}
