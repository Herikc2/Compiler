package br.univali.ttoproject.ide.components;

import br.univali.ttoproject.ide.App;

import javax.swing.*;
import java.awt.*;
import java.util.function.Supplier;

public class ToolBar extends JToolBar {

    public ToolBar(Supplier<?>[] methods){

        JButton btn;

        btn = new JButton("");
        btn.addActionListener(e -> methods[MenuOptions.NEW.getId()].get());
        btn.setIcon(new ImageIcon(App.class.getResource("/img/New File.png")));
        add(btn);

        btn = new JButton("");
        btn.addActionListener(e -> methods[MenuOptions.OPEN.getId()].get());
        btn.setIcon(new ImageIcon(App.class.getResource("/img/Open Project.png")));
        add(btn);

        btn = new JButton("");
        btn.addActionListener(e -> methods[MenuOptions.SAVE.getId()].get());
        btn.setIcon(new ImageIcon(App.class.getResource("/img/Save File.png")));
        add(btn);

        addSeparator();

        btn = new JButton("");
        btn.addActionListener(e -> methods[MenuOptions.UNDO.getId()].get());
        btn.setIcon(new ImageIcon(App.class.getResource("/img/Undo.PNG")));
        add(btn);

        btn = new JButton("");
        btn.addActionListener(e -> methods[MenuOptions.REDO.getId()].get());
        btn.setIcon(new ImageIcon(App.class.getResource("/img/Redo.PNG")));
        add(btn);

        addSeparator();

        btn = new JButton("");
        btn.addActionListener(e -> methods[MenuOptions.CUT.getId()].get());
        btn.setIcon(new ImageIcon(App.class.getResource("/img/Cut.PNG")));
        add(btn);

        btn = new JButton("");
        btn.addActionListener(e -> methods[MenuOptions.COPY.getId()].get());
        btn.setIcon(new ImageIcon(App.class.getResource("/img/Copy.PNG")));
        add(btn);

        btn = new JButton("");
        btn.addActionListener(e -> methods[MenuOptions.PASTE.getId()].get());
        btn.setIcon(new ImageIcon(App.class.getResource("/img/Paste.png")));
        add(btn);

        addSeparator();

        btn = new JButton("");
        btn.addActionListener(e -> methods[MenuOptions.COMPILE_RUN.getId()].get());
        btn.setIcon(new ImageIcon(App.class.getResource("/img/CR.png")));
        add(btn);

        btn = new JButton("");
        btn.addActionListener(e -> methods[MenuOptions.COMPILE.getId()].get());
        btn.setIcon(new ImageIcon(App.class.getResource("/img/Cog.png")));
        add(btn);

        btn = new JButton("");
        btn.addActionListener(e -> methods[MenuOptions.RUN.getId()].get());
        btn.setIcon(new ImageIcon(App.class.getResource("/img/Run.PNG")));
        add(btn);

        btn = new JButton("");
        btn.addActionListener(e -> methods[MenuOptions.STOP.getId()].get());
        btn.setIcon(new ImageIcon(App.class.getResource("/img/Stop.PNG")));
        add(btn);
    }

}
