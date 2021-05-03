package br.univali.ttoproject.ide.components.settings;

import java.awt.*;

public class FontTheme {
    public static Color COLOR_DEFAULT;
    public static Color COLOR_HEADER;
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
        COLOR_DEFAULT = new Color(0, 0, 0);
        COLOR_HEADER = new Color(129, 13, 250);
        COLOR_COMMENTS = new Color(0, 128, 0);
        COLOR_RESERVED = new Color(0, 0, 255);
        COLOR_SPECIAL = new Color(64, 64, 64);
        COLOR_NUMBER = new Color(9, 134, 88);
        COLOR_STRING = new Color(163, 21, 21);
    }

    public static void setDefaultDark() {
        COLOR_DEFAULT = new Color(255, 255, 255);
        COLOR_HEADER = new Color(147, 99, 196);
        COLOR_COMMENTS = new Color(106, 153, 85);
        COLOR_RESERVED = new Color(86, 156, 214);
        COLOR_SPECIAL = new Color(192, 192, 192);
        COLOR_NUMBER = new Color(181, 206, 168);
        COLOR_STRING = new Color(206, 145, 120);
    }
}
