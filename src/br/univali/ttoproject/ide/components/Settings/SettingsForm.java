package br.univali.ttoproject.ide.components.Settings;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class SettingsForm extends JFrame {

    private Consumer<Object> update;

    private JPanel panelMain;
    private JButton btnSave;
    private JTabbedPane tabbedPane;

    private EditorSettings editorSettings;
    private AppearanceSettings appearanceSettings;
    private FontSettings fontSettings;

    public SettingsForm(Consumer<Object> update) {
        // vars and instantiating objects
        this.update = update;
        editorSettings = new EditorSettings();
        appearanceSettings = new AppearanceSettings();
        fontSettings = new FontSettings();

        // setting interface
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        setTitle("Settings");
        setIconImage(Toolkit.getDefaultToolkit().getImage(SettingsForm.class.getResource("/img/icon.png")));
        setVisible(true);
        setBounds(0, 0, 800, 450);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        setAlwaysOnTop(true);
        add(panelMain, BorderLayout.CENTER);

        tabbedPane.add("Editor", editorSettings.getPanelMain());
        tabbedPane.add("Appearance", appearanceSettings.getPanelMain());
        tabbedPane.add("Font", fontSettings.getPanelMain());

        fontSettings.getFontChooser().setSelectedFont(Settings.FONT);

        // btn save listener
        btnSave.addActionListener(e -> {
            update();
        });
    }

    private void update() {
        Settings.TAB_SIZE = Integer.parseInt(editorSettings.getTfTabSize().getText());
        Settings.LINE_ENDING = editorSettings.getCbLineEnding().getSelectedIndex();
        Settings.ENCODING = editorSettings.getCbEncoding().getSelectedIndex();
        Settings.FONT = fontSettings.getFontChooser().getSelectedFont();
        Settings.SHOW_TOOL_BAR = appearanceSettings.getCkbToolBar().isSelected();
        Settings.SHOW_STATUS_BAR = appearanceSettings.getCkbStatusBar().isSelected();
        Settings.SHOW_CONSOLE = appearanceSettings.getCkbConsole().isSelected();

        update.accept(null);
        setVisible(false);
        dispose();
    }

}
