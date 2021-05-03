package br.univali.ttoproject.compiler.parser;

public enum TokenCategory {
    Keyword {
        public int id() {
            return 0;
        }

        @Override
        public String toString() {
            return String.format("KEYWORD(%d)", id());
        }
    },
    SpecialSymbol {
        public int id() {
            return 1;
        }

        @Override
        public String toString() {
            return String.format("SPECIAL_SYMBOL(%d)", id());
        }
    },
    Identifier {
        public int id() {
            return 2;
        }

        @Override
        public String toString() {
            return String.format("IDENTIFIER(%d)", id());
        }
    },
    LiteralConstant {
        public int id() {
            return 3;
        }

        @Override
        public String toString() {
            return String.format("LITERAL_CONSTANT(%d)", id());
        }
    },
    IntegerConstant {
        public int id() {
            return 4;
        }

        @Override
        public String toString() {
            return String.format("INTEGER_CONSTANT(%d)", id());
        }
    },
    RealConstant {
        public int id() {
            return 5;
        }

        @Override
        public String toString() {
            return String.format("REAL_CONSTANT(%d)", id());
        }
    },
    BooleanConstant {
        public int id() {
            return 6;
        }

        @Override
        public String toString() {
            return String.format("BOOLEAN_CONSTANT(%d)", id());
        }
    },
    Unknown {
        public int id() {
            return 7;
        }

        @Override
        public String toString() {
            return String.format("UNKNOWN(%d)", id());
        }
    }
}
