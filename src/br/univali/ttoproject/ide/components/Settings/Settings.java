package br.univali.ttoproject.ide.components.Settings;

import java.awt.*;

public class Settings {

    public static final String CRLF = "\r\n";
    public static final String LF = "\n";

    public static final int LNE_CRLF = 0;
    public static final int LNE_LF = 1;
    public static final int ENC_UTF_8 = 0;

    public static String SO_NAME;
    public static String SO_SHORT_NAME;

    public static int TAB_SIZE = 4;
    public static int LINE_ENDING;
    public static int ENCODING = 0;
    public static Font FONT;
    public static boolean SHOW_TOOL_BAR = true;
    public static boolean SHOW_STATUS_BAR = true;
    public static boolean SHOW_CONSOLE = true;

    static {
        SO_NAME = System.getProperty("os.name");
        SO_SHORT_NAME = System.getProperty("os.name").substring(0, 3).toLowerCase();

        // TODO: If exists "$user/.2021.1/config: load configs"
        // else:
        if (SO_SHORT_NAME.equals("win")) {
            FONT = new Font("Consolas", Font.PLAIN, 15);
            LINE_ENDING = LNE_CRLF;
        } else {
            FONT = new Font("FreeMono", Font.PLAIN, 16);
            LINE_ENDING = LNE_LF;
        }
    }

    public static void save() {

    }

    public static String stringLineEnding() {
        return LINE_ENDING == LNE_CRLF ? "CRLF" : "LF";
    }

    public static String stringEncoding() {
        return "UTF-8";
    }

}
