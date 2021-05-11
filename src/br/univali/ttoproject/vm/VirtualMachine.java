package br.univali.ttoproject.vm;

import br.univali.ttoproject.ide.components.Console;

public class VirtualMachine implements Runnable {

    private Console console;

    private Instruction<Integer, Integer>[] program;

    private Object[] stack;
    private int pointer;
    private int top;

    private volatile boolean finished = false;

    public VirtualMachine(Console console, Instruction<Integer, Integer>[] program) {
        stack = new Object[8];
        pointer = 1;
        top = 0;
        this.console = console;
        this.program = program;
    }

    @Override
    public void run() {
        new Thread(() -> {
            for (var inst : program){
                try {
                    eval(inst);
                } catch (Exception e) {
                    console.addContent(e.getMessage());
                    break;
                }
            }
            // finalize
            finished = true;
        }).start();
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

    private void div() {

    }

    private void eval(Instruction<Integer, Integer> inst) throws Exception {
        switch (inst.getLeft()){
            case VMConstants.ADD:
                add();
                break;
            case VMConstants.DIV:
                div();
                break;
            case VMConstants.MUL:
                break;
            case VMConstants.SUB:
                break;
            case VMConstants.ALB:
                break;
            case VMConstants.ALI:
                break;
            case VMConstants.ALR:
                break;
            case VMConstants.ALS:
                break;
            case VMConstants.LDB:
                break;
            case VMConstants.LDI:
                break;
            case VMConstants.LDR:
                break;
            case VMConstants.LDS:
                break;
            case VMConstants.LDV:
                break;
            case VMConstants.STR:
                break;
            case VMConstants.AND:
                break;
            case VMConstants.NOT:
                break;
            case VMConstants.OR:
                break;
            case VMConstants.BGE:
                break;
            case VMConstants.BGR:
                break;
            case VMConstants.DIF:
                break;
            case VMConstants.EQL:
                break;
            case VMConstants.SME:
                break;
            case VMConstants.SMR:
                break;
            case VMConstants.JMF:
                break;
            case VMConstants.JMP:
                break;
            case VMConstants.JMT:
                break;
            case VMConstants.STP:
                break;
            case VMConstants.REA:
                break;
            case VMConstants.WRT:
                break;
            default:
                throw new Exception("Operation error.\n");
        }
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

    public boolean isFinished() {
        return finished;
    }
}
