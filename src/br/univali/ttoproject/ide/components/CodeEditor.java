package br.univali.ttoproject.ide.components;

import br.univali.ttoproject.ide.components.Settings.Settings;
import br.univali.ttoproject.ide.util.Debug;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Stack;
import java.util.function.Consumer;

public class CodeEditor extends JTextArea {

    Consumer<Object> changesListener;

    private final Stack<State> undoStates;
    private final Stack<State> redoStates;

    private boolean hasChanges = false;

    private int tabLevel = 0;

    public CodeEditor(Consumer<Object> lcListener, Consumer<Object> changesListener) {
        this.changesListener = changesListener;
        undoStates = new Stack<>();
        redoStates = new Stack<>();
        setTabSize(4);
        setFont(Settings.FONT);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                handleKeyTyped(e);
            }
        });
        addCaretListener(e -> lcListener.accept(null));
    }

    private void handleKeyTyped(KeyEvent e){
        hasChanges =  true;
        changesListener.accept(null);
        if (!e.isControlDown()){
            undoStates.push(new State(getText(), getCaretPosition()));
        }
        feat(e);
    }

    private void feat(KeyEvent e){
//        if (e.getKeyChar() == '{'){
//            tabLevel++;
//            StringBuilder text = new StringBuilder(getText());
//            text.append("\n");
//            text.append("\t".repeat(Math.max(0, tabLevel)));
//            text.append("\n}");
//            setText(text.toString());
//            setCaretPosition(getCaretPosition() - 2);
//        }
    }

    public void undo() {
        if (canUndo()) {
            redoStates.add(new State(getText(), getCaretPosition()));
            var state = undoStates.pop();
            setText(state.getT());
            setCaretPosition(state.getP());
        } else {
            hasChanges =  true;
            changesListener.accept(null);
        }
    }

    public void redo() {
        if (canRedo()) {
            undoStates.add(new State(getText(), getCaretPosition()));
            var state = redoStates.pop();
            setText(state.getT());
            setCaretPosition(state.getP());
        }
    }

    private boolean canUndo() {
        return undoStates.size() > 0;
    }

    private boolean canRedo() {
        return redoStates.size() > 0;
    }

    public boolean hasChanges() {
        return hasChanges;
    }

    private class State {

        private String t;
        private int p;

        public State(String t, int p) {
            this.t=t;
            this.p=p;
        }

        public String getT() {
            return t;
        }

        public void setT(String t) {
            this.t = t;
        }

        public int getP() {
            return p;
        }

        public void setP(int p) {
            this.p = p;
        }
    }
}
