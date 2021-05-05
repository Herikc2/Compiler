package br.univali.ttoproject.ide.components.settings;

import javax.swing.*;
import java.awt.*;

public class SettingsForm extends JDialog {

    private final EditorSettings editorSettings;
    private final AppearanceSettings appearanceSettings;
    private final FontSettings fontSettings;
    private JPanel panelMain;
    private JButton btnSave;
    private JTabbedPane tabbedPane;
    private JButton btnRestoreDefault;

    public SettingsForm(JFrame parent) {
        super(parent, false);

        // vars and instantiating objects
        editorSettings = new EditorSettings();
        appearanceSettings = new AppearanceSettings();
        fontSettings = new FontSettings();

        // setting interface
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        setTitle("Settings");
        setIconImage(Toolkit.getDefaultToolkit().getImage(SettingsForm.class.getResource("/img/icon.png")));
        setVisible(true);
        setBounds(0, 0, 500, 350);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        add(panelMain, BorderLayout.CENTER);

        tabbedPane.add("Editor", editorSettings.getPanelMain());
        tabbedPane.add("Appearance", appearanceSettings.getPanelMain());
        tabbedPane.add("Font", fontSettings.getPanelMain());

        fontSettings.getFontChooser().setSelectedFont(Settings.FONT);

        // btn restore listener
        btnRestoreDefault.addActionListener(e -> restoreDefault() );

        // btn save listener
        btnSave.addActionListener(e -> update());
        getRootPane().setDefaultButton(btnSave);
    }

    private void restoreDefault() {
        Settings.setDefaultSettings();
        saveSettingsAndClose();
    }

    private void update() {
        Settings.TAB_TYPE = editorSettings.getCbTabType().getSelectedIndex();
        Settings.TAB_SIZE = Integer.parseInt(editorSettings.getTfTabSize().getText());
        Settings.LINE_ENDING = editorSettings.getCbLineEnding().getSelectedIndex();
        Settings.ENCODING = editorSettings.getCbEncoding().getSelectedIndex();
        Settings.CODING_HELP = editorSettings.getCkbCodingHelp().isSelected();
        Settings.SUGGESTIONS = editorSettings.getCkbCodingSuggestions().isSelected();

        Settings.FONT = fontSettings.getFontChooser().getSelectedFont();

        Settings.SHOW_TOOL_BAR = appearanceSettings.getCkbToolBar().isSelected();
        Settings.SHOW_STATUS_BAR = appearanceSettings.getCkbStatusBar().isSelected();
        Settings.SHOW_CONSOLE = appearanceSettings.getCkbConsole().isSelected();
        Settings.LOOK_AND_FEEL = appearanceSettings.getCboLookAndFeel().getSelectedIndex();
        Settings.FONT_THEME = appearanceSettings.getCboFontTheme().getSelectedIndex();
        Settings.SYNTAX_HIGHLIGHT = appearanceSettings.getCkbSyntaxHighlight().isSelected();

        saveSettingsAndClose();
    }

    private void saveSettingsAndClose() {
        Settings.setLookAndFeel();
        Settings.setFontTheme();
        Settings.save();
        Settings.update();

        setVisible(false);
        dispose();
    }

}
