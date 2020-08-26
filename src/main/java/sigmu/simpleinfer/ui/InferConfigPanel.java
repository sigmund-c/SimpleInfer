package sigmu.simpleinfer.ui;

import java.awt.*;

import javax.swing.*;

import com.intellij.openapi.options.ex.ConfigurableCardPanel;

public class InferConfigPanel extends JPanel {
    private JTextField runCmd;
    private JPanel configPanelContent;

    public InferConfigPanel() {
        add(configPanelContent);
    }

    public String getRunCmd() {
        return runCmd.getText();
    }

    public void setRunCmd(String runCmd) {
        this.runCmd.setText(runCmd);
        int b = 1;
    }
}
