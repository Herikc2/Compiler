package br.univali.ttoproject.ide;

import br.univali.ttoproject.compiler.Compiler;
import br.univali.ttoproject.ide.components.Console;
import br.univali.ttoproject.ide.components.MenuBar;
import br.univali.ttoproject.ide.components.*;
import br.univali.ttoproject.ide.components.editor.CodeEditor;
import br.univali.ttoproject.ide.components.settings.Settings;
import br.univali.ttoproject.ide.components.settings.SettingsForm;
import br.univali.ttoproject.vm.Instruction;
import br.univali.ttoproject.vm.VirtualMachine;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.function.Supplier;

public class App extends JFrame {

    private JPanel panelMain;
    private final CodeEditor codeEditor;
    private final JTabbedPane tabIO;
    private Console console;
    private final LogPane logPane;
    private final AsmTablePane asmTablePane;
    private final ToolBar toolBar;
    private final StatusBar statusBar;
    private final JSplitPane splitPane;
    private final JScrollPane scpCodeEditor;
    private final JLabel lblLnCol;
    private final JLabel lblTabType;
    private final JLabel lblTabSize;
    private final JLabel lblEncoding;
    private final JLabel lblLineEnding;

    private FileTTO file;
    private ArrayList<String> recentFiles;

    private VirtualMachine virtualMachine;
    private ArrayList<Instruction<Integer, Object>> program;

    private boolean newFile = true;
    private boolean savedFile = true;

    private boolean compiled = false;
    private boolean running = false;


