package br.univali.ttoproject.ide.components.editor;

import br.univali.ttoproject.ide.components.Settings.Settings;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
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

        setEditorKit(new TabSizeEditorKit());
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

//    public String getText() {
//        String text = super.getText();
//        if (Settings.LINE_ENDING == Settings.LNE_CRLF) {
//            text = text.replaceAll("![\r]\n", "\r\n");
//        } else {
//            text = text.replaceAll("\r\n", "\n");
//        }
//        return text;
//    }

    public void setText(String t) {
        super.setText(t);
        syntaxHighlightParser();
    }

    private void syntaxHighlightParser() {
        var text = getText();
        var length = text.length();
        var word = "";
        setDefaultColor();
        for (int i = 0; i < length; ++i) {
            //Debug.print("loop");
            char c = text.charAt(i);
            if (Token.isSymbol(c)) {
                //Debug.print(c);
                changeColor(Settings.COLOR_SPECIAL, (i + 1) - 1, (i + 1) - (i - 1));
            }
            if (Token.isNumber(c)) {
                changeColor(Settings.COLOR_NUMBER, (i + 1) - 1, (i + 1) - (i - 1));
            }
            if (Token.isSpec(word)) {
                var si = i;
                var len = 0;
                char endChar;
                if (word.equals("/*")) {
                    do{
                        ++i;
                    } while (i < length);
                } else {
                    if (word.equals("\"")) {
                        endChar = '"';
                        while (i < length && text.charAt(i) != endChar) ++i;
                        len = i;
                        changeColor(Settings.COLOR_STRING, si - 1, (len - si) + 2);
                    } else if (word.equals(":-")) {
                        endChar = '\n';
                        while (i < length && text.charAt(i) != endChar) ++i;
                        len = i;
                        changeColor(Settings.COLOR_COMMENTS, si - 1, (len - si) + 2);
                    }
                }
                word = "";
                continue;
            }
            if (Token.isSkip(c)) {
                checkTokens(word, i);
                word = "";
            } else if (i == length - 1) {
                checkTokens(word + c, ++i);
            } else {
                word += c;
            }
        }
        //Debug.print("fim\n\n");
    }

    private void checkTokens(String word, int i) {
        if (Token.isReserved(word)) {
            changeColor(Settings.COLOR_RESERVED, i - word.length(), i - (i - word.length()));
        }
    }

    private void changeColor(Color c, int beginIndex, int length) {
        var sas = new SimpleAttributeSet();
        StyleConstants.setForeground(sas, c);
        var doc = (StyledDocument) getDocument();
        doc.setCharacterAttributes(beginIndex, length, sas, false);
        setDefaultColor();
    }

    private void setDefaultColor() {
        var sas = new SimpleAttributeSet();
        sas = new SimpleAttributeSet();
        StyleConstants.setForeground(sas, Settings.COLOR_DEFAULT);
        setCharacterAttributes(sas, false);
    }

    public void setTabSize(int size) {
        var at = new AffineTransform();
        var frc = new FontRenderContext(at, true, true);
        TabSizeEditorKit.TAB_WIDTH = (int) (Settings.FONT.getStringBounds(" ", frc).getWidth()) * size;
    }

    private void handleKeyTyped(KeyEvent e) {
        if (!e.isControlDown()) {
            hasChanges = true;
            changesListener.accept(null);
            undoStates.push(new State(getText(), getCaretPosition()));
        }
    }

    private void handleKeyReleased(KeyEvent e) {
        if (!(e.isActionKey() || e.isControlDown() || e.isAltDown() || e.isShiftDown() || e.isAltGraphDown() || e.isMetaDown())) {
            syntaxHighlightParser();
        }
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
