package br.univali.ttoproject.ide.components.Settings;

import javax.swing.*;

public class AppearanceSettings {
    private JPanel panelMain;
    private JCheckBox ckbToolBar;
    private JCheckBox ckbConsole;
    private JCheckBox ckbStatusBar;

    public AppearanceSettings() {

    }

    public JPanel getPanelMain() {
        return panelMain;
    }

    public JCheckBox getCkbToolBar() {
        return ckbToolBar;
    }

    public JCheckBox getCkbConsole() {
        return ckbConsole;
    }

    public JCheckBox getCkbStatusBar() {
        return ckbStatusBar;
    }
}
