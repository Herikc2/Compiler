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
import java.util.ArrayList;
import java.util.Locale;
import java.util.Stack;

public class CodeEditor extends JTextPane {

    private final Stack<State> undoStates;
    private final Stack<State> redoStates;
    private boolean hasChanges = false;

    private JPopupMenu pmSuggestions;
    private ArrayList<JMenuItem> suggestions;

    public CodeEditor() {
        undoStates = new Stack<>();
        redoStates = new Stack<>();
        setEditorKit(new TabSizeEditorKit());
        setTabSize(Settings.TAB_SIZE);
        setFont(Settings.FONT);

        addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) { handleKeyPressed(e); }
            @Override public void keyReleased(KeyEvent e) { handleKeyReleased(e); }
            @Override public void keyTyped(KeyEvent e) { handleKeyTyped(e); }
        });
        addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { handleMouseClicked(e); }
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

    private void handleKeyReleased(KeyEvent e) {
//        if (!(e.isActionKey() || e.isControlDown() || e.isAltDown() ||
//                e.isShiftDown() || e.isAltGraphDown() || e.isMetaDown())) {
//            if (Settings.SYNTAX_HIGHLIGHT)
//                syntaxHighlight();
//        }
        syntaxHighlight();
    }

    private void handleKeyPressed(KeyEvent e) {
        coderPressed(e);
    }

    private void handleKeyTyped(KeyEvent e) {
        if (!e.isControlDown()) {
            hasChanges = true;
            undoStates.push(new State(getText(), getCaretPosition()));
            coderTyped(e);
            suggestions(e);
        }
    }

    private void suggestions(KeyEvent e) {
        if (!Settings.SUGGESTIONS) return;

        var curLine = getCurrentLine() + e.getKeyChar();
        int i;
        var word = "";
        var matched = new ArrayList<String>();

        for (i = curLine.length() - 1; i >= 0; i--) {
            if (Token.isSkip(curLine.charAt(i)) || i == 0) {
                word = curLine.substring(i).trim();
                break;
            }
        }

        if (word.isEmpty()) {
            if (pmSuggestions != null)
                pmSuggestions.setVisible(false);
            pmSuggestions = null;
            suggestions = null;
        } else {
            for (var t : Token.getReserved()) {
                if (t.equalsIgnoreCase(word)) {
                    if (pmSuggestions != null)
                        pmSuggestions.setVisible(false);
                    pmSuggestions = null;
                    suggestions = null;
                    return;
                }
                if (t.startsWith(word.toLowerCase(Locale.ROOT))) {
                    if (t.equals("not")) {
                        matched.add("not variable");
                    }
                    matched.add(t);
                }
            }

            pmSuggestions = new JPopupMenu();
            suggestions = new ArrayList<>();
            for (var m : matched) {
                var menuItem = new JMenuItem(m);
                var wordLength = word.length();
                menuItem.addActionListener(ee -> insertSelectedWord(m, wordLength));
                pmSuggestions.add(menuItem);
                suggestions.add(menuItem);
            }
            pmSuggestions.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        var selected = MenuSelectionManager.defaultManager().getSelectedPath();
                        ((JMenuItem) selected[1]).doClick();
                        pmSuggestions.setVisible(false);
                        pmSuggestions = null;
                        suggestions = null;
                    }
                }
            });

            var fontMetrics = getFontMetrics(Settings.FONT);
            int fontHeight = fontMetrics.getHeight();
            int fontWidth = fontMetrics.stringWidth(" ");
            int x = fontWidth * ((int) getCaretCol() - word.length());
            int y = fontHeight * ((int) getText().substring(0, getCaretPosition()).chars().filter(ch -> ch == '\n').count() + 1) + (int) (fontHeight * 0.3);
            pmSuggestions.show(e.getComponent(), x, y);

            requestFocus();
        }
    }

    private void insertSelectedWord(String word, int size) {
        var caretPosition = getCaretPosition();
        int braceTabLevel = getTabLevel();
        var isTab = Settings.TAB_TYPE == Settings.TT_TAB;
        var caretPad = isTab ? 1 : Settings.TAB_SIZE;
        var tab = isTab ? "\t" : " ".repeat(Settings.TAB_SIZE);
        var struct = word;
        var offset = (word.length() - size);
        var t1 = getText().substring(0, caretPosition - size);
        var t2 = getText().substring(caretPosition);
        switch (word) {
            case "program", "define", "execute" -> {
                ++braceTabLevel;
                struct = word + " {\n" + tab.repeat(braceTabLevel) + "\n" + tab.repeat(braceTabLevel - 1) + "}";
                offset += 3 + (caretPad * braceTabLevel);
            }
            case "not variable", "variable" -> {
                ++braceTabLevel;
                struct = word + "\n" + tab.repeat(braceTabLevel);
                offset += 1 + (caretPad * braceTabLevel);
            }
            case "get", "put" -> {
                ++braceTabLevel;
                struct = word + " {  } .";
                offset += 3;
            }
            case "loop" -> {
                ++braceTabLevel;
                struct = word + " {\n" + tab.repeat(braceTabLevel) + "\n" + tab.repeat(braceTabLevel - 1) + "} while  is true .";
                offset += 12 + (caretPad * braceTabLevel) + (caretPad * (braceTabLevel - 1));
            }
            case "while" -> {
                ++braceTabLevel;
                struct = word + "  is true do {\n" + tab.repeat(braceTabLevel) + "\n" + tab.repeat(braceTabLevel - 1) + "} .";
                offset += 1;
            }
            case "verify" -> {
                ++braceTabLevel;
                struct = word + " \n" + tab.repeat(braceTabLevel) + "is true {\n" + tab.repeat(braceTabLevel + 1) + "\n" + tab.repeat(braceTabLevel) + "}\n" + tab.repeat(braceTabLevel) + "is false {\n" + tab.repeat(braceTabLevel + 1) + "\n" + tab.repeat(braceTabLevel) + "} .";
                offset += 1;
            }
            case "set" -> {
                ++braceTabLevel;
                struct = word + "  to  .";
                offset += 1;
            }
        }
        setText(t1 + struct + t2);
        setCaretPosition(caretPosition + offset);
    }

    private String getCurrentLine() {
        var rowStart = 0;
        try {
            rowStart = Utilities.getRowStart(this, getCaretPosition());
        } catch (BadLocationException err) {
            err.printStackTrace();
        }
        return getText().substring(rowStart, getCaretPosition());
    }

    private void coderPressed(KeyEvent e) {
        // handle non printable characters
        if (!Settings.CODING_HELP) return;

        var keyCode = e.getKeyCode();
        var tabSize = Settings.TAB_SIZE;
        int braceTabLevel = getTabLevel();
        var isTab = Settings.TAB_TYPE == Settings.TT_TAB;
        var caretPad = isTab ? 1 : Settings.TAB_SIZE;
        var tab = isTab ? "\t" : " ".repeat(Settings.TAB_SIZE);
        var curCaretPosition = getCaretPosition();
        var t1 = getText().substring(0, getCaretPosition());
        var t2 = getText().substring(getCaretPosition());

        if (keyCode == '\t') {
            if (Settings.TAB_TYPE == Settings.TT_SPACES) {
                e.consume();
                var lineSize = getCurrentLine().length();
                if (lineSize < Settings.TAB_SIZE) {
                    tabSize -= lineSize;
                } else if (lineSize > Settings.TAB_SIZE) {
                    var calc = lineSize % Settings.TAB_SIZE;
                    tabSize = calc != 0 ? tabSize - calc : tabSize;
                }

                setText(t1 + " ".repeat(tabSize) + t2);
                setCaretPosition(curCaretPosition + tabSize);
            }
        } else if (keyCode == '\b') {
            if (t1.endsWith("'") && t2.startsWith("'") || t1.endsWith("\"") && t2.startsWith("\"") ||
                    t1.endsWith("[") && t2.startsWith("]") || t1.endsWith("(") && t2.startsWith(")")) {
                setText(t1 + t2.substring(1));
                setCaretPosition(curCaretPosition);
            } else if (Settings.TAB_TYPE == Settings.TT_SPACES) {
                // handle space tab delete cases
                // pega a linha e tamanho
                var line = getCurrentLine();
                var lineSize = line.length();

                if (line.chars().filter(ch -> ch == ' ').count() == lineSize && lineSize > 0) {
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
        } else if (keyCode == '\n') {
            if (t1.endsWith("{") && (getTabLevel(t1) > Math.abs(getTabLevel(t2)))) {
                // handle block cases
                e.consume();
                var pad = 1;
                t1 = t1.substring(0, t1.length() - 1);
                if (!t1.endsWith(" ")) {
                    t1 += " ";
                    pad++;
                }
                setText(t1 + "{\n" + tab.repeat(braceTabLevel) + "\n" + tab.repeat(braceTabLevel - 1) + "}" + t2);
                setCaretPosition(curCaretPosition + (caretPad * braceTabLevel) + pad);
            } else if (t1.endsWith("/**")) {
                e.consume();
                // handle block comment cases
                setText(t1 + "\n" + tab.repeat(braceTabLevel) + " * \n" + tab.repeat(braceTabLevel) + " */" + t2);
                setCaretPosition(curCaretPosition + (caretPad * braceTabLevel) + 4);
            } else {
                // mantém o nível do tab quando teclado enter
                // prioridade: nível do tab atual -> chaves abertas
                e.consume();
                var curTabLevel = 0;
                var rowStart = 0;
                try {
                    rowStart = Utilities.getRowStart(this, curCaretPosition);
                } catch (BadLocationException err) {
                    err.printStackTrace();
                }
                var chars = t1.substring(rowStart, curCaretPosition).chars();

                if (isTab)
                    curTabLevel = (int) chars.filter(ch -> ch == '\t').count();
                else
                    curTabLevel = (int) chars.filter(ch -> ch == ' ').count() / Settings.TAB_SIZE;

                var bigger = Math.max(curTabLevel, braceTabLevel);

                setText(t1 + "\n" + tab.repeat(bigger) + t2);
                setCaretPosition(curCaretPosition + (caretPad * bigger) + 1);
            }
        } else if (keyCode == KeyEvent.VK_DOWN) {
            if (pmSuggestions != null && pmSuggestions.isVisible() && suggestions != null && suggestions.size() > 0) {
                e.consume();
                pmSuggestions.requestFocus();
                SwingUtilities.invokeLater(() -> MenuSelectionManager.defaultManager().setSelectedPath(new MenuElement[]{pmSuggestions, suggestions.get(0)}));
            }
        }
    }

    private void coderTyped(KeyEvent e) {
        // handle printable characters
        if (!Settings.CODING_HELP) return;

        var keyChar = e.getKeyChar();
        var isTab = Settings.TAB_TYPE == Settings.TT_TAB;
        var curCaretPosition = getCaretPosition();
        var t1 = getText().substring(0, getCaretPosition());
        var t2 = getText().substring(getCaretPosition());

        if (keyChar == ']' || keyChar == ')') {
            if (t1.endsWith("[")) {
                e.consume();
                if (!t2.startsWith("]")) {
                    setText(t1 + "]" + t2);
                }
                setCaretPosition(curCaretPosition + 1);
            } else if (t1.endsWith("(")) {
                e.consume();
                if (!t2.startsWith(")")) {
                    setText(t1 + ")" + t2);
                }
                setCaretPosition(curCaretPosition + 1);
            }
        } else if (keyChar == '"' || keyChar == '\'' || keyChar == '[' || keyChar == '(') {
            e.consume();
            if (keyChar == '[') {
                setText(t1 + keyChar + ']' + t2);
            } else if (keyChar == '(') {
                setText(t1 + keyChar + ')' + t2);
            } else if (t1.endsWith(Character.toString(keyChar))) {
                if (!t2.startsWith(Character.toString(keyChar))) {
                    setText(t1 + keyChar + t2);
                }
            } else {
                setText(t1 + keyChar + keyChar + t2);
            }
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
                setText(t1 + "{  } ." + t2);
                setCaretPosition(curCaretPosition + pad);
            }
        }
    }

    private int getTabLevel() {
        var text = getText().substring(0, getCaretPosition());
        return getTabLevel(text);
    }

    private int getTabLevel(String text) {
        int tabLevel = 0;
        for (int i = 0; i < text.length(); ++i) {
            var c = text.charAt(i);
            if (c == '{') tabLevel++;
            else if (c == '}') tabLevel--;
            //if (tabLevel < 0) tabLevel = 0;
        }
        return tabLevel;
    }

    public long getCaretLine() {
        // calcula o número de linhas
        // conta-se os caracteres '\n' até a posição atual do caret
        return getText().substring(0, getCaretPosition()).chars().filter(ch -> ch == '\n').count() + 1;
    }

    public long getCaretCol() {
        // calcula a coluna
        int offset = 0;
        try {
            // pega o começo da linha atual
            offset = Utilities.getRowStart(this, getCaretPosition());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        // subtrai da posição atual do caret sobrando apena a coluna atual
        long tabAdjust = 0;
        if (Settings.TAB_TYPE == Settings.TT_TAB) {
            tabAdjust = getText().substring(offset, getCaretPosition()).chars().filter(ch -> ch == '\t').count();
            tabAdjust *= Settings.TAB_SIZE - 1;
        }
        return getCaretPosition() - offset + 1 + tabAdjust;
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
