package br.univali.ttoproject.ide.components;

import br.univali.ttoproject.ide.App;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SettingsForm extends JFrame {

    private JPanel panelMain;
    private JList<String> listOptionsMenu;
    private JTable tableKeyValueOptions;
    private JButton btnOk;

    public static int TAB_SIZE = 4;

    public SettingsForm(App app) {

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

        var listModel = new DefaultListModel<String>();
        listModel.addElement("Editor");
        listOptionsMenu.setModel(listModel);
        listOptionsMenu.setSelectedIndex(0);
        listOptionsMenu.setBorder(new EmptyBorder(3, 3, 3, 3));

        var tableModel = new DefaultTableModel();
        // set columns
        tableModel.setColumnIdentifiers(new Object[] {"Option", "Value"});
        // add rows
        tableModel.addRow(new Object[]{"Tab size", Integer.toString(TAB_SIZE)});
        tableKeyValueOptions.setModel(tableModel);
        btnOk.addActionListener(e -> {
            TAB_SIZE = Integer.parseInt(tableKeyValueOptions.getModel().getValueAt(0, 1).toString());
            app.updateSettings();
            setVisible(false);
            dispose();
        });
    }

}
