package br.univali.ttoproject.ide.components.Settings;

import javax.swing.*;

public class EditorSettings {

    private JPanel panelMain;
    private JTextField tfTabSize;
    private JComboBox cbLineEnding;
    private JComboBox cbEncoding;

    public EditorSettings(){
        tfTabSize.setText("4");
        cbLineEnding.addItem("CRLF");
        cbLineEnding.addItem("LF");
        cbEncoding.addItem("UTF-8");
        cbLineEnding.setSelectedIndex(Settings.LINE_ENDING);
    }

    public JPanel getPanelMain() {
        return panelMain;
    }

    public JTextField getTfTabSize() {
        return tfTabSize;
    }

    public JComboBox getCbLineEnding() {
        return cbLineEnding;
    }

    public JComboBox getCbEncoding() {
        return cbEncoding;
    }

}
