package br.univali.ttoproject.ide.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Stack;
import java.util.function.Consumer;

public class CodeEditor extends JTextArea {

    private Stack<String> undoStates;
    private Stack<String> redoStates;

    public CodeEditor(Consumer<Object> lcListener, Consumer<Object> changesListener){
        setTabSize(4);
        // verifica se é windows ou linux para setar a fonte
        if (System.getProperty("os.name").substring(0, 3).equalsIgnoreCase("win")) {
            setFont(new Font("Consolas", Font.PLAIN, 14));
        } else {
            setFont(new Font("FreeMono", Font.PLAIN, 15));
        }
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                changesListener.accept(null);
            }
        });
        addCaretListener(e -> lcListener.accept(null));
    }

    public void undo(){

    }

    public void redo(){

    }

}
