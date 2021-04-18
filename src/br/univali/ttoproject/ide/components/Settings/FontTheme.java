package br.univali.ttoproject.ide.components.Settings;

import java.awt.*;

public class FontTheme {
    public static Color COLOR_RESERVED;
    public static Color COLOR_COMMENTS;
    public static Color COLOR_SPECIAL;
    public static Color COLOR_NUMBER;
    public static Color COLOR_STRING;
    public static Color COLOR_DEFAULT;

    public static String[] getFontThemes(){
        return new String[]{"Light", "Dark"};
    }

    public static void setFontTheme(int t){
        switch (t) {
            case 0 -> setDefaultLight();
            case 1 -> setDefaultDark();
        }
    }

    public static void setDefaultLight(){
        COLOR_RESERVED = Color.BLUE;
        COLOR_SPECIAL = Color.DARK_GRAY;
        COLOR_NUMBER = Color.GREEN;
        COLOR_STRING = Color.ORANGE;
        COLOR_DEFAULT = Color.BLACK;
        COLOR_COMMENTS = Color.MAGENTA;
    }

    public static void setDefaultDark(){
        COLOR_RESERVED = Color.BLUE;
        COLOR_SPECIAL = Color.DARK_GRAY;
        COLOR_NUMBER = Color.GREEN;
        COLOR_STRING = Color.ORANGE;
        COLOR_DEFAULT = Color.WHITE;
        COLOR_COMMENTS = Color.MAGENTA;
    }
}
