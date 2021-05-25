package br.univali.ttoproject.ide.components;

import br.univali.ttoproject.ide.App;
import br.univali.ttoproject.ide.components.settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Objects;
import java.util.function.Supplier;

public class MenuBar extends JMenuBar {

    public MenuBar(Supplier<?>[] methods, JMenuItem recentMenu) {
        JMenu menu;
        JMenuItem menuItem;
        JCheckBoxMenuItem checkBoxMenuItem;
        var keyCtrl = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();
        var keyShift = InputEvent.SHIFT_DOWN_MASK;

        menu = new JMenu("File");
        add(menu);

        menuItem = new JMenuItem("New");
        menuItem.addActionListener(e -> methods[MenuOptions.NEW.getID()].get());
        menuItem.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/new.png"))));
        menuItem.setAccelerator(KeyStroke.getKeyStroke('N', keyCtrl));
        menu.add(menuItem);

        menuItem = new JMenuItem("Open");
        menuItem.addActionListener(e -> methods[MenuOptions.OPEN.getID()].get());
        menuItem.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/open.png"))));
        menuItem.setAccelerator(KeyStroke.getKeyStroke('O', keyCtrl));
        menu.add(menuItem);

        menu.add(recentMenu);

        menuItem = new JMenuItem("Save");
        menuItem.addActionListener(e -> methods[MenuOptions.SAVE.getID()].get());
        menuItem.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/save.png"))));
        menuItem.setAccelerator(KeyStroke.getKeyStroke('S', keyCtrl));
        menu.add(menuItem);

        menuItem = new JMenuItem("Save As...");
        menuItem.addActionListener(e -> methods[MenuOptions.SAVE_AS.getID()].get());
        menuItem.setAccelerator(KeyStroke.getKeyStroke('S', keyShift | keyCtrl));
        menu.add(menuItem);

        menu.addSeparator();

        menuItem = new JMenuItem("Settings");
        menuItem.addActionListener(e -> methods[MenuOptions.SETTINGS.getID()].get());
        menu.add(menuItem);

        menu.addSeparator();

        menuItem = new JMenuItem("Exit");
        menuItem.addActionListener(e -> methods[MenuOptions.EXIT.getID()].get());
        menu.add(menuItem);

        menu = new JMenu("Edit");
        add(menu);

        menuItem = new JMenuItem("Undo");
        menuItem.addActionListener(e -> methods[MenuOptions.UNDO.getID()].get());
        menuItem.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/undo.png"))));
        menuItem.setAccelerator(KeyStroke.getKeyStroke('Z', keyCtrl));
        menu.add(menuItem);

        menuItem = new JMenuItem("Redo");
        menuItem.addActionListener(e -> methods[MenuOptions.REDO.getID()].get());
        menuItem.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/redo.png"))));
        menuItem.setAccelerator(KeyStroke.getKeyStroke('Y', keyCtrl));
        menu.add(menuItem);

        menu.addSeparator();

        menuItem = new JMenuItem("Cut");
        menuItem.addActionListener(e -> methods[MenuOptions.CUT.getID()].get());
        menuItem.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/cut.png"))));
        menuItem.setAccelerator(KeyStroke.getKeyStroke('X', keyCtrl));
        menu.add(menuItem);

        menuItem = new JMenuItem("Copy");
        menuItem.addActionListener(e -> methods[MenuOptions.COPY.getID()].get());
        menuItem.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/copy.png"))));
        menuItem.setAccelerator(KeyStroke.getKeyStroke('C', keyCtrl));
        menu.add(menuItem);

        menuItem = new JMenuItem("Paste");
        menuItem.addActionListener(e -> methods[MenuOptions.PASTE.getID()].get());
        menuItem.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/paste.png"))));
        menuItem.setAccelerator(KeyStroke.getKeyStroke('V', keyCtrl));
        menu.add(menuItem);

        menu.addSeparator();

        menuItem = new JMenuItem("Select All");
        menuItem.addActionListener(e -> methods[MenuOptions.SELECT_ALL.getID()].get());
        menuItem.setAccelerator(KeyStroke.getKeyStroke('A', keyCtrl));
        menu.add(menuItem);

        menu = new JMenu("View");
        add(menu);

        checkBoxMenuItem = new JCheckBoxMenuItem("Tool Bar");
        checkBoxMenuItem.setState(Settings.SHOW_TOOL_BAR);
        checkBoxMenuItem.addActionListener(e -> methods[MenuOptions.SHOW_TOOL_BAR.getID()].get());
        menu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("Status Bar");
        checkBoxMenuItem.setState(Settings.SHOW_STATUS_BAR);
        checkBoxMenuItem.addActionListener(e -> methods[MenuOptions.SHOW_STATUS_BAR.getID()].get());
        menu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("Console");
        checkBoxMenuItem.setState(Settings.SHOW_CONSOLE);
        checkBoxMenuItem.addActionListener(e -> methods[MenuOptions.SHOW_CONSOLE.getID()].get());
        menu.add(checkBoxMenuItem);

        menu = new JMenu("Build");
        add(menu);

        menuItem = new JMenuItem("Compile and Run");
        menuItem.addActionListener(e -> methods[MenuOptions.COMPILE_RUN.getID()].get());
        menuItem.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/compile_run.png"))));
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, keyShift));
        menu.add(menuItem);

        menuItem = new JMenuItem("Compile");
        menuItem.addActionListener(e -> methods[MenuOptions.COMPILE.getID()].get());
        menuItem.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/build.png"))));
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6, keyShift));
        menu.add(menuItem);

        menuItem = new JMenuItem("Run");
        menuItem.addActionListener(e -> methods[MenuOptions.RUN.getID()].get());
        menuItem.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/run.png"))));
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F7, keyShift));
        menu.add(menuItem);

        menuItem = new JMenuItem("Stop");
        menuItem.addActionListener(e -> methods[MenuOptions.STOP.getID()].get());
        menuItem.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/stop.png"))));
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F8, keyShift));
        menu.add(menuItem);

        menu = new JMenu("Help");
        add(menu);

        menuItem = new JMenuItem("Show Help");
        menuItem.addActionListener(e -> methods[MenuOptions.HELP.getID()].get());
        menuItem.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/help.png"))));
        menu.add(menuItem);

        menu.addSeparator();

        menuItem = new JMenuItem("About");
        menuItem.addActionListener(e -> methods[MenuOptions.ABOUT.getID()].get());
        menu.add(menuItem);
    }

    public void setOpenRecentMenu(JMenu menu) {
        var fileMenu = getMenu(0);
        fileMenu.remove(2);
        fileMenu.insert(menu, 2);
    }

}
