package sigmu.simpleinfer.ui;

import sigmu.simpleinfer.run.InferRunConfiguration;

import javax.swing.*;

import org.jetbrains.annotations.NotNull;

import com.intellij.execution.ExecutionException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.ui.components.fields.ExpandableTextField;

public class RunConfigurationEditor extends SettingsEditor<InferRunConfiguration> {
    private JPanel mainPanel;
    private ExpandableTextField additionalArgsTextField;

    public RunConfigurationEditor() {

    }

    @Override
    protected void resetEditorFrom(@NotNull InferRunConfiguration inferRC) {
        //reloadBuildToolComboBoxList(inferRC);
        additionalArgsTextField.setText(inferRC.getInferLaunchCmd());
    }

    @Override
    protected void applyEditorTo(@NotNull InferRunConfiguration inferRC) {
        inferRC.setInferLaunchCmd(additionalArgsTextField.getText());
    }

    @NotNull
    @Override
    protected JComponent createEditor() {
        return mainPanel;
    }

    private void createUIComponents() {

    }
}
