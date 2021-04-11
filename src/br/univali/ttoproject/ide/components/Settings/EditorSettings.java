package br.univali.ttoproject.ide.components.Settings;

import javax.swing.*;

public class EditorSettings {

    private JPanel panelMain;
    private JTextField tfTabSize;
    private JComboBox<String> cbLineEnding;
    private JComboBox<String> cbEncoding;

    public EditorSettings() {
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

    public JComboBox<String> getCbLineEnding() {
        return cbLineEnding;
    }

    public JComboBox<String> getCbEncoding() {
        return cbEncoding;
    }

}
