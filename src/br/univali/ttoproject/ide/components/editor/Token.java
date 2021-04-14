package br.univali.ttoproject.ide.components.editor;

public class Token {
    private static final String[] reserved = {
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

    private static final char[] symbols = {
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
            '<',
            '>',
            '&',
            '|',
            '!'
    };

    private static final String[] specBegin = {
            ":-",
            "/*",
            "\""
    };

    public static boolean isSkip(char c){
        return c == ' ' || c == '\n' || c == '\r' || c == '\t' || isSymbol(c);
    }

    public static boolean isSymbol(char c){
        for (var token : symbols){
            if (c == token) return true;
        }
        return false;
    }

    public static boolean isNumber(char c){
        return Character.isDigit(c);
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

}
