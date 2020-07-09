package sigmu.simpleinfer.ui;

import java.awt.*;

import javax.swing.*;

import com.intellij.openapi.options.ex.ConfigurableCardPanel;

public class InferConfigPanel extends JPanel {
    private JTextField textField1;
    private JTextField textField2;
    private JPanel configPanelContent;

    public InferConfigPanel() {
        add(configPanelContent);
    }

    public String getRunCmd() {
        return textField1.getText();
    }
}
