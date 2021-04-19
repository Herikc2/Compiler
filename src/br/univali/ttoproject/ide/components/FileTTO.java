package br.univali.ttoproject.ide.components;

import javax.swing.filechooser.FileSystemView;
import java.io.*;

public class FileTTO extends File {

    private static final String DEFAULT_PATH =
            FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + "untitled.tto";

    public FileTTO() {
        super(DEFAULT_PATH);
    }

    public FileTTO(String fullPath) {
        super(fullPath);
    }

    public void save(String content) {
        // salva o conteudo no arquivo
        try (var out = new PrintWriter(this)) {
            out.println(content);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String load() {
        // carrega e retorna o conteudo do arquivo
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
