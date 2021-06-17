package br.univali.ttoproject.vm;

public class VMConstants {

    public static final String[] instrNames = {
            "ADD", "DIV", "MUL", "SUB", "ALB", "ALI", "ALR", "ALS", "LDB", "LDI", "LDR", "LDS", "LDV", "STR", "STC",
            "AND", "NOT", "OR", "BGE", "BGR", "DIF", "EQL", "SME", "SMR", "JMF", "JMP", "JMT", "STP", "REA", "WRT"
    };

    // arithmetics
    public static final int ADD =  0;
    public static final int DIV =  1;
    public static final int MUL =  2;
    public static final int SUB =  3;
    // memory
    public static final int ALB =  4;
    public static final int ALI =  5;
    public static final int ALR =  6;
    public static final int ALS =  7;
    public static final int LDB =  8;
    public static final int LDI =  9;
    public static final int LDR = 10;
    public static final int LDS = 11;
    public static final int LDV = 12;
    public static final int STR = 13;
    public static final int STC = 14;
    // logical
    public static final int AND = 15;
    public static final int NOT = 16;
    public static final int OR  = 17;
    // relational
    public static final int BGE = 18;
    public static final int BGR = 19;
    public static final int DIF = 20;
    public static final int EQL = 21;
    public static final int SME = 22;
    public static final int SMR = 23;
    // branch
    public static final int JMF = 24;
    public static final int JMP = 25;
    public static final int JMT = 26;
    public static final int STP = 27;
    // IO
    public static final int REA = 28;
    public static final int WRT = 29;

    // null param
    public static final int NULL_PARAM = 0;

    // types
    public static final int NATURAL = 0;
    public static final int REAL    = 1;
    public static final int CHAR    = 2;
    public static final int BOOLEAN = 3;

    // semantic
    public static final int PROGRAM_IDENT = 0;
    public static final int VAR_NATURAL   = 1;
    public static final int VAR_REAL      = 2;
    public static final int VAR_CHAR      = 3;
    public static final int VAR_BOOLEAN   = 4;
    public static final int CONST_NATURAL = 5;
    public static final int CONST_REAL    = 6;
    public static final int CONST_CHAR    = 7;
}
