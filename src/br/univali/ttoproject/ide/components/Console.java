package br.univali.ttoproject.ide.components;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

public class Console extends JTextArea {

    private final Consumer<String> returnFunction;

    private boolean allowConsoleInput = false;
    private int finalAllowedArea;
    private int initialAllowedArea;

    public Console(Consumer<String> returnFunction) {
        this.returnFunction = returnFunction;
        setTabSize(4);
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
    }

    public void reset() {
        setText("");
        allowConsoleInput = false;
    }

    public void initDataEntry(String content) {
        addContent(content);
        requestFocusInWindow();
        allowConsoleInput = true;
        finalAllowedArea = getCaretPosition();
        initialAllowedArea = finalAllowedArea;
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
                returnFunction.accept(getText().substring(initialAllowedArea));
                setCaretPosition(getText().length());
            }
        } else if (keyChar == KeyEvent.VK_TAB) {
            // tab será sempre consumido
            e.consume();
        }
    }

}
