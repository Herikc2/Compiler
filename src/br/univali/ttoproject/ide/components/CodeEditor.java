package br.univali.ttoproject.ide.components;

import br.univali.ttoproject.ide.components.Settings.Settings;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Stack;
import java.util.function.Consumer;

public class CodeEditor extends JTextArea {

    private Stack<String> undoStates;
    private Stack<String> redoStates;

    public CodeEditor(Consumer<Object> lcListener, Consumer<Object> changesListener) {
        setTabSize(4);
        // verifica se é windows ou linux para setar a fonte
        setFont(Settings.FONT);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                changesListener.accept(null);
            }
        });
        addCaretListener(e -> lcListener.accept(null));
    }

    public void undo() {

    }

    public void redo() {

    }

}
