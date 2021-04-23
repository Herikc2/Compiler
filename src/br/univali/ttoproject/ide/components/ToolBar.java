package br.univali.ttoproject.ide.components;

import br.univali.ttoproject.ide.App;

import javax.swing.*;
import java.util.Objects;
import java.util.function.Supplier;

public class ToolBar extends JToolBar {

    public ToolBar(Supplier<?>[] methods) {

        JButton btn;

        btn = new JButton("");
        btn.setToolTipText("New - Ctrl+N");
        btn.addActionListener(e -> methods[MenuOptions.NEW.getID()].get());
        btn.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/new.png"))));
        add(btn);

        btn = new JButton("");
        btn.setToolTipText("Open - Ctrl+O");
        btn.addActionListener(e -> methods[MenuOptions.OPEN.getID()].get());
        btn.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/open.png"))));
        add(btn);

        btn = new JButton("");
        btn.setToolTipText("Save - Ctrl+S");
        btn.addActionListener(e -> methods[MenuOptions.SAVE.getID()].get());
        btn.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/save.png"))));
        add(btn);

        addSeparator();

        btn = new JButton("");
        btn.setToolTipText("Undo - Ctrl+Z");
        btn.addActionListener(e -> methods[MenuOptions.UNDO.getID()].get());
        btn.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/undo.png"))));
        add(btn);

        btn = new JButton("");
        btn.setToolTipText("Redo - Ctrl+Y");
        btn.addActionListener(e -> methods[MenuOptions.REDO.getID()].get());
        btn.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/redo.png"))));
        add(btn);

        addSeparator();

        btn = new JButton("");
        btn.setToolTipText("Cut - Ctrl+X");
        btn.addActionListener(e -> methods[MenuOptions.CUT.getID()].get());
        btn.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/cut.png"))));
        add(btn);

        btn = new JButton("");
        btn.setToolTipText("Copy - Ctrl+C");
        btn.addActionListener(e -> methods[MenuOptions.COPY.getID()].get());
        btn.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/copy.png"))));
        add(btn);

        btn = new JButton("");
        btn.setToolTipText("Paste - Ctrl+V");
        btn.addActionListener(e -> methods[MenuOptions.PASTE.getID()].get());
        btn.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/paste.png"))));
        add(btn);

        addSeparator();

        btn = new JButton("");
        btn.setToolTipText("Compile and Run - F5");
        btn.addActionListener(e -> methods[MenuOptions.COMPILE_RUN.getID()].get());
        btn.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/compile_run.png"))));
        add(btn);

        btn = new JButton("");
        btn.setToolTipText("Compile - F6");
        btn.addActionListener(e -> methods[MenuOptions.COMPILE.getID()].get());
        btn.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/build.png"))));
        add(btn);

        btn = new JButton("");
        btn.setToolTipText("Run - F7");
        btn.addActionListener(e -> methods[MenuOptions.RUN.getID()].get());
        btn.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/run.png"))));
        add(btn);

        btn = new JButton("");
        btn.setToolTipText("Stop - F8");
        btn.addActionListener(e -> methods[MenuOptions.STOP.getID()].get());
        btn.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/stop.png"))));
        add(btn);
    }

}
