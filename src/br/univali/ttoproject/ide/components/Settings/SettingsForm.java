package br.univali.ttoproject.ide.components.Settings;

import br.univali.ttoproject.ide.App;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SettingsForm extends JDialog {

    private final Consumer<Object> update;

    private JPanel panelMain;
    private JButton btnSave;
    private JTabbedPane tabbedPane;

    private final EditorSettings editorSettings;
    private final AppearanceSettings appearanceSettings;
    private final FontSettings fontSettings;

    public SettingsForm(JFrame parent, Consumer<Object> update) {
        super(parent, false);
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
        setBounds(0, 0, 500, 350);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        add(panelMain, BorderLayout.CENTER);

        tabbedPane.add("Editor", editorSettings.getPanelMain());
        tabbedPane.add("Appearance", appearanceSettings.getPanelMain());
        tabbedPane.add("Font", fontSettings.getPanelMain());

        fontSettings.getFontChooser().setSelectedFont(Settings.FONT);

        // btn save listener
        btnSave.addActionListener(e -> {
            update();
        });
        getRootPane().setDefaultButton(btnSave);
    }

    private void update() {
        Settings.TAB_TYPE = editorSettings.getCbTabType().getSelectedIndex();
        Settings.TAB_SIZE = Integer.parseInt(editorSettings.getTfTabSize().getText());
        Settings.LINE_ENDING = editorSettings.getCbLineEnding().getSelectedIndex();
        Settings.ENCODING = editorSettings.getCbEncoding().getSelectedIndex();

        Settings.FONT = fontSettings.getFontChooser().getSelectedFont();

        Settings.SHOW_TOOL_BAR = appearanceSettings.getCkbToolBar().isSelected();
        Settings.SHOW_STATUS_BAR = appearanceSettings.getCkbStatusBar().isSelected();
        Settings.SHOW_CONSOLE = appearanceSettings.getCkbConsole().isSelected();
        Settings.LOOK_AND_FEEL = appearanceSettings.getCboLookAndFeel().getSelectedIndex();
        Settings.FONT_THEME = appearanceSettings.getCboFontTheme().getSelectedIndex();
        Settings.SYNTAX_HIGHLIGHT = appearanceSettings.getCkbSyntaxHighlight().isSelected();
        setLookAndFeel();
        setFontTheme();

        update.accept(null);
        setVisible(false);
        dispose();
    }

    private void setLookAndFeel(){
        try {
            var op = Settings.LOOK_AND_FEEL;
            var name = UIManager.getInstalledLookAndFeels()[op].getClassName();
            UIManager.setLookAndFeel(name);
        } catch (ClassNotFoundException |
                InstantiationException |
                IllegalAccessException |
                UnsupportedLookAndFeelException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setFontTheme(){
        var op = Settings.FONT_THEME;
        FontTheme.setFontTheme(op);
    }

}
