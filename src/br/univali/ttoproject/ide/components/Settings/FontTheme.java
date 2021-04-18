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
        COLOR_RESERVED = new Color(48, 88, 222);
        COLOR_SPECIAL = Color.DARK_GRAY;
        COLOR_NUMBER = new Color(18, 172, 72);
        COLOR_STRING = new Color(194, 97, 45);
        COLOR_DEFAULT = Color.BLACK;
        COLOR_COMMENTS = new Color(42, 42, 42);
    }

    public static void setDefaultDark(){
        COLOR_RESERVED = new Color(66, 109, 255);
        COLOR_SPECIAL = Color.LIGHT_GRAY;
        COLOR_NUMBER = new Color(52, 255, 125);
        COLOR_STRING = new Color(255, 127, 58);
        COLOR_DEFAULT = Color.WHITE;
        COLOR_COMMENTS = new Color(42, 42, 42);
    }
}
