package br.univali.ttoproject.ide.components;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LogPane extends JTextArea {

    public LogPane(){
        setEditable(false);
        addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { handleMouseClicked(e); }
        });
    }

    public void addContent(String content) {
        setText(getText() + content);
    }

    public void reset() {
        setText("");
    }

    private void handleMouseClicked(MouseEvent event){
        if (event.getButton() == MouseEvent.BUTTON3) {
            var popupMenu = new JPopupMenu();
            JMenuItem menuItem;

            menuItem = new JMenuItem("Copy");
            menuItem.addActionListener(e -> copy());
            popupMenu.add(menuItem);

            popupMenu.addSeparator();

            menuItem = new JMenuItem("Clear All");
            menuItem.addActionListener(e -> setText(""));
            popupMenu.add(menuItem);

            popupMenu.show(event.getComponent(), event.getX(), event.getY());
        }
    }
}
