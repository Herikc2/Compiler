package br.univali.ttoproject.vm;

import br.univali.ttoproject.ide.components.Console;

import java.util.ArrayList;

public class VirtualMachine implements Runnable {

    private final Console console;

    private final ArrayList<Instruction<Integer, Object>> program;

    private Thread thread;

    private final Object[] stack;
    private int pointer;
    private int top;

    private volatile boolean finished = false;
    private volatile boolean stopped = false;

    public VirtualMachine(Console console, ArrayList<Instruction<Integer, Object>> program) {
        stack = new Object[8];
        pointer = 1;
        top = 0;
        this.console = console;
        this.program = program;
        console.reset();
    }

    @Override
    public void run() {
        thread = new Thread(() -> {
            for (var inst : program) {
                try {
                    eval(inst);
                } catch (Exception e) {
                    if (!e.getMessage().isEmpty())
                        console.addContent(e.getMessage());
                    break;
                }
            }
            // finalize
            finished = true;
        });
        thread.start();
    }

    public void stop() {
        thread.interrupt();
        finished = true;
        stopped = true;
    }

    private void eval(Instruction<Integer, Object> inst) throws Exception {
        switch (inst.getLeft()) {
            case VMConstants.ADD -> add();
            case VMConstants.DIV -> div();
            case VMConstants.MUL -> mul();
            case VMConstants.SUB -> sub();
            case VMConstants.ALB -> alb((int) inst.getRight());
            case VMConstants.ALI -> ali((int) inst.getRight());
            case VMConstants.ALR -> alr((int) inst.getRight());
            case VMConstants.ALS -> als((int) inst.getRight());
            case VMConstants.LDB -> ldb((boolean) inst.getRight());
            case VMConstants.LDI -> ldi((int) inst.getRight());
            case VMConstants.LDR -> ldr((float) inst.getRight());
            case VMConstants.LDS -> lds((String) inst.getRight());
            case VMConstants.LDV -> ldv((int) inst.getRight());
            case VMConstants.STR -> str((int) inst.getRight());
            case VMConstants.AND -> and();
            case VMConstants.NOT -> not();
            case VMConstants.OR -> or();
            case VMConstants.BGE -> bge();
            case VMConstants.BGR -> bgr();
            case VMConstants.DIF -> dif();
            case VMConstants.EQL -> eql();
            case VMConstants.SME -> sme();
            case VMConstants.SMR -> smr();
            case VMConstants.JMF -> jmf((int) inst.getRight());
            case VMConstants.JMP -> jmp((int) inst.getRight());
            case VMConstants.JMT -> jmt((int) inst.getRight());
            case VMConstants.STP -> stp();
            case VMConstants.REA -> rea((int) inst.getRight());
            case VMConstants.WRT -> wrt();
            default -> throw new Exception("Operation error.\n");
        }
    }

    private void add() {
        if (stack[top] instanceof Integer) {
            stack[top - 1] = (int) stack[top - 1] + (int) stack[top];
        } else if (stack[top] instanceof Float) {
            stack[top - 1] = (float) stack[top - 1] + (float) stack[top];
        } else {
            stack[top - 1] = (String) stack[top - 1] + stack[top];
        }
        --top;
        ++pointer;
    }

    private void div() throws Exception {
        if (stack[top] instanceof Integer) {
            if ((int) stack[top] == 0) {
                throw new Exception("Runtime error: division by zero.");
            }
            stack[top - 1] = (int) stack[top - 1] / (int) stack[top];
        } else if (stack[top] instanceof Float) {
            if ((float) stack[top] == 0f) {
                throw new Exception("Runtime error: division by zero.");
            }
            stack[top - 1] = (float) stack[top - 1] / (float) stack[top];
        }
        --top;
        ++pointer;
    }

    private void mul() {
        if (stack[top] instanceof Integer) {
            stack[top - 1] = (int) stack[top - 1] * (int) stack[top];
        } else if (stack[top] instanceof Float) {
            stack[top - 1] = (float) stack[top - 1] * (float) stack[top];
        }
        --top;
        ++pointer;
    }

    private void sub() {
        if (stack[top] instanceof Integer) {
            stack[top - 1] = (int) stack[top - 1] - (int) stack[top];
        } else if (stack[top] instanceof Float) {
            stack[top - 1] = (float) stack[top - 1] - (float) stack[top];
        }
        --top;
        ++pointer;
    }

    private void alb(int param) {
        for (int i = top + 1; i < top + param; ++i) {
            stack[i] = false;
        }
        top = top + param;
        ++pointer;
    }

    private void ali(int param) {
        for (int i = top + 1; i < top + param; ++i) {
            stack[i] = 0;
        }
        top = top + param;
        ++pointer;
    }

