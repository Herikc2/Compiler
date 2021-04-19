package br.univali.ttoproject.ide.components.Settings;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Settings {

    public static final String CRLF = "\r\n";
    public static final String LF = "\n";

    public static final int SO_WINDOWS = 0;
    public static final int SO_LINUX = 1;

    public static String SO_NAME;
    public static String SO_SHORT_NAME;
    public static final int CURRENT_SO;

    public static final int LNE_CRLF = 0;
    public static final int LNE_LF = 1;
    public static final int ENC_UTF_8 = 0;
    public static final int TT_SPACES = 0;
    public static final int TT_TAB = 1;
    public static final int LF_SYSTEM = 0;
    public static final int LF_CROSS_PLATFORM = 1;

    public static int TAB_TYPE = TT_TAB;
    public static int TAB_SIZE = 4;
    public static int LINE_ENDING;
    public static int ENCODING = ENC_UTF_8;
    public static Font FONT;
    public static boolean SHOW_TOOL_BAR = true;
    public static boolean SHOW_STATUS_BAR = true;
    public static boolean SHOW_CONSOLE = true;
    public static int LOOK_AND_FEEL = 0;
    public static int FONT_THEME = 0;
    public static boolean SYNTAX_HIGHLIGHT = true;

    static {
        SO_NAME = System.getProperty("os.name");
        SO_SHORT_NAME = System.getProperty("os.name").substring(0, 3).toLowerCase();

        // TODO: If exists "$user/.2021.1/config: load configs" else:
        FontTheme.setFontTheme(FONT_THEME);
        try {
            FONT = Font.createFont(Font.TRUETYPE_FONT, new File("./src/res/fonts/consolas.ttf")).deriveFont(16f);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(FONT);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        if (SO_SHORT_NAME.equals("win")) {
            //FONT = new Font("Consolas", Font.PLAIN, 16);
            LINE_ENDING = LNE_CRLF;
            CURRENT_SO = SO_WINDOWS;
            //TAB_TYPE = TT_SPACES;
            //SYNTAX_HIGHLIGHT = false;
        } else {
            //FONT = new Font("Ubuntu Mono", Font.PLAIN, 16);
            LINE_ENDING = LNE_LF;
            CURRENT_SO = SO_LINUX;
            //TAB_TYPE = TT_TAB;
            //SYNTAX_HIGHLIGHT = true;
        }

        // debug
//        FontTheme.setDefaultDark();
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
//            e.printStackTrace();
//        }
    }

    public static void load() {

    }

    public static void save() {

    }

    public static String stringLineEnding() { return LINE_ENDING == LNE_CRLF ? "CRLF" : "LF"; }

    public static String stringEncoding() { return "UTF-8"; }

    public static String stringTabType() { return TAB_TYPE == TT_TAB ? "TAB" : "SPACE"; }

}
