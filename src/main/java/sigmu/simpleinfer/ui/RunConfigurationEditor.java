package sigmu.simpleinfer.ui;

import sigmu.simpleinfer.run.infer.InferRunConfiguration;

import javax.swing.*;

import org.jetbrains.annotations.NotNull;

import com.intellij.openapi.options.SettingsEditor;
import com.intellij.ui.components.fields.ExpandableTextField;

public class RunConfigurationEditor extends SettingsEditor<InferRunConfiguration> {
    private JPanel mainPanel;
    private ExpandableTextField runCmd;

    public RunConfigurationEditor() {

    }

    @Override
    protected void resetEditorFrom(@NotNull InferRunConfiguration inferRC) {
        //reloadBuildToolComboBoxList(inferRC);
        runCmd.setText(inferRC.getInferLaunchCmd());
    }

    @Override
    protected void applyEditorTo(@NotNull InferRunConfiguration inferRC) {
        inferRC.setInferLaunchCmd(runCmd.getText());
    }

    @NotNull
    @Override
    protected JComponent createEditor() {
        return mainPanel;
    }

    public String getRunCmd() {
        return runCmd.getText();
    }
}
