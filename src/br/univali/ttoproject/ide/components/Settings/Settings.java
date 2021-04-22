package br.univali.ttoproject.ide.components.Settings;

import br.univali.ttoproject.ide.App;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Settings {

    public static final String CRLF = "\r\n";
    public static final String LF = "\n";


    public static String SO_NAME;
    public static String SO_SHORT_NAME;
    public static final int SO_WINDOWS = 0;
    public static final int SO_LINUX = 1;
    public static final int CURRENT_SO;

    public static final int LNE_CRLF = 0;
    public static final int LNE_LF = 1;
    public static final int ENC_UTF_8 = 0;
    public static final int TT_SPACES = 0;
    public static final int TT_TAB = 1;
    public static final int LF_SYSTEM = 0;
    public static final int LF_CROSS_PLATFORM = 1;

    public static App app;

    public static int TAB_TYPE;
    public static int TAB_SIZE;
    public static int LINE_ENDING;
    public static int ENCODING;
    public static Font FONT;
    public static boolean SHOW_TOOL_BAR;
    public static boolean SHOW_STATUS_BAR;
    public static boolean SHOW_CONSOLE;
    public static int LOOK_AND_FEEL;
    public static int FONT_THEME;
    public static boolean SYNTAX_HIGHLIGHT;

    static {
        SO_NAME = System.getProperty("os.name");
        SO_SHORT_NAME = System.getProperty("os.name").substring(0, 3).toLowerCase();

        if (SO_SHORT_NAME.equals("win")) CURRENT_SO = SO_WINDOWS;
        else CURRENT_SO = SO_LINUX;

        if (new File(getDefaultConfigFilePath()).exists()) {
            load();
        } else {
            setDefaultSettings();
            save();
        }
    }

    public static void load() {
        var properties = new Properties();
        try {
            var is = new FileInputStream(getDefaultConfigFilePath());
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        var fontStr = properties.getProperty("FONT").split(",");
        FONT = new Font(fontStr[0], Font.PLAIN, Integer.parseInt(fontStr[1]));
        LINE_ENDING = Integer.parseInt(properties.getProperty("LINE_ENDING"));
        TAB_TYPE = Integer.parseInt(properties.getProperty("TAB_TYPE"));
        TAB_SIZE = Integer.parseInt(properties.getProperty("TAB_SIZE"));
        ENCODING = Integer.parseInt(properties.getProperty("ENCODING"));
        SHOW_TOOL_BAR = Boolean.parseBoolean(properties.getProperty("SHOW_TOOL_BAR"));
        SHOW_STATUS_BAR = Boolean.parseBoolean(properties.getProperty("SHOW_STATUS_BAR"));
        SHOW_CONSOLE = Boolean.parseBoolean(properties.getProperty("SHOW_CONSOLE"));
        LOOK_AND_FEEL = Integer.parseInt(properties.getProperty("LOOK_AND_FEEL"));
        FONT_THEME = Integer.parseInt(properties.getProperty("FONT_THEME"));
        SYNTAX_HIGHLIGHT = Boolean.parseBoolean(properties.getProperty("SYNTAX_HIGHLIGHT"));
        setFontTheme();
        setLookAndFeel();
    }

    public static void save() {
        var properties = new Properties();
        properties.setProperty("FONT", FONT.getFontName() + "," + FONT.getSize());
        properties.setProperty("LINE_ENDING", Integer.toString(LINE_ENDING));
        properties.setProperty("TAB_TYPE", Integer.toString(TAB_TYPE));
        properties.setProperty("TAB_SIZE", Integer.toString(TAB_SIZE));
        properties.setProperty("ENCODING", Integer.toString(ENCODING));
        properties.setProperty("SHOW_TOOL_BAR", Boolean.toString(SHOW_TOOL_BAR));
        properties.setProperty("SHOW_STATUS_BAR", Boolean.toString(SHOW_STATUS_BAR));
        properties.setProperty("SHOW_CONSOLE", Boolean.toString(SHOW_CONSOLE));
        properties.setProperty("LOOK_AND_FEEL", Integer.toString(LOOK_AND_FEEL));
        properties.setProperty("FONT_THEME", Integer.toString(FONT_THEME));
        properties.setProperty("SYNTAX_HIGHLIGHT", Boolean.toString(SYNTAX_HIGHLIGHT));
        var directory = new File(getDefaultConfigPath());
        if (!directory.exists()) directory.mkdir();
        try {
            properties.store(new FileWriter(getDefaultConfigFilePath()), "2021.1 Compiler configs");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setDefaultSettings() {
        if (CURRENT_SO == SO_WINDOWS) {
            FONT = new Font("Consolas", Font.PLAIN, 16);
            LINE_ENDING = LNE_CRLF;
        } else {
            FONT = new Font("Monospace", Font.PLAIN, 15);
            LINE_ENDING = LNE_LF;
        }
        TAB_TYPE = TT_TAB;
        TAB_SIZE = 4;
        ENCODING = ENC_UTF_8;
        SHOW_TOOL_BAR = true;
        SHOW_STATUS_BAR = true;
        SHOW_CONSOLE = true;
        LOOK_AND_FEEL = 0;
        FONT_THEME = 0;
        SYNTAX_HIGHLIGHT = true;
        setFontTheme();
        setLookAndFeel();
    }

    public static void update() {
        app.updateSettings();
    }

    public static void setLookAndFeel() {
        try {
            var op = Settings.LOOK_AND_FEEL;
            var name = UIManager.getInstalledLookAndFeels()[op].getClassName();
            UIManager.setLookAndFeel(name);
        } catch (ClassNotFoundException |
                InstantiationException |
                IllegalAccessException |
                UnsupportedLookAndFeelException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void setFontTheme() {
        var op = Settings.FONT_THEME;
        FontTheme.setFontTheme(op);
    }

    public static String getDefaultConfigPath() {
        return FileSystemView.getFileSystemView().getDefaultDirectory() + File.separator + "2021-1";
    }

    public static String getDefaultConfigFilePath() {
        return getDefaultConfigPath() + File.separator + "config.properties";
    }

    public static String stringLineEnding() {
        return LINE_ENDING == LNE_CRLF ? "CRLF" : "LF";
    }

    public static String stringEncoding() {
        return "UTF-8";
    }

    public static String stringTabType() {
        return TAB_TYPE == TT_TAB ? "TAB" : "SPACE";
    }

}
