package sigmu.simpleinfer.ui;

import sigmu.simpleinfer.run.infer.InferRunConfiguration;
import sigmu.simpleinfer.run.racerdfix.RacerDFixRunConfiguration;

import javax.swing.*;

import org.jetbrains.annotations.NotNull;

import com.intellij.openapi.options.SettingsEditor;
import com.intellij.ui.components.fields.ExpandableTextField;

public class RacerDFixRunConfigurationEditor extends SettingsEditor<RacerDFixRunConfiguration> {
    private JPanel mainPanel;
    private JTextField racerdfixRunCmd;
    private JTextField inferRunCmd;

    public RacerDFixRunConfigurationEditor() {

    }

    @Override
    protected void resetEditorFrom(@NotNull RacerDFixRunConfiguration racerdfixRC) {
        //reloadBuildToolComboBoxList(inferRC);
        inferRunCmd.setText(racerdfixRC.getInferLaunchCmd());
        racerdfixRunCmd.setText(racerdfixRC.getRacerDFixLaunchCmd());
    }

    @Override
    protected void applyEditorTo(@NotNull RacerDFixRunConfiguration racerdfixRC) {
        racerdfixRC.setInferLaunchCmd(inferRunCmd.getText());
        racerdfixRC.setRacerDFixLaunchCmd(racerdfixRunCmd.getText());
    }

    @NotNull
    @Override
    protected JComponent createEditor() {
        return mainPanel;
    }

    public String getInferRunCmd() {
        return inferRunCmd.getText();
    }

    public String getRacerDFixRunCmd() {
        return racerdfixRunCmd.getText();
    }
}