    public App() {
        // Inicialização de objetos ------------------------------------------------------------------------------------
        file = new FileTTO();
        loadRecentFiles();
        var recentMenu = createRecentMenu();
        Settings.addListener(this::updateSettings);

        // Interface ---------------------------------------------------------------------------------------------------
        setTitle("Compiler");
        setIconImage(Toolkit.getDefaultToolkit().getImage(App.class.getResource("/img/icon.png")));
        setContentPane(panelMain);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(0, 0, 992, 558);
        setMinimumSize(new Dimension(800, 450));
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mExit();
            }
        });
        setVisible(true);

        // Adding components -------------------------------------------------------------------------------------------

        // menu methods
        Supplier<?>[] menuMethods = {this::mNew, this::mOpen, this::mSave, this::mSaveAs, this::mSettings, this::mExit,
                this::mUndo, this::mRedo, this::mCut, this::mCopy, this::mPaste, this::mSelectAll, this::mToolBar,
                this::mStatusBar, this::mConsole, this::mCompileRun, this::mCompile, this::mRun, this::mStop,
                this::mHelp, this::mAbout};

        // menu bar
        setJMenuBar(new MenuBar(menuMethods, recentMenu));

        // tool bar
        toolBar = new ToolBar(menuMethods);
        add(toolBar, BorderLayout.NORTH);

        // status bar
        statusBar = new StatusBar();
        lblLnCol = new JLabel("Ln 1, Col 1");
        lblTabType = new JLabel(Settings.stringTabType());
        lblTabSize = new JLabel(Settings.TAB_SIZE + " spaces");
        lblEncoding = new JLabel(Settings.stringEncoding());
        lblLineEnding = new JLabel(Settings.stringLineEnding());
        statusBar.add(lblLnCol);
        statusBar.add(lblTabType);
        statusBar.add(lblTabSize);
        statusBar.add(lblEncoding);
        statusBar.add(lblLineEnding);
        panelMain.add(statusBar, BorderLayout.SOUTH);

        // split pane (up(left): editor / down(right): console)
        splitPane = new JSplitPane();
        splitPane.setResizeWeight(0.8);
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        panelMain.add(splitPane, BorderLayout.CENTER);

        // console
        console = new Console();
        var scpConsole = new JScrollPane(console);

        // log
        logPane = new LogPane();
        var scpLog = new JScrollPane(logPane);

        // asm table
        asmTablePane = new AsmTablePane();
        var scpAsmTable = new JScrollPane(asmTablePane);

        // creating and setting up tab
        tabIO = new JTabbedPane();
        tabIO.add("Console", scpConsole);
        tabIO.add("Log messages", scpLog);
        tabIO.add("Object Code", scpAsmTable);

        // adding tab (console and log)
        splitPane.setRightComponent(tabIO);

        // editor
        codeEditor = new CodeEditor();
        codeEditor.addCaretListener(e -> updateLCLabel());
        codeEditor.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!e.isControlDown()) updateFileEdit();
            }
        });
        scpCodeEditor = new JScrollPane(codeEditor);
        splitPane.setLeftComponent(scpCodeEditor);
        // editor line number
        var tln = new JTextLineNumber(codeEditor);
        scpCodeEditor.setRowHeaderView(tln);
        scpCodeEditor.setViewportView(codeEditor);

        updateSettings();

        codeEditor.requestFocus();
    }

    public static void main(String[] args) {
        // TODO [BUG]: sometimes files loading without \t on windows

        // Inicializa a aplicação
        EventQueue.invokeLater(() -> {
            try {
                new App();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Actions
    // -----------------------------------------------------------------------------------------------------------------

    public boolean mNew() {
        // verifica se existe arquivo a ser salvo, se tiver isso é tratado
        if (cancelSaveFileOp()) return false;

        clearOutputs();

        // reinicializa as variáveis de arquivo
        file = new FileTTO();
        codeEditor.setText("");
        resetControlVars();
        newFile = true;
        setTitle("Compiler");

        return true;
    }

    public boolean mOpen() {
        if (cancelSaveFileOp()) return false;

        clearOutputs();

        // pega o caminho do arquivo a ser aberto
        var fullPath = getFilePath(false);
        if (fullPath.equals("")) return false;
        if (!new FileTTO(fullPath).exists()) {
            JOptionPane.showMessageDialog(
                    this,
                    "The path " + fullPath + " is not valid.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // abre o arquivo
        file = new FileTTO(fullPath);
        resetControlVars();
        setTitle("Compiler - " + file.getName());

        codeEditor.setText(file.load());

        return true;
    }

    public boolean mSave() {
        if (newFile && !savedFile) {
            // se for arquivo novo salva como
            return mSaveAs();
        } else if (!savedFile) {
            // senão se o arquivo tiver alterações, ele é salvo
            resetFileVars();
            setTitle(getTitle().substring(0, getTitle().length() - 1));
            file.save(codeEditor.getText());
        }
        return true;
    }

    public boolean mSaveAs() {
        // se o arquivo possui alterações ele é salvo
        var fullPath = getFilePath(true);
        if (fullPath.equals("")) return false;

        file = new FileTTO(fullPath);
        resetFileVars();
        setTitle("Compiler - " + file.getName());
        file.save(codeEditor.getText());
        return true;
    }

    public boolean mSettings() {
        new SettingsForm(this);

        return true;
    }

    public boolean mExit() {
        if (savedFile) {
            System.exit(0);
        } else if (verifySaveFile()) {
            System.exit(0);
        }

        return true;
    }

    public boolean mUndo() {
        codeEditor.undo();
        updateFileEdit();

        return true;
    }

    public boolean mRedo() {
        codeEditor.redo();
        updateFileEdit();

        return true;
    }

    public boolean mCut() {
        codeEditor.cut();

        return true;
    }

    public boolean mCopy() {
        codeEditor.copy();

        return true;
    }

    public boolean mPaste() {
        codeEditor.paste();

        return true;
    }

    public boolean mSelectAll() {
        codeEditor.selectAll();

        return true;
    }

    public boolean mToolBar() {
        toolBar.setVisible(!toolBar.isVisible());

        return true;
    }

    public boolean mStatusBar() {
        statusBar.setVisible(!statusBar.isVisible());

        return true;
    }

    public boolean mConsole() {
        tabIO.setVisible(!tabIO.isVisible());

        if (!tabIO.isVisible()) {
            panelMain.remove(splitPane);
            splitPane.setVisible(false);
            panelMain.add(scpCodeEditor, BorderLayout.CENTER);
        } else {
            panelMain.remove(scpCodeEditor);
            panelMain.add(splitPane, BorderLayout.CENTER);
            splitPane.setRightComponent(tabIO);
            splitPane.setLeftComponent(scpCodeEditor);
            splitPane.setResizeWeight(0.8);
            splitPane.setDividerLocation(0.8);
            splitPane.setVisible(true);
        }
        panelMain.updateUI();

        return true;
    }

    public boolean mCompileRun() {
        return mCompile() && mRun();
    }

    public boolean mCompile() {
        // verifica se o arquivo é vazio
        if (codeEditor.getText().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Your file is empty.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (running) return false;

        // salva o arquivo antes de compilar
        //if (!mSave()) return false;

        tabIO.setSelectedIndex(1);
        codeEditor.requestFocus();
        //var strLog = new Compiler().build(new StringReader(codeEditor.getText()));
        var comp = new Compiler();
        if (comp.compile(codeEditor.getText())) {
            program = comp.getProgram();
            asmTablePane.setProgram(program);
            logPane.setText(comp.getMessages());
            compiled = true;
        } else {
            logPane.setText(comp.getMessages());
            logPane.requestFocus();
            compiled = false;
        }

        return true;
    }

    public boolean mRun() {
        // verifica se existe algo compilado
        if (!compiled) {
//            JOptionPane.showMessageDialog(
//                    this,
//                    "Please, compile your file before running.",
//                    "Warning",
//                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (running) return false;
        if (program == null) return false;

        console.reset();
        tabIO.setSelectedIndex(0);
        running = true;
        virtualMachine = new VirtualMachine(console, program);
        new Thread(() -> {
            virtualMachine.run();
            //noinspection StatementWithEmptyBody
            while (!virtualMachine.isFinished()) ;
            if (!virtualMachine.isStopped()) {
                console.stopDataEntry();
                console.addContent("\nProcess finished.");
            }
            running = false;
        }).start();

        return true;
    }

    public boolean mStop() {
        if (running) {
            virtualMachine.stop();
            running = false;
            resetBuildVars();
            program = null;
            console.addContent("\nProcess finished without success.");
            return true;
        }

        return false;
    }

    public boolean mAbout() {
        JOptionPane.showMessageDialog(
                this,
                "Authors: Carlos E. B. Machado and Herikc Brecher.",
                "About",
                JOptionPane.INFORMATION_MESSAGE);

        return true;
    }

    public boolean mHelp() {
        new HelpForm(this);

        return true;
    }

    //------------------------------------------------------------------------------------------------------------------
    // UI controls
    //------------------------------------------------------------------------------------------------------------------

    private void updateLCLabel() {
        long rows = codeEditor.getCaretLine();
        long cols = codeEditor.getCaretCol();
        lblLnCol.setText("Ln " + rows + ", Col " + cols);
    }

    private void updateFileEdit() {
        // função chamada sempre que algo é alterado no editor de texto
        // se o arquivo não tiver alterações, uma flag (*) é adicionada ao título
        if (savedFile) {
            setTitle(getTitle() + "*");
            savedFile = !savedFile;
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // Auxiliary functions
    //------------------------------------------------------------------------------------------------------------------

    public void clearOutputs() {
        console.reset();
        logPane.setText("");
    }

    public boolean pathExists(String testPath) {
        for (var path : recentFiles) {
            if (testPath.equals(path)) return true;
        }
        return false;
    }

    public void updateRecentMenu() {
        ((MenuBar) getJMenuBar()).setOpenRecentMenu(createRecentMenu());
    }

    public JMenu createRecentMenu() {
        var recentMenu = new JMenu("Open Recent");
        if (recentFiles.isEmpty()) {
            var menuItem = new JMenuItem("No recent files...");
            recentMenu.add(menuItem);
        }
        for (var f : recentFiles) {
            var menuItem = new JMenuItem(f);
            menuItem.addActionListener(e -> openRecent(f));
            recentMenu.add(menuItem);
        }
        return recentMenu;
    }

    public void openRecent(String path) {
        if (cancelSaveFileOp()) return;

        recentFiles.remove(path);

        if (!new FileTTO(path).exists()) {
            JOptionPane.showMessageDialog(
                    this,
                    "File not found at " + path + ".",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            updateRecentMenu();
            saveRecentFiles();
            return;
        }

        recentFiles.add(0, path);
        updateRecentMenu();
        saveRecentFiles();

        clearOutputs();

        // abre o arquivo
        file = new FileTTO(path);
        resetControlVars();
        setTitle("Compiler - " + file.getName());

        codeEditor.setText(file.load());
    }

    public void loadRecentFiles() {
        recentFiles = new ArrayList<>();
        var file = new File(Settings.getDefaultRecentFilePath());
        if (file.exists()) {
            try {
                var br = new BufferedReader(new FileReader(file));
                String line = br.readLine();
                while (line != null) {
                    recentFiles.add(line);
                    line = br.readLine();
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveRecentFiles() {
        var file = new File(Settings.getDefaultRecentFilePath());
        try (var out = new PrintWriter(file)) {
            for (var path : recentFiles)
                out.println(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateSettings() {
        // Atualiza as novas configurações definidas no form Settings
        codeEditor.setFont(Settings.FONT);
        codeEditor.setTabSize(Settings.TAB_SIZE);
        lblTabType.setText(Settings.stringTabType());
        lblTabSize.setText(Settings.TAB_SIZE + " spaces");
        lblEncoding.setText(Settings.stringEncoding());
        lblLineEnding.setText(Settings.stringLineEnding());
        SwingUtilities.updateComponentTreeUI(this);
        var menuBar = getJMenuBar().getMenu(2);
        if (Settings.SHOW_STATUS_BAR != statusBar.isVisible()) {
            mStatusBar();
            ((JCheckBoxMenuItem) menuBar.getItem(0)).setState(Settings.SHOW_STATUS_BAR);
        }
        if (Settings.SHOW_TOOL_BAR != toolBar.isVisible()) {
            mToolBar();
            ((JCheckBoxMenuItem) menuBar.getItem(1)).setState(Settings.SHOW_TOOL_BAR);
        }
        if (Settings.SHOW_CONSOLE != tabIO.isVisible()) {
            mConsole();
            ((JCheckBoxMenuItem) menuBar.getItem(2)).setState(Settings.SHOW_CONSOLE);
        }
        if (Settings.SYNTAX_HIGHLIGHT) codeEditor.syntaxHighlight();
    }

    public void resetControlVars() {
        resetFileVars();
        resetBuildVars();
    }

    public void resetBuildVars() {
        compiled = false;
        running = false;
    }

    public void resetFileVars() {
        newFile = false;
        savedFile = true;
    }

    public boolean verifySaveFile() {
        // verifica se o usuário deseja salvar o arquivo
        int result = JOptionPane.showConfirmDialog(
                this,
                "Would you like to save the file?",
                "Save",
                JOptionPane.YES_NO_CANCEL_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            return mSave();
        }
        return result != JOptionPane.CANCEL_OPTION && result != JOptionPane.CLOSED_OPTION;
    }

    public boolean cancelSaveFileOp() {
        // chama a função verifySaveFile
        // mas verifica se a operação de salvamento foi cancelada (nega o retorno)
        if (!savedFile) {
            return !verifySaveFile();
        }
        return false;
    }

    public String getFilePath(boolean save) {
        var fullPath = "";
        var optionString = save ? "save" : "open";
        // configura o file chooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to " + optionString);
        //fileChooser.setFileFilter(new FileNameExtensionFilter("2021.1 files", "tto"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("2021.1 files", "tto"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Text files", "txt"));
        if (save) {
            fileChooser.setSelectedFile(new File(file.getName()));
        } else {
            fileChooser.setSelectedFile(new File(Settings.CURRENT_FOLDER));
        }

        int userSelection;
        boolean verifyIfExistsOperationDone = false;

        // loop para verificação se de fato foi selecionado um caminho
        do {
            if (save) {
                userSelection = fileChooser.showSaveDialog(this);
            } else {
                userSelection = fileChooser.showOpenDialog(this);
            }

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                // se for salvar, será verificado se o arquivo existe, caso exista será perguntado que o arquivo deve
                // ser sobrescrito, se a opção for diferente de APPROVE_OPTION uma string vazia é retornada
                if (save) {
                    if (file.exists()) {
                        int result = JOptionPane.showConfirmDialog(
                                this,
                                "A file with that name already exists. Would you like to overwrite?",
                                "Overwrite",
                                JOptionPane.YES_NO_OPTION);
                        if (result == JOptionPane.YES_OPTION) {
                            verifyIfExistsOperationDone = true;
                        }
                    } else {
                        verifyIfExistsOperationDone = true;
                    }
                } else {
                    verifyIfExistsOperationDone = true;
                }
                fullPath = file.getAbsolutePath();
                Settings.CURRENT_FOLDER = fullPath.substring(0, fullPath.lastIndexOf(File.separator)) + File.separator + "*";
                Settings.save();
            } else {
                return "";
            }
        } while (!verifyIfExistsOperationDone);

        // adiciona a extensão caso não tenha
        if (!fullPath.endsWith(".txt")) {
            if (fullPath.length() >= 4 && !fullPath.endsWith(".tto")) {
                fullPath += ".tto";
            } else if (fullPath.length() < 4) {
                fullPath += ".tto";
            }
        }

        // add o novo path aos arquivos recentes
        if (!pathExists(fullPath)) {
            recentFiles.add(0, fullPath);
        }
        if (recentFiles.size() > 10) {
            recentFiles.remove(recentFiles.size() - 1);
        }
        updateRecentMenu();
        saveRecentFiles();

        return fullPath;
    }
}
