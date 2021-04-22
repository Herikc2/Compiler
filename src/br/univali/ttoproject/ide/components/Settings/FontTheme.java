package br.univali.ttoproject.ide.components.Settings;

import java.awt.*;

public class FontTheme {
    public static Color COLOR_DEFAULT;
    public static Color COLOR_COMMENTS;
    public static Color COLOR_RESERVED;
    public static Color COLOR_SPECIAL;
    public static Color COLOR_NUMBER;
    public static Color COLOR_STRING;

    public static String[] getFontThemes() {
        return new String[]{"Light", "Dark"};
    }

    public static void setFontTheme(int t) {
        switch (t) {
            case 0 -> setDefaultLight();
            case 1 -> setDefaultDark();
        }
    }

    public static void setDefaultLight() {
        COLOR_DEFAULT = Color.BLACK;
        COLOR_COMMENTS = new Color(0, 128, 0);
        COLOR_RESERVED = new Color(0, 0, 255);
        COLOR_SPECIAL = Color.DARK_GRAY;
        COLOR_NUMBER = new Color(9, 134, 88);
        COLOR_STRING = new Color(163, 21, 21);
    }

    public static void setDefaultDark() {
        COLOR_DEFAULT = Color.WHITE;
        COLOR_COMMENTS = new Color(106, 153, 85);
        COLOR_RESERVED = new Color(86, 156, 214);
        COLOR_SPECIAL = Color.LIGHT_GRAY;
        COLOR_NUMBER = new Color(181, 206, 168);
        COLOR_STRING = new Color(206, 145, 120);
    }
}
