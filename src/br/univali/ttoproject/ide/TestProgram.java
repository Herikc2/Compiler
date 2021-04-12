package br.univali.ttoproject.ide;

import br.univali.ttoproject.ide.components.Console;

public class TestProgram {

    public Console console;

    public TestProgram(Console console) {
        this.console = console;
    }

    public void run() {
        new Thread(() -> {
            funcPut("Type your name: ");
            var name = funcGet();
            funcPut("Hello " + name + ".");
        }).start();
    }

    public void funcPut(String content) {
        console.addContent(content);
    }

    public String funcGet() {
        console.initDataEntry();
        while (!console.isInputReady()) ;
        var input = console.getInput();
        console.addContent("\n");
        return input;
    }
}
