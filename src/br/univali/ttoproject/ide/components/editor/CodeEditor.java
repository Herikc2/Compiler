package br.univali.ttoproject.ide.components.editor;

import br.univali.ttoproject.ide.components.Settings.FontTheme;
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
            public void keyPressed(KeyEvent e) {
                handleKeyPressed(e);
            }

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
        if (Settings.SYNTAX_HIGHLIGHT)
            syntaxHighlight();
    }

    private void syntaxHighlight() {
        var text = getText();
        var length = text.length();
        var word = "";
        setDefaultColor();
        changeColor(FontTheme.COLOR_DEFAULT, 0, length);
        for (int i = 0; i < length; ++i) {
            char c = text.charAt(i);
            if (Token.isSpec(c)) {
                var si = i + 1;
                var len = 0;
                char endChar;
                if (c == '/' && i < length - 1 && text.charAt(i + 1) == '*') {
                    i += 2;
                    while ((i < length && text.charAt(i) != '*') || (i < length - 1 && text.charAt(i + 1) != '/')) ++i;
                    i++;
                    len = i;
                    changeColor(FontTheme.COLOR_COMMENTS, si - 1, (len - si) + 2);
                    word = "";
                    continue;
                } else if (c == '\"') {
                    ++i;
                    endChar = '"';
                    while (i < length && text.charAt(i) != endChar) ++i;
                    len = i;
                    changeColor(FontTheme.COLOR_STRING, si - 1, (len - si) + 2);
                    word = "";
                    continue;
                } else if (c == ':' && i < length - 1 && text.charAt(i + 1) == '-') {
                    ++i;
                    endChar = '\n';
                    while (i < length && text.charAt(i) != endChar) ++i;
                    len = i;
                    changeColor(FontTheme.COLOR_COMMENTS, si - 1, (len - si) + 2);
                    word = "";
                    continue;
                }
            }
            if (Token.isSymbol(c)) {
                changeColor(FontTheme.COLOR_SPECIAL, (i + 1) - 1, (i + 1) - i);
            }
            if (Token.isNumber(c)) {
                changeColor(FontTheme.COLOR_NUMBER, (i + 1) - 1, (i + 1) - i);
            }
            if (Token.isSkip(c)) {
                if (Token.isReserved(word)) {
                    changeColor(FontTheme.COLOR_RESERVED, i - word.length(), i - (i - word.length()));
                }
                word = "";
            } else if (i == length - 1) {
                word += c;
                ++i;
                if (Token.isReserved(word)) {
                    changeColor(FontTheme.COLOR_RESERVED, i - word.length(), i - (i - word.length()));
                }
            } else {
                word += c;
            }
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
        StyleConstants.setForeground(sas, FontTheme.COLOR_DEFAULT);
        setCharacterAttributes(sas, false);
    }

    public void setTabSize(int size) {
        Settings.TAB_SIZE = size;
        if (Settings.TAB_TYPE == Settings.TT_TAB) {
            var at = new AffineTransform();
            var frc = new FontRenderContext(at, false, true);
            //Debug.print(""+((float) (Settings.FONT.getStringBounds(" ", frc).getWidth())));
            TabSizeEditorKit.SPACE_WIDTH = (float) (Settings.FONT.getStringBounds(" ", frc).getWidth());
        }
    }

    private void handleKeyTyped(KeyEvent e) {
        if (!e.isControlDown()) {
            hasChanges = true;
            changesListener.accept(null);
            undoStates.push(new State(getText(), getCaretPosition()));
            coder(e);
        }
    }

    private void coder(KeyEvent e){
        var kChar = e.getKeyChar();
        int tabLevel = getTabLevel();
        if(kChar == '{'){
            ++tabLevel;
            e.consume();
            var isTab = Settings.TAB_TYPE == Settings.TT_TAB;
            var curCaretPosition = getCaretPosition();
            var pad = 2;
            var caretPad = isTab ? 1 : Settings.TAB_SIZE;
            var t1 = getText().substring(0, getCaretPosition());
            var t2 = getText().substring(getCaretPosition());
            var tab = isTab ? "\t" : " ".repeat(Settings.TAB_SIZE);
            if(!t1.endsWith(" ")) {
                t1 += " ";
                pad++;
            }
            setText(t1 + "{\n" + tab.repeat(tabLevel) + "\n" + tab.repeat(tabLevel-1) + "}" + t2);
            setCaretPosition(curCaretPosition + (caretPad * tabLevel) + pad);
        } else if(kChar == '\n'){
            e.consume();
            var isTab = Settings.TAB_TYPE == Settings.TT_TAB;
            var curCaretPosition = getCaretPosition();
            var caretPad = isTab ? 1 : Settings.TAB_SIZE;
            var t1 = getText().substring(0, getCaretPosition());
            var t2 = getText().substring(getCaretPosition());
            var tab = isTab ? "\t" : " ".repeat(Settings.TAB_SIZE);
            setText(t1 + tab.repeat(tabLevel) + t2);
            setCaretPosition(curCaretPosition + (caretPad * tabLevel));
        }
    }

    private int getTabLevel() {
        var text = getText().substring(0, getCaretPosition());
        int tabLevel = 0;
        for (int i = 0; i < text.length(); ++i) {
            var c = text.charAt(i);
            if (c == '{') tabLevel++;
            else if (c == '}') tabLevel--;
            if (tabLevel < 0) tabLevel = 0;
        }
        return tabLevel;
    }

    private void handleKeyReleased(KeyEvent e) {
        if (!(e.isActionKey() || e.isControlDown() || e.isAltDown() ||
                e.isShiftDown() || e.isAltGraphDown() || e.isMetaDown())) {
            if (Settings.SYNTAX_HIGHLIGHT)
                syntaxHighlight();
        }
    }

    private void handleKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_TAB) {
            if (Settings.TAB_TYPE == Settings.TT_SPACES) {
                e.consume();
                var caretPosition = getCaretPosition();
                var tabSize = Settings.TAB_SIZE;
                var text1 = getText().substring(0, caretPosition);
                var text2 = getText().substring(caretPosition);
                // TODO: verify incomplete tab cases
                setText(text1 + " ".repeat(tabSize) + text2);
                setCaretPosition(caretPosition + tabSize);
            }
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

    public boolean canUndo() {
        return undoStates.size() > 0;
    }

    public boolean canRedo() {
        return redoStates.size() > 0;
    }

    public boolean hasChanges() {
        return hasChanges;
    }

}
