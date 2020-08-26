package sigmu.simpleinfer.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.*;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.intellij.util.xmlb.annotations.Property;

@State(name = "SimpleInferAppSettings", storages = {@Storage("$APP_CONFIG/infer.xml")})
public class GlobalSettings implements PersistentStateComponent<GlobalSettings> {

    @Property
    private String runCmd = "infer run --";

    public String getRunCmd() {
        return runCmd;
    }

    public void setRunCmd(String runCmd) {
        this.runCmd = runCmd;
    }

    @Nullable
    @Override
    public GlobalSettings getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull GlobalSettings state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    public static GlobalSettings getInstance() {
        return ApplicationManager.getApplication().getComponent(GlobalSettings.class);
    }
}
