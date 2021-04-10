package br.univali.ttoproject.ide.components;

import br.univali.ttoproject.ide.App;

import javax.swing.*;
import java.util.function.Supplier;

public class MenuBar extends JMenuBar {

    public MenuBar(Supplier<?>[] methods) {
        JMenu menu;
        JMenuItem menuItem;
        JCheckBoxMenuItem checkBoxMenuItem;

        menu = new JMenu("File");
        add(menu);

        menuItem = new JMenuItem("New");
        menuItem.addActionListener(e -> methods[MenuOptions.NEW.getId()].get());
        menuItem.setIcon(new ImageIcon(App.class.getResource("/img/New File.png")));
        menu.add(menuItem);

        menuItem = new JMenuItem("Open");
        menuItem.addActionListener(e -> methods[MenuOptions.OPEN.getId()].get());
        menuItem.setIcon(new ImageIcon(App.class.getResource("/img/Open Project.png")));
        menu.add(menuItem);

        menuItem = new JMenuItem("Save");
        menuItem.addActionListener(e -> methods[MenuOptions.SAVE.getId()].get());
        menuItem.setIcon(new ImageIcon(App.class.getResource("/img/Save File.png")));
        menu.add(menuItem);

        menuItem = new JMenuItem("Save as...");
        menuItem.addActionListener(e -> methods[MenuOptions.SAVE_AS.getId()].get());
        menu.add(menuItem);

        menu.addSeparator();

        menuItem = new JMenuItem("Settings");
        menuItem.addActionListener(e -> methods[MenuOptions.SETTINGS.getId()].get());
        menu.add(menuItem);

        menu.addSeparator();

        menuItem = new JMenuItem("Exit");
        menuItem.addActionListener(e -> methods[MenuOptions.EXIT.getId()].get());
        menu.add(menuItem);

        menu = new JMenu("Edit");
        add(menu);

        menuItem = new JMenuItem("Undo");
        menuItem.addActionListener(e -> methods[MenuOptions.UNDO.getId()].get());
        menuItem.setIcon(new ImageIcon(App.class.getResource("/img/Undo.PNG")));
        menu.add(menuItem);

        menuItem = new JMenuItem("Redo");
        menuItem.addActionListener(e -> methods[MenuOptions.REDO.getId()].get());
        menuItem.setIcon(new ImageIcon(App.class.getResource("/img/Redo.PNG")));
        menu.add(menuItem);

        menu.addSeparator();

        menuItem = new JMenuItem("Cut");
        menuItem.addActionListener(e -> methods[MenuOptions.CUT.getId()].get());
        menuItem.setIcon(new ImageIcon(App.class.getResource("/img/Cut.PNG")));
        menu.add(menuItem);

        menuItem = new JMenuItem("Copy");
        menuItem.addActionListener(e -> methods[MenuOptions.COPY.getId()].get());
        menuItem.setIcon(new ImageIcon(App.class.getResource("/img/Copy.PNG")));
        menu.add(menuItem);

        menuItem = new JMenuItem("Paste");
        menuItem.addActionListener(e -> methods[MenuOptions.PASTE.getId()].get());
        menuItem.setIcon(new ImageIcon(App.class.getResource("/img/Paste.png")));
        menu.add(menuItem);

        menu = new JMenu("View");
        add(menu);

        checkBoxMenuItem = new JCheckBoxMenuItem("Tool bar");
        checkBoxMenuItem.setState(true);
        checkBoxMenuItem.addActionListener(e -> methods[MenuOptions.TOOL_BAR.getId()].get());
        menu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("Status bar");
        checkBoxMenuItem.setState(true);
        checkBoxMenuItem.addActionListener(e -> methods[MenuOptions.STATUS_BAR.getId()].get());
        menu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("Console");
        checkBoxMenuItem.setState(true);
        checkBoxMenuItem.addActionListener(e -> methods[MenuOptions.CONSOLE.getId()].get());
        menu.add(checkBoxMenuItem);

        menu = new JMenu("Build");
        add(menu);

        menuItem = new JMenuItem("Compile");
        menuItem.addActionListener(e -> methods[MenuOptions.COMPILE.getId()].get());
        menuItem.setIcon(new ImageIcon(App.class.getResource("/img/Cog.png")));
        menu.add(menuItem);

        menuItem = new JMenuItem("Run");
        menuItem.addActionListener(e -> methods[MenuOptions.RUN.getId()].get());
        menuItem.setIcon(new ImageIcon(App.class.getResource("/img/Run .PNG")));
        menu.add(menuItem);

        menu = new JMenu("Help");
        add(menu);

        menuItem = new JMenuItem("Show help");
        menuItem.addActionListener(e -> methods[MenuOptions.HELP.getId()].get());
        menu.add(menuItem);

        menuItem = new JMenuItem("About");
        menuItem.addActionListener(e -> methods[MenuOptions.ABOUT.getId()].get());
        menu.add(menuItem);
    }

}
