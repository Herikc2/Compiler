package br.univali.ttoproject.ide.components.editor;

public class Token {
    private static String[] reserved = {
            "program",
            "define",
            "not",
            "variable",
            "is",
            "natural",
            "real",
            "char",
            "boolean",
            "execute",
            "set",
            "to",
            "get",
            "put",
            "verify",
            "true",
            "false",
            "loop",
            "while",
            "do"
    };

    private static char[] startSymbols = {
            '{',
            '}',
            '(',
            ')',
            '.',
            ',',
            '+',
            '-',
            '*',
            '/',
            '%',
            '=',
            '>',
            '<',
            '>',
            '&',
            '|',
            '!'
    };

    private static String[] specBegin = {
            ":-",
            "/*",
            "\""
    };

    private static String[] specEnd = {
            "\n",
            "*/",
            "\""
    };

    public static boolean isSkip(char c){
        return c == ' ' || c == '\n' || c == '\r' || c == '\t' || isStarSymbol(c);
    }

    private static boolean isStarSymbol(char c){
        for (var token : startSymbols){
            if (c == token) return true;
        }
        return false;
    }

    public static boolean isReserved(String word){
        for (var token : reserved){
            if (word.equalsIgnoreCase(token)) return true;
        }
        return false;
    }

    public static boolean isSpec(String word){
        for (var token : specBegin){
            if (word.equalsIgnoreCase(token)) return true;
        }
        return false;
    }

    public static int getSpecEndIndex(String word){
        for (var i = 0; i < specBegin.length; i++){
            if (word.equalsIgnoreCase(specBegin[i])) return i;
        }
        return -1;
    }

}
