package br.univali.ttoproject.ide.components.settings;

import br.univali.ttoproject.ide.App;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Settings {

    private static String[] propertiesName = {
            "FONT", "LINE_ENDING", "TAB_TYPE", "TAB_SIZE", "ENCODING",
            "SHOW_TOOL_BAR", "SHOW_STATUS_BAR", "SHOW_CONSOLE", "LOOK_AND_FEEL", "FONT_THEME",
            "SYNTAX_HIGHLIGHT", "CODING_HELP", "SUGGESTIONS", "CURRENT_FOLDER"
    };

    private static ArrayList<UpdateListener> listeners;

    public static final String CRLF = "\r\n";
    public static final String CR = "\r";
    public static final String LF = "\n";

    public static String OS_NAME;
    public static String OS_SHORT_NAME;
    public static final int OS_WINDOWS = 0;
    public static final int OS_LINUX = 1;
    public static final int OS_MAC = 2;
    public static final int CURRENT_OS;

    public static final int LNE_CRLF = 0;
    public static final int LNE_LF = 1;
    public static final int ENC_UTF_8 = 0;
    public static final int TT_SPACES = 0;
    public static final int TT_TAB = 1;

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
    public static boolean CODING_HELP;
    public static boolean SUGGESTIONS;
    public static String CURRENT_FOLDER;

    static {
        OS_NAME = System.getProperty("os.name");
        OS_SHORT_NAME = OS_NAME.substring(0, 3).toLowerCase();

        if (OS_SHORT_NAME.equals("win")) CURRENT_OS = OS_WINDOWS;
        else CURRENT_OS = OS_LINUX;

        listeners = new ArrayList<>();

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
        for(var p : propertiesName){
            if(!properties.containsKey(p)){
                setDefaultSettings();
                return;
            }
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
        CODING_HELP = Boolean.parseBoolean(properties.getProperty("CODING_HELP"));
        SUGGESTIONS = Boolean.parseBoolean(properties.getProperty("SUGGESTIONS"));
        CURRENT_FOLDER = properties.getProperty("CURRENT_FOLDER");
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
        properties.setProperty("CODING_HELP", Boolean.toString(CODING_HELP));
        properties.setProperty("SUGGESTIONS", Boolean.toString(SUGGESTIONS));
        properties.setProperty("CURRENT_FOLDER", CURRENT_FOLDER);

        var directory = new File(getDefaultConfigFolderPath());
        if (!directory.exists()) //noinspection ResultOfMethodCallIgnored
            directory.mkdir();
        try {
            properties.store(new FileWriter(getDefaultConfigFilePath()), "2021.1 Compiler configs");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setDefaultSettings() {
        if (CURRENT_OS == OS_WINDOWS) {
            FONT = new Font("Consolas", Font.PLAIN, 16);
            LINE_ENDING = LNE_CRLF;
        } else {
            FONT = new Font("Monospaced", Font.PLAIN, 15);
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
        SYNTAX_HIGHLIGHT = false;
        CODING_HELP = false;
        SUGGESTIONS = false;
        CURRENT_FOLDER = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + "*";
        setFontTheme();
        setLookAndFeel();
        save();
    }

    public static void addListener(UpdateListener listener) {
        listeners.add(listener);
    }

    public static void update() {
        for (var listener : listeners)
            listener.update();
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

    public static String getDefaultConfigFolderPath() {
        return FileSystemView.getFileSystemView().getDefaultDirectory() + File.separator + "2021-1";
    }

    public static String getDefaultConfigFilePath() {
        return getDefaultConfigFolderPath() + File.separator + "config.properties";
    }

    public static String getDefaultRecentFilePath() {
        return getDefaultConfigFolderPath() + File.separator + "recent";
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
