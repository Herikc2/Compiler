package br.univali.ttoproject.compiler;

import br.univali.ttoproject.vm.Instruction;
import br.univali.ttoproject.vm.VMConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SemanticAnalysis {

    private String context;
    private int VT;
    private int VP;
    private int VIT;
    private int kind;
    private int pointer;
    private boolean indexedVariable;
    private Stack<String> deviationStack;
    private List<String[]> symbolTable;

    private String recognizedIdentifier;
    private String identifierAction11;
    private String identifierAction12;
    private int constantAction14;

    private ArrayList<Instruction<Integer, Object>> program;

    public SemanticAnalysis() {
        this.context = "";
        this.VT = 0;
        this.VP = 0;
        this.VIT = 0;
        this.kind = 0;
        this.pointer = 0;
        this.indexedVariable = false;
        this.deviationStack = new Stack<>();
        this.symbolTable = new ArrayList<>();
        this.recognizedIdentifier = "";
        this.program = new ArrayList<>();
    }

    public void action1() {
        program.add(new Instruction<>(VMConstants.STP, VMConstants.NULL_PARAM));
    }

    public void action2(String identifier) {
        insertSymbolTable(identifier, "0", "-", "-");
    }

    public void action3() {
        this.context = "constant";
        this.VIT = 0;
    }

    public void action4() {
        VPAdd(this.VIT);
        switch (this.kind) {
            case 1, 5 -> {
                this.program.add(new Instruction<>(VMConstants.ALI, this.VP));
                pointerAdd(1);
            }
            case 2, 6 -> {
                this.program.add(new Instruction<>(VMConstants.ALR, this.VP));
                pointerAdd(1);
            }
            case 3, 7 -> {
                this.program.add(new Instruction<>(VMConstants.ALS, this.VP));
                pointerAdd(1);
            }
            case 4 -> {
                this.program.add(new Instruction<>(VMConstants.ALB, this.VP));
                pointerAdd(1);
            }
        }
        if (this.kind >= 1 && this.kind <= 4) {
            this.VP = 0;
            this.VIT = 0;
        }
    }

    public void action5(String value) {
        switch (this.kind) {
            case 5 -> {
                this.program.add(new Instruction<>(VMConstants.LDI, value));
                pointerAdd(1);
            }
            case 6 -> {
                this.program.add(new Instruction<>(VMConstants.LDR, value));
                pointerAdd(1);
            }
            case 7 -> {
                this.program.add(new Instruction<>(VMConstants.LDS, value));
                pointerAdd(1);
            }
        }

        this.program.add(new Instruction<>(VMConstants.STC, this.VP));
        pointerAdd(1);
        this.VP = 0;
    }

    public void action6() {
        this.context = "variable";
    }

    public void action7() {
        if (context.equals("variable")) {
            this.kind = 1;
        } else {
            this.kind = 5;
        }
    }

    public void action8() {
        if (context.equals("variable")) {
            this.kind = 2;
        } else {
            this.kind = 6;
        }
    }

    public void action9() {
        if (context.equals("variable")) {
            this.kind = 3;
        } else {
            this.kind = 7;
        }
    }

    public String action10() {
        if (context.equals("variable")) {
            this.kind = 4;
            return "";
        } else {
            return "Invalid type for constant.\n";
        }
    }

    public String action11(String identifier) {
        if (existsSymbolTable(identifier)) {
            return "Identifier already declared.\n";
        } else {
            VTAdd(1);
            VPAdd(1);
            insertSymbolTable(identifier, "-");
            this.identifierAction11 = identifier;
            return "";
        }
    }

    public String action12(String identifier) {
        if (this.context.equals("variable")) {
            if (existsSymbolTable(identifier)) {
                return "Identifier already declared.\n";
            } else {
                this.indexedVariable = false;
                this.recognizedIdentifier = identifier;
                this.identifierAction12 = identifier;
            }
        } else {
            this.indexedVariable = false;
            this.recognizedIdentifier = identifier;
        }
        return "";
    }

    public String action13() {
        switch (this.context){
            case "variable":
                if(!indexedVariable){
                    VTAdd(1);
                    VPAdd(1);
                    insertSymbolTable(this.identifierAction11,"-");
                }else{
                    VITAdd(constantAction14);
                    int incrementedVT = this.VT + 1;
                    insertSymbolTable(identifierAction12, Integer.toString(this.kind), Integer.toString(incrementedVT), Integer.toString(constantAction14));
                    VTAdd(constantAction14);
                }
                break;
            case "assignment":
                break;
        }

        return "";
    }

    public boolean existsSymbolTable(String identifier) {
        for (String[] row : symbolTable)
            for (String item : row)
                if (item.equals(identifier))
                    return true;

        return false;
    }

    public void insertSymbolTable(String identifier, String parameter) {
        symbolTable.add(new String[]{identifier, Integer.toString(this.kind), Integer.toString(this.VT), parameter});
    }

    public void insertSymbolTable(String identifier, String parameter1, String parameter2, String parameter3) {
        symbolTable.add(new String[]{identifier, parameter1, parameter2, parameter3});
    }

    public void pointerAdd(int value) {
        this.pointer += value;
    }

    public void VTAdd(int value) {
        this.VT += value;
    }

    public void VPAdd(int value) {
        this.VP += value;
    }

    public void VITAdd(int value) {
        this.VIT += value;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public int getVT() {
        return VT;
    }

    public void setVT(int VT) {
        this.VT = VT;
    }

    public int getVP() {
        return VP;
    }

    public void setVP(int VP) {
        this.VP = VP;
    }

    public int getVIT() {
        return VIT;
    }

    public void setVIT(int VIT) {
        this.VIT = VIT;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public int getPointer() {
        return pointer;
    }

    public void setPointer(int pointer) {
        this.pointer = pointer;
    }

    public boolean isIndexedVariable() {
        return indexedVariable;
    }

    public void setIndexedVariable(boolean indexedVariable) {
        this.indexedVariable = indexedVariable;
    }

    public Stack<String> getDeviationStack() {
        return deviationStack;
    }

    public void setDeviationStack(Stack<String> deviationStack) {
        this.deviationStack = deviationStack;
    }

    public List<String[]> getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(List<String[]> symbolTable) {
        this.symbolTable = symbolTable;
    }

    public String getRecognizedIdentifier() {
        return recognizedIdentifier;
    }

    public void setRecognizedIdentifier(String recognizedIdentifier) {
        this.recognizedIdentifier = recognizedIdentifier;
    }

    public ArrayList<Instruction<Integer, Object>> getProgram() {
        return program;
    }

    public void setProgram(ArrayList<Instruction<Integer, Object>> program) {
        this.program = program;
    }
}
