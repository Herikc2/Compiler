package br.univali.ttoproject.ide.components;

import br.univali.ttoproject.ide.App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Objects;
import java.util.function.Supplier;

public class MenuBar extends JMenuBar {

    public MenuBar(Supplier<?>[] methods) {
        JMenu menu;
        JMenuItem menuItem;
        JCheckBoxMenuItem checkBoxMenuItem;
        var keyCtrl = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();
        var keyShift = InputEvent.SHIFT_DOWN_MASK;

        menu = new JMenu("File");
        add(menu);

        menuItem = new JMenuItem("New");
        menuItem.addActionListener(e -> methods[MenuOptions.NEW.getId()].get());
        menuItem.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/New File.png"))));
        menuItem.setAccelerator(KeyStroke.getKeyStroke('N', keyCtrl));
        menu.add(menuItem);

        menuItem = new JMenuItem("Open");
        menuItem.addActionListener(e -> methods[MenuOptions.OPEN.getId()].get());
        menuItem.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/Open Project.png"))));
        menuItem.setAccelerator(KeyStroke.getKeyStroke('O', keyCtrl));
        menu.add(menuItem);

        menuItem = new JMenuItem("Save");
        menuItem.addActionListener(e -> methods[MenuOptions.SAVE.getId()].get());
        menuItem.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/Save File.png"))));
        menuItem.setAccelerator(KeyStroke.getKeyStroke('S', keyCtrl));
        menu.add(menuItem);

        menuItem = new JMenuItem("Save as...");
        menuItem.addActionListener(e -> methods[MenuOptions.SAVE_AS.getId()].get());
        menuItem.setAccelerator(KeyStroke.getKeyStroke('S', keyShift | keyCtrl));
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
        menuItem.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/Undo.PNG"))));
        menuItem.setAccelerator(KeyStroke.getKeyStroke('Z', keyCtrl));
        menu.add(menuItem);

        menuItem = new JMenuItem("Redo");
        menuItem.addActionListener(e -> methods[MenuOptions.REDO.getId()].get());
        menuItem.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/Redo.PNG"))));
        menuItem.setAccelerator(KeyStroke.getKeyStroke('Y', keyCtrl));
        menu.add(menuItem);

        menu.addSeparator();

        menuItem = new JMenuItem("Cut");
        menuItem.addActionListener(e -> methods[MenuOptions.CUT.getId()].get());
        menuItem.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/Cut.PNG"))));
        menuItem.setAccelerator(KeyStroke.getKeyStroke('X', keyCtrl));
        menu.add(menuItem);

        menuItem = new JMenuItem("Copy");
        menuItem.addActionListener(e -> methods[MenuOptions.COPY.getId()].get());
        menuItem.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/Copy.PNG"))));
        menuItem.setAccelerator(KeyStroke.getKeyStroke('C', keyCtrl));
        menu.add(menuItem);

        menuItem = new JMenuItem("Paste");
        menuItem.addActionListener(e -> methods[MenuOptions.PASTE.getId()].get());
        menuItem.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/Paste.png"))));
        menuItem.setAccelerator(KeyStroke.getKeyStroke('V', keyCtrl));
        menu.add(menuItem);

        menu.addSeparator();

        menuItem = new JMenuItem("Select all");
        menuItem.addActionListener(e -> methods[MenuOptions.SELECT_ALL.getId()].get());
        menuItem.setAccelerator(KeyStroke.getKeyStroke('A', keyCtrl));
        menu.add(menuItem);

        menu = new JMenu("View");
        add(menu);

        checkBoxMenuItem = new JCheckBoxMenuItem("Tool bar");
        checkBoxMenuItem.setState(true);
        checkBoxMenuItem.addActionListener(e -> methods[MenuOptions.SHOW_TOOL_BAR.getId()].get());
        menu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("Status bar");
        checkBoxMenuItem.setState(true);
        checkBoxMenuItem.addActionListener(e -> methods[MenuOptions.SHOW_STATUS_BAR.getId()].get());
        menu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("Console");
        checkBoxMenuItem.setState(true);
        checkBoxMenuItem.addActionListener(e -> methods[MenuOptions.SHOW_CONSOLE.getId()].get());
        menu.add(checkBoxMenuItem);

        menu = new JMenu("Build");
        add(menu);

        menuItem = new JMenuItem("Compile and run");
        menuItem.addActionListener(e -> methods[MenuOptions.COMPILE_RUN.getId()].get());
        menuItem.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/CR.png"))));
        menuItem.setAccelerator(KeyStroke.getKeyStroke("F5"));
        menu.add(menuItem);

        menuItem = new JMenuItem("Compile");
        menuItem.addActionListener(e -> methods[MenuOptions.COMPILE.getId()].get());
        menuItem.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/Cog.png"))));
        menuItem.setAccelerator(KeyStroke.getKeyStroke("F6"));
        menu.add(menuItem);

        menuItem = new JMenuItem("Run");
        menuItem.addActionListener(e -> methods[MenuOptions.RUN.getId()].get());
        menuItem.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/Run.PNG"))));
        menuItem.setAccelerator(KeyStroke.getKeyStroke("F7"));
        menu.add(menuItem);

        menuItem = new JMenuItem("Stop");
        menuItem.addActionListener(e -> methods[MenuOptions.STOP.getId()].get());
        menuItem.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/Stop.PNG"))));
        menuItem.setAccelerator(KeyStroke.getKeyStroke("F8"));
        menu.add(menuItem);

        menu = new JMenu("Help");
        add(menu);

        menuItem = new JMenuItem("Show help");
        menuItem.addActionListener(e -> methods[MenuOptions.HELP.getId()].get());
        menu.add(menuItem);

        menu.addSeparator();

        menuItem = new JMenuItem("About");
        menuItem.addActionListener(e -> methods[MenuOptions.ABOUT.getId()].get());
        menu.add(menuItem);
    }

}
