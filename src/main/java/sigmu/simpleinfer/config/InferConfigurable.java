package sigmu.simpleinfer.config;

import sigmu.simpleinfer.ui.InferConfigPanel;

import javax.swing.*;

import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;

public class InferConfigurable implements Configurable {
    private GlobalSettings settings;
    private InferConfigPanel settingsPanel;

    public InferConfigurable(GlobalSettings settings) {
        this.settingsPanel = new InferConfigPanel();
        if (settings == null) {
            this.settings = new GlobalSettings();
        } else {
            this.settings = settings;
        }
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Infer Configuration";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        reset();
        return this.settingsPanel;
    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public void apply() {
        this.settings.setRunCmd(this.settingsPanel.getRunCmd());
    }

    @Override
    public void reset() {
        int x = 1;
        this.settingsPanel.setRunCmd(this.settings.getRunCmd());
        int a = 3;
    }



}
