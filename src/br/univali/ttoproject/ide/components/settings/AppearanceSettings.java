package br.univali.ttoproject.ide.components.settings;

import javax.swing.*;

public class AppearanceSettings {
    private JPanel panelMain;
    private JCheckBox ckbToolBar;
    private JCheckBox ckbConsole;
    private JCheckBox ckbStatusBar;
    private JComboBox<String> cboLookAndFeel;
    private JComboBox<String> cboFontTheme;
    private JCheckBox ckbSyntaxHighlight;

    public AppearanceSettings() {
        ckbToolBar.setSelected(Settings.SHOW_TOOL_BAR);
        ckbConsole.setSelected(Settings.SHOW_CONSOLE);
        ckbStatusBar.setSelected(Settings.SHOW_STATUS_BAR);
        ckbSyntaxHighlight.setSelected(Settings.SYNTAX_HIGHLIGHT);

        var lafInfo = UIManager.getInstalledLookAndFeels();
        var fontThemes = FontTheme.getFontThemes();

        for (var laf : lafInfo) {
            cboLookAndFeel.addItem(laf.getName());
        }

        for (var t : fontThemes) {
            cboFontTheme.addItem(t);
        }

        cboLookAndFeel.setSelectedIndex(Settings.LOOK_AND_FEEL);
        cboFontTheme.setSelectedIndex(Settings.FONT_THEME);
    }

    public JPanel getPanelMain() {
        return panelMain;
    }

    public JCheckBox getCkbToolBar() {
        return ckbToolBar;
    }

    public JCheckBox getCkbConsole() {
        return ckbConsole;
    }

    public JCheckBox getCkbStatusBar() {
        return ckbStatusBar;
    }

    public JComboBox<String> getCboLookAndFeel() {
        return cboLookAndFeel;
    }

    public JComboBox<String> getCboFontTheme() {
        return cboFontTheme;
    }

    public JCheckBox getCkbSyntaxHighlight() {
        return ckbSyntaxHighlight;
    }
}
