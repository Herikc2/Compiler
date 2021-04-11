package br.univali.ttoproject.ide.components.Settings;

import br.univali.ttoproject.ide.components.JFontChooser;

import javax.swing.*;
import java.awt.*;

public class FontSettings {
    private JPanel panelMain;
    private JFontChooser fontChooser;

    public FontSettings(){
        fontChooser = new JFontChooser();
        panelMain.add(fontChooser, BorderLayout.CENTER);
    }

    public JPanel getPanelMain() {
        return panelMain;
    }

    public JFontChooser getFontChooser() {
        return fontChooser;
    }
}
