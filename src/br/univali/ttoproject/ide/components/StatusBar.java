package br.univali.ttoproject.ide.components;

import javax.swing.*;
import java.awt.*;

public class StatusBar extends JPanel {
    public StatusBar() {
        setMinimumSize(new Dimension(10, 16));
        setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
    }

    @Override
    public Component add(Component comp) {
        // como os componentes da status bar são dinâmicos, eles são adicionados fora da classe
        JSeparator separator = new JSeparator();
        separator.setPreferredSize(new Dimension(5, 14));
        separator.setMaximumSize(new Dimension(5, 14));
        separator.setOrientation(SwingConstants.VERTICAL);
        super.add(separator);
        return super.add(comp);
    }
}
