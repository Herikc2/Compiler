package br.univali.ttoproject.compiler;

import br.univali.ttoproject.vm.Instruction;
import br.univali.ttoproject.vm.VMConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SemanticAnalysis {

    private final int VARIABLE = 0;
    private final int ASSIGNMENT = 1;
    private final int CONSTANT = 2;
    private final int DATA_INPUT = 3;

    private int context;
    private int VT;
    private int VP;
    private int VIT;
    private int kind;
    private int pointer;
    private boolean indexedVariable;
    private Stack<String> deviationStack;
    private List<String[]> symbolTable;

    private String recognizedIdentifier;
    private int recognizedConstant;
    private String identifierAction11;
    private String identifierAction12;
    private int constantAction14;

    private ArrayList<Instruction<Integer, Object>> program;

    public SemanticAnalysis() {
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

    public void action2(Object identifier) {
        insertSymbolTable(identifier.toString(), "0", "-", "-");
    }

    public void action3() {
        this.context = CONSTANT;
        this.VIT = 0;
    }

    public void action4() {
        this.VP += this.VIT;
        switch (this.kind) {
            case 1, 5 -> {
                this.program.add(new Instruction<>(VMConstants.ALI, this.VP));
                this.pointer++;
            }
            case 2, 6 -> {
                this.program.add(new Instruction<>(VMConstants.ALR, this.VP));
                this.pointer++;
            }
            case 3, 7 -> {
                this.program.add(new Instruction<>(VMConstants.ALS, this.VP));
                this.pointer++;
            }
            case 4 -> {
                this.program.add(new Instruction<>(VMConstants.ALB, this.VP));
                this.pointer++;
            }
        }
        if (this.kind >= 1 && this.kind <= 4) {
            this.VP = 0;
            this.VIT = 0;
        }
    }

    public void action5(Object value) {
        switch (this.kind) {
            case 5 -> {
                this.program.add(new Instruction<>(VMConstants.LDI, value));
                this.pointer++;
            }
            case 6 -> {
                this.program.add(new Instruction<>(VMConstants.LDR, value));
                this.pointer++;
            }
            case 7 -> {
                this.program.add(new Instruction<>(VMConstants.LDS, value));
                this.pointer++;
            }
        }

        this.program.add(new Instruction<>(VMConstants.STC, this.VP));
        this.pointer++;
        this.VP = 0;
    }

    public void action6() {
        this.context = VARIABLE;
    }

    public void action7() {
        if (context == VARIABLE) {
            this.kind = 1;
        } else {
            this.kind = 5;
        }
    }

    public void action8() {
        if (context == VARIABLE) {
            this.kind = 2;
        } else {
            this.kind = 6;
        }
    }

    public void action9() {
        if (context == VARIABLE) {
            this.kind = 3;
        } else {
            this.kind = 7;
        }
    }

    public String action10() {
        if (context == VARIABLE) {
            this.kind = 4;
            return "";
        } else {
            return "Invalid type for constant.\n";
        }
    }

    public String action11(Object identifier) {
        if (existsSymbolTable(identifier.toString())) {
            return "Identifier already declared.\n";
        } else {
            this.VT++;
            this.VP++;
            insertSymbolTable(identifier.toString(), "-");
            this.identifierAction11 = identifier.toString();
            return "";
        }
    }

    public String action12(Object identifier) {
        if (this.context == VARIABLE) {
            if (existsSymbolTable(identifier.toString())) {
                return "Identifier already declared.\n";
            } else {
                this.indexedVariable = false;
                this.recognizedIdentifier = identifier.toString();
                this.identifierAction12 = identifier.toString();
            }
        } else {
            this.indexedVariable = false;
            this.recognizedIdentifier = identifier.toString();
        }
        return "";
    }

    public String action13() {
        switch (this.context){
            case VARIABLE:
                if(!indexedVariable){
                    this.VT++;
                    this.VP++;
                    insertSymbolTable(this.identifierAction11,"-");
                }else{
                    this.VIT += constantAction14;
                    int incrementedVT = this.VT + 1;
                    insertSymbolTable(identifierAction12, Integer.toString(this.kind), Integer.toString(incrementedVT), Integer.toString(constantAction14));
                    this.VT += constantAction14;
                }
                break;
            case ASSIGNMENT:
                break;
            case DATA_INPUT:
                break;
        }

        return "";
    }

    public void action14(Object identifier){
        recognizedConstant = Integer.parseInt((String) identifier);
        this.indexedVariable = true;
    }

    public void action15(){
        this.context = ASSIGNMENT;
    }

    public void action16(){
        // TO DO
    }

    public void action17(){
        this.context = DATA_INPUT;
    }

    public void action18(){
        program.add(new Instruction<>(VMConstants.WRT, VMConstants.NULL_PARAM));
        this.pointer++;
    }

    public void action19(){

    }

    public void action20(){

    }

    public void action21(Object integerConstant){
        program.add(new Instruction<>(VMConstants.LDI, integerConstant));
        this.pointer++;
    }

    public void action22(Object floatConstant){
        program.add(new Instruction<>(VMConstants.LDR, floatConstant));
        this.pointer++;
    }

    public void action23(Object literalConstant){
        program.add(new Instruction<>(VMConstants.LDS, literalConstant));
        this.pointer++;
    }

    public void action24(){
        deviationStack.pop();
        replaceLastInstruction("?", String.valueOf(this.pointer));
    }

    public void action25(){
        program.add(new Instruction<>(VMConstants.JMF, "?"));
        this.pointer++;
        deviationStack.push(String.valueOf(this.pointer - 1));
    }

    public void action26(){
        program.add(new Instruction<>(VMConstants.JMT, "?"));
        this.pointer++;
        deviationStack.push(String.valueOf(this.pointer - 1));
    }

    public void action27(){
        deviationStack.pop();
        replaceLastInstruction("?", String.valueOf(this.pointer + 1));
        program.add(new Instruction<>(VMConstants.JMP, "?"));
        this.pointer++;
        deviationStack.push(String.valueOf(this.pointer - 1));
    }

    public void action28(){
        deviationStack.push(String.valueOf(this.pointer));
    }

    public void action29(){
        String address = deviationStack.pop();
        program.add(new Instruction<>(VMConstants.JMT, address));
        this.pointer++;
    }

    public void action30(){
        deviationStack.push(String.valueOf(this.pointer));
    }

    public void action31(){
        program.add(new Instruction<>(VMConstants.JMF, "?"));
        this.pointer++;
        deviationStack.push(String.valueOf(this.pointer - 1));
    }

    public void action32(){
        deviationStack.pop();
        replaceLastInstruction("?", String.valueOf(this.pointer + 1));
        String address = deviationStack.pop();
        program.add(new Instruction<>(VMConstants.JMP, address));
        this.pointer++;
    }

    public void action33(){
        program.add(new Instruction<>(VMConstants.EQL, VMConstants.NULL_PARAM));
        this.pointer++;
    }

    public void action34(){
        program.add(new Instruction<>(VMConstants.DIF, VMConstants.NULL_PARAM));
        this.pointer++;
    }

    public void action35(){
        program.add(new Instruction<>(VMConstants.SMR, VMConstants.NULL_PARAM));
        this.pointer++;
    }

    public void action36(){
        program.add(new Instruction<>(VMConstants.BGR, VMConstants.NULL_PARAM));
        this.pointer++;
    }

    public void action37(){
        program.add(new Instruction<>(VMConstants.SME, VMConstants.NULL_PARAM));
        this.pointer++;
    }

    public void action38(){
        program.add(new Instruction<>(VMConstants.BGE, VMConstants.NULL_PARAM));
        this.pointer++;
    }

    public void action39(){
        program.add(new Instruction<>(VMConstants.ADD, VMConstants.NULL_PARAM));
        this.pointer++;
    }

    public void action40(){
        program.add(new Instruction<>(VMConstants.SUB, VMConstants.NULL_PARAM));
        this.pointer++;
    }

    public void action41(){
        program.add(new Instruction<>(VMConstants.OR, VMConstants.NULL_PARAM));
        this.pointer++;
    }

    public void action42(){
        program.add(new Instruction<>(VMConstants.MUL, VMConstants.NULL_PARAM));
        this.pointer++;
    }

    public void action43(){
        program.add(new Instruction<>(VMConstants.DIV, VMConstants.NULL_PARAM));
        this.pointer++;
    }

    public void action44(){
        // TO DO
    }

    public void action45(){
        // TO DO
    }

    public void action46(){
        program.add(new Instruction<>(VMConstants.AND, VMConstants.NULL_PARAM));
        this.pointer++;
    }

    public void action47(){
        // TO DO
    }

    public void action48(){
        program.add(new Instruction<>(VMConstants.LDB, "TRUE"));
        this.pointer++;
    }

    public void action49(){
        program.add(new Instruction<>(VMConstants.LDB, "FALSE"));
        this.pointer++;
    }

    public void action50(){
        program.add(new Instruction<>(VMConstants.NOT, VMConstants.NULL_PARAM));
        this.pointer++;
    }

    public int getLastIndexInstruction(String value) {
        for(int i = program.size(); i  >= 0; i--){
            Instruction<Integer, Object> temp = program.get(i);

            if(temp.getParameter().toString().equals(value))
                return i;
        }

        return (-1);
    }

    public int getFirstIndexInstruction(String value) {
        for(int i = 0; i  < program.size(); i++){
            Instruction<Integer, Object> temp = program.get(i);

            if(temp.getParameter().toString().equals(value))
                return i;
        }

        return (-1);
    }

    public void replaceLastInstruction(String oldValue, String newValue){
        int index = getLastIndexInstruction(oldValue);

        if(index != (-1))
            program.get(index).setParameter(newValue);
    }

    public void replaceFirstInstruction(String oldValue, String newValue){
        int index = getFirstIndexInstruction(oldValue);

        if(index != (-1))
            program.get(index).setParameter(newValue);
    }

    public int[] getIndexSymbolTable(String value){

        for(int i = symbolTable.size() - 1; i >= 0; i--)
        {
            String[] temp = symbolTable.get(i);
            for(int j = temp.length - 1; j >= 0; j--)
            {
                String item = temp[j];
                if(item.equals(value))
                    return new int[]{i, j};
            }
        }

        return new int[]{-1, -1};
    }

    public void replaceSymbolTable(String oldValue, String newValue){
        int[] index = getIndexSymbolTable(oldValue);

        if(index[0] != (-1) && index[1] != (-1))
            symbolTable.get(index[0])[index[1]] = newValue;
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

    public int getContext() {
        return context;
    }

    public void setContext(int context) {
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
