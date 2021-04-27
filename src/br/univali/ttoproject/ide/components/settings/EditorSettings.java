package br.univali.ttoproject.ide.components.settings;

import javax.swing.*;

public class EditorSettings {

    private JPanel panelMain;
    private JTextField tfTabSize;
    private JComboBox<String> cbLineEnding;
    private JComboBox<String> cbEncoding;
    private JComboBox<String> cbTabType;
    private JCheckBox ckbCodingHelp;
    private JCheckBox ckbCodingSuggestions;
    private JPanel pnlText;
    private JPanel pnlBehavior;

    public EditorSettings() {
        tfTabSize.setText(Integer.toString(Settings.TAB_SIZE));

        cbLineEnding.addItem("CRLF");
        cbLineEnding.addItem("LF");

        cbEncoding.addItem("UTF-8");

        cbTabType.addItem("SPACES");
        cbTabType.addItem("TAB");

        cbLineEnding.setSelectedIndex(Settings.LINE_ENDING);
        cbTabType.setSelectedIndex(Settings.TAB_TYPE);

        ckbCodingHelp.setSelected(Settings.CODING_HELP);
        ckbCodingSuggestions.setSelected(Settings.SUGGESTIONS);
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

    public JComboBox<String> getCbTabType() {
        return cbTabType;
    }

    public JCheckBox getCkbCodingHelp() {
        return ckbCodingHelp;
    }

    public JCheckBox getCkbCodingSuggestions() {
        return ckbCodingSuggestions;
    }
}
