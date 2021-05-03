package br.univali.ttoproject.compiler;

import br.univali.ttoproject.ide.components.Console;

public abstract class Program implements Runnable {

    public Console console;

    protected volatile boolean finished = false;

    public Program(Console console) {
        this.console = console;
    }

    @Override
    public abstract void run();

    public boolean isFinished() {
        return finished;
    }
}
