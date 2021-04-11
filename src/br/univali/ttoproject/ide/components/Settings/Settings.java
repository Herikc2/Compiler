package br.univali.ttoproject.ide.components.Settings;

import java.awt.*;

public class Settings {

    public static int TAB_SIZE = 4;
    public static int LINE_ENDING;
    public static int ENCODING = 0;
    public static Font FONT;
    public static boolean SHOW_TOOL_BAR = true;
    public static boolean SHOW_STATUS_BAR = true;
    public static boolean SHOW_CONSOLE = true;

    static {
        if (System.getProperty("os.name").substring(0, 3).equalsIgnoreCase("win")) {
            FONT = new Font("Consolas", Font.PLAIN, 15);
            LINE_ENDING = 0;
        } else {
            FONT = new Font("FreeMono", Font.PLAIN, 16);
            LINE_ENDING = 1;
        }
    }

    public static String stringLineEnding(){
        return LINE_ENDING == 0 ? "CRLF" : "LF";
    }

    public static String stringEncoding(){
        return "UTF-8";
    }

}