    private void alr(int param) {
        for (int i = top + 1; i < top + param; ++i) {
            stack[i] = 0f;
        }
        top = top + param;
        ++pointer;
    }

    private void als(int param) {
        for (int i = top + 1; i < top + param; ++i) {
            stack[i] = "";
        }
        top = top + param;
        ++pointer;
    }

    private void ldb(boolean param) {
        ++top;
        stack[top] = param;
        ++pointer;
    }

    private void ldi(int param) {
        ++top;
        stack[top] = param;
        ++pointer;
    }

    private void ldr(float param) {
        ++top;
        stack[top] = param;
        ++pointer;
    }

    private void lds(String param) {
        ++top;
        stack[top] = param;
        ++pointer;
    }

    private void ldv(int param) {
        ++top;
        stack[top] = stack[param];
        ++pointer;
    }

    private void str(int param) {
        stack[param] = stack[top];
        --top;
        ++pointer;
    }

    private void and() {
        stack[top - 1] = (boolean) stack[top - 1] && (boolean) stack[top];
        --top;
        ++pointer;
    }

    private void not() {
        stack[top] = !(boolean) stack[top];
        ++pointer;
    }

    private void or() {
        stack[top - 1] = (boolean) stack[top - 1] || (boolean) stack[top];
        --top;
        ++pointer;
    }

    private void bge() {
        if (stack[top] instanceof Integer) {
            stack[top - 1] = (int) stack[top - 1] >= (int) stack[top];
        } else if (stack[top] instanceof Float) {
            stack[top - 1] = (float) stack[top - 1] >= (float) stack[top];
        }
        --top;
        ++pointer;
    }

    private void bgr() {
        if (stack[top] instanceof Integer) {
            stack[top - 1] = (int) stack[top - 1] > (int) stack[top];
        } else if (stack[top] instanceof Float) {
            stack[top - 1] = (float) stack[top - 1] > (float) stack[top];
        }
        --top;
        ++pointer;
    }

    private void dif() {
        if (stack[top] instanceof Integer) {
            stack[top - 1] = (int) stack[top - 1] != (int) stack[top];
        } else if (stack[top] instanceof Float) {
            stack[top - 1] = (float) stack[top - 1] != (float) stack[top];
        }
        --top;
        ++pointer;
    }

    private void eql() {
        if (stack[top] instanceof Integer) {
            stack[top - 1] = (int) stack[top - 1] == (int) stack[top];
        } else if (stack[top] instanceof Float) {
            stack[top - 1] = (float) stack[top - 1] == (float) stack[top];
        }
        --top;
        ++pointer;
    }

    private void sme() {
        if (stack[top] instanceof Integer) {
            stack[top - 1] = (int) stack[top - 1] <= (int) stack[top];
        } else if (stack[top] instanceof Float) {
            stack[top - 1] = (float) stack[top - 1] <= (float) stack[top];
        }
        --top;
        ++pointer;
    }

    private void smr() {
        if (stack[top] instanceof Integer) {
            stack[top - 1] = (int) stack[top - 1] < (int) stack[top];
        } else if (stack[top] instanceof Float) {
            stack[top - 1] = (float) stack[top - 1] < (float) stack[top];
        }
        --top;
        ++pointer;
    }

    private void jmf(int param) {
        if (!(boolean) stack[top]) {
            pointer = param;
        } else {
            ++pointer;
        }
        --top;
    }

    private void jmp(int param) {
        pointer = param;
    }

    private void jmt(int param) {
        if ((boolean) stack[top]) {
            pointer = param;
        } else {
            ++pointer;
        }
        --top;
    }

    private void stp() throws Exception {
        throw new Exception("");
    }

    private void rea(int param) throws Exception {
        ++top;
        var consoleInput = read();
        ++pointer;
        try {
            switch (param) {
                case VMConstants.NATURAL -> stack[top] = Integer.parseInt(consoleInput);
                case VMConstants.REAL -> stack[top] = Float.parseFloat(consoleInput);
                case VMConstants.CHAR -> stack[top] = consoleInput;
                case VMConstants.BOOLEAN -> stack[top] = Boolean.parseBoolean(consoleInput);
            }
        } catch (Exception e) {
            throw new Exception("Runtime error.");
        }
    }

    private void wrt() {
        write((String) stack[top]);
        --top;
        pointer++;
    }

    public void write(String content) {
        console.addContent(content);
    }

    public String read() {
        console.initDataEntry();
        //noinspection StatementWithEmptyBody
        while (!console.isInputReady()) ;
        var input = console.getInput();
        console.addContent("\n");
        return input;
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isStopped() {
        return stopped;
    }
}