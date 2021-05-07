package br.univali.ttoproject.compiler.parser;

public enum TokenCategory {
    Keyword {
        public int id() {
            return 0;
        }

        @Override
        public String toString() {
            return String.format("keyword (%d)", id());
        }
    },
    SpecialSymbol {
        public int id() {
            return 1;
        }

        @Override
        public String toString() {
            return String.format("special symbol (%d)", id());
        }
    },
    Identifier {
        public int id() {
            return 2;
        }

        @Override
        public String toString() {
            return String.format("identifier (%d)", id());
        }
    },
    CharConstant {
        public int id() {
            return 3;
        }

        @Override
        public String toString() {
            return String.format("LITERAL_CONSTANT (%d)", id());
        }
    },
    NaturalConstant {
        public int id() {
            return 4;
        }

        @Override
        public String toString() {
            return String.format("natual const (%d)", id());
        }
    },
    RealConstant {
        public int id() {
            return 5;
        }

        @Override
        public String toString() {
            return String.format("real const (%d)", id());
        }
    },
    BooleanConstant {
        public int id() {
            return 6;
        }

        @Override
        public String toString() {
            return String.format("boolean const (%d)", id());
        }
    },
    Unknown {
        public int id() {
            return 7;
        }

        @Override
        public String toString() {
            return String.format("unknown (%d)", id());
        }
    }
}
