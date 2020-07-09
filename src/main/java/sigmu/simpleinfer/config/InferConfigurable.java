package sigmu.simpleinfer.config;

import sigmu.simpleinfer.ui.InferConfigPanel;

import javax.swing.*;

import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;

public class InferConfigurable implements Configurable {
    private final Project project;
    private final InferConfigPanel configPanel;

    private String runCmd;

    public InferConfigurable(@NotNull final Project project, @NotNull final InferConfigPanel configPanel) {
        this.project = project;
        this.configPanel = new InferConfigPanel();
        runCmd = "infer run -- mvn compile";
    }

    @Override
    public @Nls(capitalization = Nls.Capitalization.Title) String getDisplayName() {
        return "Infer Configuration";
    }

    @Override
    public @Nullable JComponent createComponent() {
        reset();
        return configPanel;
    }

    @Override
    public boolean isModified() {
        return false;
    }

    public void apply() {
        runCmd = configPanel.getRunCmd();
    }

    public String getRunCmd() {
        return runCmd;
    }

}
