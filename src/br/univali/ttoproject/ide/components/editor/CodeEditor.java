package br.univali.ttoproject.ide.components.editor;

import br.univali.ttoproject.ide.components.Settings.Settings;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Stack;
import java.util.function.Consumer;

public class CodeEditor extends JTextPane {

    Consumer<Object> changesListener;

    private final Stack<State> undoStates;
    private final Stack<State> redoStates;

    private boolean hasChanges = false;

    public CodeEditor(Consumer<Object> lcListener, Consumer<Object> changesListener) {
        this.changesListener = changesListener;
        undoStates = new Stack<>();
        redoStates = new Stack<>();
        setTabSize(4);

        setFont(Settings.FONT);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                handleKeyReleased(e);
            }
            @Override
            public void keyTyped(KeyEvent e) {
                handleKeyTyped(e);
            }
        });
        addCaretListener(e -> lcListener.accept(null));
    }

    public void syntaxHighlightParser(){
        var text = getText();
        var length = text.length();
        var word = "";
        for(int i = 0; i < length; ++i){
            char c = text.charAt(i);
            if (Token.isSkip(c)) {
                if(Token.isReserved(word)){
                    changeColor(Settings.COLOR_RESERVED, i - word.length(), i - (i - word.length()));
                }
                word = "";
            } else if (i == length - 1) {
                word += c;
                ++i;
                if(Token.isReserved(word)){
                    changeColor(Settings.COLOR_RESERVED, i - word.length(), i - (i - word.length()));
                }
            } else {
                word += c;
            }
        }
    }

    public void changeColor(Color c, int beginIndex, int length) {
        var sas = new SimpleAttributeSet();
        StyleConstants.setForeground(sas, c);
        var doc = (StyledDocument)getDocument();
        doc.setCharacterAttributes(beginIndex, length, sas, false);
        sas = new SimpleAttributeSet();
        StyleConstants.setForeground(sas, Settings.COLOR_DEFAULT);
        setCharacterAttributes(sas, false);
    }

    public void setTabSize(int size) {
//        Document doc = getDocument();
//        if (doc != null) {
//            int old = getTabSize();
//            doc.putProperty(PlainDocument.tabSizeAttribute, Integer.valueOf(size));
//            firePropertyChange("tabSize", old, size);
//        }
    }

    public int getTabSize() {
        int size = 8;
//        Document doc = getDocument();
//        if (doc != null) {
//            Integer i = (Integer) doc.getProperty(PlainDocument.tabSizeAttribute);
//            if (i != null) {
//                size = i.intValue();
//            }
//        }
        return size;
    }

    private void handleKeyTyped(KeyEvent e) {
        if (!e.isControlDown()) {
            hasChanges = true;
            changesListener.accept(null);
            undoStates.push(new State(getText(), getCaretPosition()));
        }
    }

    private void handleKeyReleased(KeyEvent e) {
        syntaxHighlightParser();
    }

    public void undo() {
        if (canUndo()) {
            redoStates.add(new State(getText(), getCaretPosition()));
            var state = undoStates.pop();
            setText(state.getT());
            setCaretPosition(state.getP());
        } else {
            hasChanges = true;
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

//    public String getText() {
//        String text = super.getText();
//        if (Settings.LINE_ENDING == Settings.LNE_CRLF) {
//            text = text.replaceAll("![\r]\n", "\r\n");
//        } else {
//            text = text.replaceAll("\r\n", "\n");
//        }
//        return text;
//    }

    private boolean canUndo() {
        return undoStates.size() > 0;
    }

    private boolean canRedo() {
        return redoStates.size() > 0;
    }

    public boolean hasChanges() {
        return hasChanges;
    }

}
