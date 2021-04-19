package br.univali.ttoproject.ide.components;

import br.univali.ttoproject.ide.components.Settings.Settings;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Console extends JTextArea {

    private boolean allowConsoleInput = false;
    private int finalAllowedArea;
    private int initialAllowedArea;

    private volatile boolean inputReady = false;
    private String input = "";

    public Console() {
        setTabSize(4);
//        if (Settings.CURRENT_SO == Settings.SO_WINDOWS) {
//            setFont(new Font("Consolas", Font.PLAIN, 16));
//        } else {
//            setFont(new Font("FreeMono", Font.PLAIN, 17));
//        }
        setFont(Settings.FONT);
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
    }

    public void addContent(String content) {
        setText(getText() + content);
        setCaretPosition(getText().length());
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

    private void stopDataEntry() {
        allowConsoleInput = false;
    }

    private void handleKeyTyped(KeyEvent e) {
        // caso o console esteja recebendo dados
        if (allowConsoleInput) {
            var curCaretPosition = getCaretPosition();
            // antes do input do teclado, é verificado se a posição do caret está fora da área perminida
            if (curCaretPosition < initialAllowedArea || curCaretPosition > finalAllowedArea) {
                // se não tiver, o caret é posicionado ao final da área permitida
                setCaretPosition(finalAllowedArea);
            }
            // aumenta o compimento da área permitida em um caracter
            finalAllowedArea++;
        } else {
            e.consume();
        }
    }

    private void handleKeyPressed(KeyEvent e) {
        var keyChar = e.getKeyCode();
        var curCaretPosition = getCaretPosition();

        if (keyChar == KeyEvent.VK_BACK_SPACE) {
            // manipula backspace
            if (!allowConsoleInput) {
                // se o console tiver desabilitado o caractere apenas é consumido
                e.consume();
            } else if (curCaretPosition > initialAllowedArea) {
                // se for acima da posição inicial da área permite que o caractere seja apagado e diminui o comprimento
                // da área permitida
                finalAllowedArea--;
            } else {
                // qualquer outro caso consome o caracter
                e.consume();
            }
        } else if (keyChar == KeyEvent.VK_ENTER) {
            // o enter sempre será consumido
            e.consume();
            // caso o enter seja pressionado enquanto o console está ativo, o dado é enviado para a função de retorno
            if (allowConsoleInput) {
                stopDataEntry();
                input = getText().substring(initialAllowedArea);
                inputReady = true;
                setCaretPosition(getText().length());
            }
        } else if (keyChar == KeyEvent.VK_TAB) {
            // tab será sempre consumido
            e.consume();
        }
    }

    public boolean isInputReady() {
        return inputReady;
    }

    public String getInput() {
        return input;
    }
}
