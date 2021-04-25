package br.univali.ttoproject.ide.components;

import br.univali.ttoproject.ide.components.settings.SettingsForm;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;

public class HelpForm extends JDialog {

    private JPanel panelMain;

    public HelpForm(JFrame parent) {
        super(parent, false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        setTitle("Help");
        setIconImage(Toolkit.getDefaultToolkit().getImage(SettingsForm.class.getResource("/img/icon.png")));
        setBounds(0, 0, 450, 300);
        setLocationRelativeTo(null);
        getContentPane().add(panelMain, BorderLayout.CENTER);
        setVisible(true);

        var scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 11, 414, 206);
        panelMain.add(scrollPane);

        var tree = new JTree();
        tree.setModel(new DefaultTreeModel(
                new DefaultMutableTreeNode("Help") {
                    {
                        DefaultMutableTreeNode node_1;
                        node_1 = new DefaultMutableTreeNode("File");
                        node_1.add(new DefaultMutableTreeNode("New: Create a new 2021.1 file"));
                        node_1.add(new DefaultMutableTreeNode("Open: Open a 2021.1 file"));
                        node_1.add(new DefaultMutableTreeNode("Save: Save the current file"));
                        node_1.add(new DefaultMutableTreeNode("Save as: Save the current file with another name"));
                        node_1.add(new DefaultMutableTreeNode("Exit: Exits the program"));
                        add(node_1);
                        node_1 = new DefaultMutableTreeNode("Edit");
                        node_1.add(new DefaultMutableTreeNode("Cut: Cut the text selection to the transfer area"));
                        node_1.add(new DefaultMutableTreeNode("Copy: Copy the text selection to the transfer area"));
                        node_1.add(new DefaultMutableTreeNode("Paste: Paste at caret position the text from transfer area"));
                        add(node_1);
                        node_1 = new DefaultMutableTreeNode("Build");
                        node_1.add(new DefaultMutableTreeNode("Compile: Compile the current file"));
                        node_1.add(new DefaultMutableTreeNode("Run: Run the current file"));
                        add(node_1);
                    }
                }
        ));

        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
        renderer.setLeafIcon(null);
        renderer.setClosedIcon(null);
        renderer.setOpenIcon(null);

        scrollPane.setViewportView(tree);
        {
            var buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                var okButton = new JButton("OK");
                okButton.setActionCommand("OK");
                okButton.addActionListener(e -> exit());
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
        }
    }

    private void exit(){
        setVisible(false);
        dispose();
    }
}
