package br.univali.ttoproject.ide.components.Settings;

import br.univali.ttoproject.ide.App;

import javax.swing.*;
import java.awt.*;

public class SettingsForm extends JFrame {

    private JPanel panelMain;
    private JButton btnSave;
    private JTabbedPane tabbedPane;

    // default options
    public static int TAB_SIZE = 4;
    public static boolean SHOW_TOOL_BAR = true;
    public static boolean SHOW_STATUS_BAR = true;
    public static boolean SHOW_CONSOLE = true;

    public SettingsForm(App app) {
        // vars and instantiating objects

        // setting interface
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        setTitle("Settings");
        setIconImage(Toolkit.getDefaultToolkit().getImage(SettingsForm.class.getResource("/img/icon.png")));
        setVisible(true);
        setBounds(0, 0, 450, 300);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        setAlwaysOnTop(true);
        add(panelMain);


        // btn save listener
        btnSave.addActionListener(e -> {
            app.updateSettings();
            setVisible(false);
            dispose();
        });
    }

}
