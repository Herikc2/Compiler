package br.univali.ttoproject.ide.components;

import br.univali.ttoproject.ide.util.Debug;

import javax.swing.*;
import javax.swing.text.DefaultStyledDocument;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Console extends JTextArea {

    private boolean allowConsoleInput = false;
    private int finalAllowedArea;
    private int initialAllowedArea;

    private volatile boolean inputReady = false;
    private String input = "";

    public Console() {
        //setTabSize(4);
        //setFont(Settings.FONT);
        setFocusTraversalKeysEnabled(false);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                handleKeyTyped(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPressed(e);
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClicked(e);
            }
        });
    }

    public void addContent(String content) {
        try {
            append(content);
            setCaretPosition(getText().length());
        } catch (Error e) {
            Debug.print(e.getMessage());
        }
    }

    public void reset() {
        setText("");
        allowConsoleInput = false;
    }

    public void initDataEntry() {
        requestFocusInWindow();
        allowConsoleInput = true;
        initialAllowedArea = getText().length();
        finalAllowedArea = initialAllowedArea;
        inputReady = false;
        input = "";
    }

    public void stopDataEntry() {
        allowConsoleInput = false;
    }

    private void handleKeyTyped(KeyEvent e) {
        // caso o console esteja recebendo dados
        if (allowConsoleInput) {
            var curCaretPosition = getCaretPosition();
            // antes do input do teclado, é verificado se a posição do caret está fora da área permitida
            if (curCaretPosition < initialAllowedArea || curCaretPosition > finalAllowedArea) {
                // se não tiver, o caret é posicionado ao final da área permitida
                setCaretPosition(finalAllowedArea);
            }
            // aumenta o comprimento da área permitida em um caractere
            finalAllowedArea++;
        } else {
            e.consume();
        }
    }

    private void handleKeyPressed(KeyEvent e) {
        var keyCode = e.getKeyCode();
        var curCaretPosition = getCaretPosition();

        if (keyCode == KeyEvent.VK_BACK_SPACE) {
            // manipula backspace
            if (!allowConsoleInput) {
                // se o console tiver desabilitado o caractere apenas é consumido
                e.consume();
            } else if (curCaretPosition > initialAllowedArea) {
                // se for acima da posição inicial da área permite que o caractere seja apagado e diminui o comprimento
                // da área permitida
                finalAllowedArea--;
            } else {
                // qualquer outro caso consome o caractere
                e.consume();
            }
        } else if (keyCode == KeyEvent.VK_ENTER) {
            // o enter sempre será consumido
            e.consume();
            // caso o enter seja pressionado enquanto o console está ativo, o dado é enviado para a função de retorno
            if (allowConsoleInput) {
                stopDataEntry();
                input = getText().substring(initialAllowedArea);
                inputReady = true;
                setCaretPosition(getText().length());
            }
        } else if (keyCode == KeyEvent.VK_TAB) {
            // tab será sempre consumido
            e.consume();
        }
    }

    private void handleMouseClicked(MouseEvent event) {
        if (event.getButton() == MouseEvent.BUTTON3) {
            var popupMenu = new JPopupMenu();
            JMenuItem menuItem;

            menuItem = new JMenuItem("Copy");
            menuItem.addActionListener(e -> copy());
            popupMenu.add(menuItem);

            popupMenu.addSeparator();

            menuItem = new JMenuItem("Clear All");
            menuItem.addActionListener(e -> reset());
            popupMenu.add(menuItem);

            popupMenu.show(event.getComponent(), event.getX(), event.getY());
        }
    }

    public boolean isInputReady() {
        return inputReady;
    }

    public String getInput() {
        return input;
    }
}
