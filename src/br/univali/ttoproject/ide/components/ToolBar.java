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
        btn.addActionListener(e -> methods[MenuOptions.NEW.getId()].get());
        btn.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/New File.png"))));
        add(btn);

        btn = new JButton("");
        btn.setToolTipText("Open - Ctrl+O");
        btn.addActionListener(e -> methods[MenuOptions.OPEN.getId()].get());
        btn.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/Open Project.png"))));
        add(btn);

        btn = new JButton("");
        btn.setToolTipText("Save - Ctrl+S");
        btn.addActionListener(e -> methods[MenuOptions.SAVE.getId()].get());
        btn.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/Save File.png"))));
        add(btn);

        addSeparator();

        btn = new JButton("");
        btn.setToolTipText("Undo - Ctrl+Z");
        btn.addActionListener(e -> methods[MenuOptions.UNDO.getId()].get());
        btn.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/Undo.PNG"))));
        add(btn);

        btn = new JButton("");
        btn.setToolTipText("Redo - Ctrl+Y");
        btn.addActionListener(e -> methods[MenuOptions.REDO.getId()].get());
        btn.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/Redo.PNG"))));
        add(btn);

        addSeparator();

        btn = new JButton("");
        btn.setToolTipText("Cut - Ctrl+X");
        btn.addActionListener(e -> methods[MenuOptions.CUT.getId()].get());
        btn.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/Cut.PNG"))));
        add(btn);

        btn = new JButton("");
        btn.setToolTipText("Copy - Ctrl+C");
        btn.addActionListener(e -> methods[MenuOptions.COPY.getId()].get());
        btn.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/Copy.PNG"))));
        add(btn);

        btn = new JButton("");
        btn.setToolTipText("Paste - Ctrl+V");
        btn.addActionListener(e -> methods[MenuOptions.PASTE.getId()].get());
        btn.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/Paste.png"))));
        add(btn);

        addSeparator();

        btn = new JButton("");
        btn.setToolTipText("Compile and run - F5");
        btn.addActionListener(e -> methods[MenuOptions.COMPILE_RUN.getId()].get());
        btn.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/CR.png"))));
        add(btn);

        btn = new JButton("");
        btn.setToolTipText("Compile - F6");
        btn.addActionListener(e -> methods[MenuOptions.COMPILE.getId()].get());
        btn.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/Cog.png"))));
        add(btn);

        btn = new JButton("");
        btn.setToolTipText("Run - F7");
        btn.addActionListener(e -> methods[MenuOptions.RUN.getId()].get());
        btn.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/Run.PNG"))));
        add(btn);

        btn = new JButton("");
        btn.setToolTipText("Stop - F8");
        btn.addActionListener(e -> methods[MenuOptions.STOP.getId()].get());
        btn.setIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("/img/Stop.PNG"))));
        add(btn);
    }

}
