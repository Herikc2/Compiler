package br.univali.ttoproject.ide.components.editor;

import br.univali.ttoproject.ide.components.settings.FontTheme;
import br.univali.ttoproject.ide.components.settings.Settings;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.Stack;

public class CodeEditor extends JTextPane {

    private final Stack<State> undoStates;
    private final Stack<State> redoStates;
    private boolean hasChanges = false;

    public CodeEditor() {
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
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClicked(e);
            }
        });

    }

    public String getText() {
        String text = super.getText();
        text = text.replaceAll("\r\n", "\n");
        return text;
    }

    public void setText(String t) {
        super.setText(t);
        syntaxHighlight();
    }

    public void syntaxHighlight() {
        if (!Settings.SYNTAX_HIGHLIGHT) return;
        var text = getText();
        var length = text.length();
        StringBuilder word = new StringBuilder();
        setDefaultColor();
        changeColor(FontTheme.COLOR_DEFAULT, 0, length);
        for (int i = 0; i < length; ++i) {
            char c = text.charAt(i);
            if (Token.isSpec(c) || Token.isNumber(c)) {
                var si = i + 1;
                var len = 0;
                char endChar;
                boolean spec = false, str = false, num = false, hdr = false;
                if (Token.isNumber(c) && (word.length() == 0)) {
                    while ((i < length && Token.isNumber(text.charAt(i))) ||
                            (i < length && Token.isNumMate(text.charAt(i)) && !Token.isNumSep(text.charAt(i)) &&
                                    i < length - 1 && Token.isNumber(text.charAt(i + 1))) ||
                            (i < length && text.charAt(i) == '*' &&
                                    i < length - 1 && text.charAt(i + 1) == '*' &&
                                    i < length - 2 && Token.isNumber(text.charAt(i + 2))) ||
                            (i < length && text.charAt(i) == '%' &&
                                    i < length - 1 && text.charAt(i + 1) == '%' &&
                                    i < length - 2 && Token.isNumber(text.charAt(i + 2)))) ++i;
                    --i;
                    spec = true;
                    num = true;
                } else if (c == '/' && i < length - 1 && text.charAt(i + 1) == '*') {
                    i += 2;
                    while ((i < length && text.charAt(i) != '*') || (i < length - 1 && text.charAt(i + 1) != '/')) ++i;
                    ++i;
                    spec = true;
                } else if (c == '/' && i < length - 1 && text.charAt(i + 1) == '/') {
                    ++i;
                    endChar = '\n';
                    while (i < length && text.charAt(i) != endChar) ++i;
                    spec = true;
                } else if (c == '\'' || c == '"') {
                    ++i;
                    endChar = c;
                    while ((i < length && text.charAt(i) != endChar) ||
                            (i - 1 >= 0 && i - 1 < length && text.charAt(i - 1) == '\\')) ++i;
                    spec = true;
                    str = true;
                } else if (c == ':' && i < length - 1 && text.charAt(i + 1) == '-') {
                    ++i;
                    endChar = '\n';
                    while (i < length && text.charAt(i) != endChar) ++i;
                    spec = true;
                    hdr = true;
                }
                if (spec) {
                    len = i;
                    Color color;
                    if (str) color = FontTheme.COLOR_STRING;
                    else if (num) color = FontTheme.COLOR_NUMBER;
                    else if (hdr) color = FontTheme.COLOR_HEADER;
                    else color = FontTheme.COLOR_COMMENTS;
                    changeColor(color, si - 1, (len - si) + 2);
                    word = new StringBuilder();
                    continue;
                }
            }
            if (Token.isSymbol(c)) {
                changeColor(FontTheme.COLOR_SPECIAL, i, (i + 1) - i);
            }
            if (Token.isSkip(c) || i == length - 1) {
                if (i == length - 1 && !Token.isSkip(c)) {
                    word.append(c);
                    ++i;
                }
                if (Token.isReserved(word.toString())) {
                    var from = i - word.length();
                    var to = i - from;
                    changeColor(FontTheme.COLOR_RESERVED, from, to);
                }
                word = new StringBuilder();
            } else {
                word.append(c);
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
            TabSizeEditorKit.SPACE_WIDTH = (float) (Settings.FONT.getStringBounds(" ", frc).getWidth());
        }
    }

    private void handleKeyReleased(KeyEvent e) {
//        if (!(e.isActionKey() || e.isControlDown() || e.isAltDown() ||
//                e.isShiftDown() || e.isAltGraphDown() || e.isMetaDown())) {
//            if (Settings.SYNTAX_HIGHLIGHT)
//                syntaxHighlight();
//        }
        syntaxHighlight();
    }

    private void handleKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == '\t') {
            if (Settings.TAB_TYPE == Settings.TT_SPACES) {
                e.consume();
                var curCaretPosition = getCaretPosition();
                var tabSize = Settings.TAB_SIZE;
                var t1 = getText().substring(0, curCaretPosition);
                var t2 = getText().substring(curCaretPosition);

                var rowStart = 0;
                try {
                    rowStart = Utilities.getRowStart(this, curCaretPosition);
                } catch (BadLocationException err) {
                    err.printStackTrace();
                }
                var lineSize = t1.substring(rowStart, curCaretPosition).length();
                if (lineSize < Settings.TAB_SIZE) {
                    tabSize -= lineSize;
                } else if (lineSize > Settings.TAB_SIZE) {
                    var calc = lineSize % Settings.TAB_SIZE;
                    tabSize = calc != 0 ? tabSize - calc : tabSize;
                }

                setText(t1 + " ".repeat(tabSize) + t2);
                setCaretPosition(curCaretPosition + tabSize);
            }
        } else if (e.getKeyCode() == '\b') {
            if (Settings.TAB_TYPE == Settings.TT_SPACES) {
                var curCaretPosition = getCaretPosition();
                var tabSize = Settings.TAB_SIZE;
                var t1 = getText().substring(0, curCaretPosition);
                var t2 = getText().substring(curCaretPosition);

                var rowStart = 0;
                try {
                    rowStart = Utilities.getRowStart(this, curCaretPosition);
                } catch (BadLocationException err) {
                    err.printStackTrace();
                }
                // pega a linha e tamanho
                var line = t1.substring(rowStart, curCaretPosition);
                var lineSize = line.length();

                if (line.chars().filter(ch -> ch == ' ').count() == lineSize) {
                    e.consume();
                    if (lineSize < Settings.TAB_SIZE) {
                        tabSize -= lineSize;
                    } else if (lineSize > Settings.TAB_SIZE) {
                        var calc = lineSize % Settings.TAB_SIZE;
                        tabSize = calc != 0 ? tabSize - calc : tabSize;
                    }

                    setText(t1.substring(0, t1.length() - (tabSize)) + t2);
                    setCaretPosition(curCaretPosition - tabSize);
                }
            }
        }
    }

    private void handleKeyTyped(KeyEvent e) {
        if (!e.isControlDown()) {
            hasChanges = true;
            undoStates.push(new State(getText(), getCaretPosition()));
            coder(e);
        }
    }

    private void handleMouseClicked(MouseEvent event) {
        if (event.getButton() == MouseEvent.BUTTON3) {
            var keyCtrl = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();
            var popupMenu = new JPopupMenu();
            JMenuItem menuItem;

            menuItem = new JMenuItem("Cut");
            menuItem.addActionListener(e -> cut());
            menuItem.setAccelerator(KeyStroke.getKeyStroke('X', keyCtrl));
            popupMenu.add(menuItem);

            menuItem = new JMenuItem("Copy");
            menuItem.addActionListener(e -> copy());
            menuItem.setAccelerator(KeyStroke.getKeyStroke('C', keyCtrl));
            popupMenu.add(menuItem);

            menuItem = new JMenuItem("Paste");
            menuItem.addActionListener(e -> paste());
            menuItem.setAccelerator(KeyStroke.getKeyStroke('V', keyCtrl));
            popupMenu.add(menuItem);

            popupMenu.addSeparator();

            menuItem = new JMenuItem("Select All");
            menuItem.addActionListener(e -> selectAll());
            menuItem.setAccelerator(KeyStroke.getKeyStroke('A', keyCtrl));
            popupMenu.add(menuItem);

            popupMenu.show(event.getComponent(), event.getX(), event.getY());
        }
    }

    private void coder(KeyEvent e) {
        var keyChar = e.getKeyChar();
        int braceTabLevel = getTabLevel();
        var isTab = Settings.TAB_TYPE == Settings.TT_TAB;
        var curCaretPosition = getCaretPosition();
        var caretPad = isTab ? 1 : Settings.TAB_SIZE;
        var tab = isTab ? "\t" : " ".repeat(Settings.TAB_SIZE);
        var t1 = getText().substring(0, getCaretPosition());
        var t2 = getText().substring(getCaretPosition());

        if (keyChar == '"' || keyChar == '\'' || keyChar == '[') {
            e.consume();
            if (keyChar == '[')
                setText(t1 + keyChar + ']' + t2);
            else
                setText(t1 + keyChar + keyChar + t2);
            setCaretPosition(curCaretPosition + 1);
        } else if (keyChar == ' ') {
            if (t1.endsWith("{")) {
                e.consume();
                var pad = 1;
                t1 = t1.substring(0, t1.length() - 1);
                if (!t1.endsWith(" ")) {
                    t1 += " ";
                    pad++;
                }
                setText(t1 + "{ " + " } ." + t2);
                setCaretPosition(curCaretPosition + pad);
            }
        } else if (keyChar == '\b') {
            if (t2.startsWith("'") || t2.startsWith("\"") || t2.startsWith("]")) {
                setText(t1 + t2.substring(1));
                setCaretPosition(curCaretPosition);
            }
        } else if (keyChar == '\n') {
            if (t1.endsWith("{\n")) {
                var pad = 0;
                t1 = t1.substring(0, t1.length() - 2);
                if (!t1.endsWith(" ")) {
                    t1 += " ";
                    pad++;
                }
                setText(t1 + "{\n" + tab.repeat(braceTabLevel) + "\n" + tab.repeat(braceTabLevel - 1) + "}" + t2);
                setCaretPosition(curCaretPosition + (caretPad * braceTabLevel) + pad);
            } else if (t1.endsWith("/**\n")) {
                setText(t1 + tab.repeat(braceTabLevel) + " * \n" + tab.repeat(braceTabLevel) + " */" + t2);
                setCaretPosition(curCaretPosition + (caretPad * braceTabLevel) + 3);
            } else {
                // mantém o nível do tab quando teclado enter
                // prioridade: nível do tab atual -> chaves abertas
                var curTabLevel = 0;
                var rowStart = 0;
                try {
                    rowStart = Utilities.getRowStart(this, curCaretPosition - 1);
                } catch (BadLocationException err) {
                    err.printStackTrace();
                }
                var chars = t1.substring(rowStart, curCaretPosition - 1).chars();
                if (isTab)
                    curTabLevel = (int) chars.filter(ch -> ch == '\t').count();
                else
                    curTabLevel = (int) chars.filter(ch -> ch == ' ').count() / Settings.TAB_SIZE;
                braceTabLevel = Math.max(curTabLevel, braceTabLevel);

                setText(t1 + tab.repeat(braceTabLevel) + t2);
                setCaretPosition(curCaretPosition + (caretPad * braceTabLevel));
            }
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

    public void undo() {
        if (canUndo()) {
            redoStates.add(new State(getText(), getCaretPosition()));
            var state = undoStates.pop();
            setText(state.getT());
            setCaretPosition(state.getP());
        } else {
            hasChanges = true;
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
