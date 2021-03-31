package br.univali.ttoproject.ide.components;

import java.io.*;

import javax.swing.filechooser.FileSystemView;

public class FileTTO extends File {

    private static final String DEFAULT_PATH =
            FileSystemView.getFileSystemView().getDefaultDirectory().getPath() +"\\" + "untitled.tto";

    public FileTTO() {
        super(DEFAULT_PATH);
    }

    public FileTTO(String fullPath) {
        super(fullPath);
    }

    public void save(String content) {
        try (var out = new PrintWriter(this)) {
            out.println(content);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String load() {
        var content = new StringBuilder();
        try {
            var br = new BufferedReader(new FileReader(this));
            String line = br.readLine();
            while (line != null) {
                content.append(line);
                content.append(System.lineSeparator());
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
