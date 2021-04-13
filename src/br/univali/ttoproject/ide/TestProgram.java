package br.univali.ttoproject.ide;

import br.univali.ttoproject.compiler.Program;
import br.univali.ttoproject.ide.components.Console;

import javax.swing.*;

public class TestProgram extends Program {

    public TestProgram(Console console) {
        super(console);
    }

    @Override
    public void run() {
        new Thread(() -> {
            funcPut("Type your name: ");
            var name = funcGet();
            funcPut("Hello " + name + ".");

            // last command
            finished = true;
        }).start();
    }

    public void funcPut(String content) {
        console.addContent(content);
    }

    public String funcGet() {
        console.initDataEntry();
        //noinspection StatementWithEmptyBody
        while (!console.isInputReady()) ;
        var input = console.getInput();
        console.addContent("\n");
        return input;
    }
}
