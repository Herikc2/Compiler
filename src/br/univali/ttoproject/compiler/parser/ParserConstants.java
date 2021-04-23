/* Generated By:JavaCC: Do not edit this line. ParserConstants.java */
package br.univali.ttoproject.compiler.parser;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface ParserConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int PROGRAM = 9;
  /** RegularExpression Id. */
  int DEFINE = 10;
  /** RegularExpression Id. */
  int NOT = 11;
  /** RegularExpression Id. */
  int VARIABLE = 12;
  /** RegularExpression Id. */
  int IS = 13;
  /** RegularExpression Id. */
  int NATURAL_TYPE = 14;
  /** RegularExpression Id. */
  int REAL_TYPE = 15;
  /** RegularExpression Id. */
  int CHAR_TYPE = 16;
  /** RegularExpression Id. */
  int BOOLEAN_TYPE = 17;
  /** RegularExpression Id. */
  int EXECUTE = 18;
  /** RegularExpression Id. */
  int SET = 19;
  /** RegularExpression Id. */
  int TO = 20;
  /** RegularExpression Id. */
  int GET = 21;
  /** RegularExpression Id. */
  int PUT = 22;
  /** RegularExpression Id. */
  int VERIFY = 23;
  /** RegularExpression Id. */
  int LOOP = 24;
  /** RegularExpression Id. */
  int WHILE = 25;
  /** RegularExpression Id. */
  int DO = 26;
  /** RegularExpression Id. */
  int HEADER = 27;
  /** RegularExpression Id. */
  int LBRACE = 28;
  /** RegularExpression Id. */
  int RBRACE = 29;
  /** RegularExpression Id. */
  int LBRACKET = 30;
  /** RegularExpression Id. */
  int RBRACKET = 31;
  /** RegularExpression Id. */
  int PARENTHESESL = 32;
  /** RegularExpression Id. */
  int PARANTHESESR = 33;
  /** RegularExpression Id. */
  int DOT = 34;
  /** RegularExpression Id. */
  int COMMA = 35;
  /** RegularExpression Id. */
  int PLUS = 36;
  /** RegularExpression Id. */
  int MINUS = 37;
  /** RegularExpression Id. */
  int POWER = 38;
  /** RegularExpression Id. */
  int MULTIPLICATION = 39;
  /** RegularExpression Id. */
  int DIVISION = 40;
  /** RegularExpression Id. */
  int INTEGER_DIVISION = 41;
  /** RegularExpression Id. */
  int REST = 42;
  /** RegularExpression Id. */
  int EQUAL = 43;
  /** RegularExpression Id. */
  int DIFFERENT = 44;
  /** RegularExpression Id. */
  int SMALLER = 45;
  /** RegularExpression Id. */
  int LARGER = 46;
  /** RegularExpression Id. */
  int SMALLER_EQUAL = 47;
  /** RegularExpression Id. */
  int LARGER_EQUAL = 48;
  /** RegularExpression Id. */
  int AND = 49;
  /** RegularExpression Id. */
  int OR = 50;
  /** RegularExpression Id. */
  int NOT_SYMBOL = 51;
  /** RegularExpression Id. */
  int LETTER = 52;
  /** RegularExpression Id. */
  int DIGIT = 53;
  /** RegularExpression Id. */
  int CHAR_CONST = 54;
  /** RegularExpression Id. */
  int NATURAL_CONST = 55;
  /** RegularExpression Id. */
  int REAL_CONST = 56;
  /** RegularExpression Id. */
  int BOOLEAN_CONST = 57;
  /** RegularExpression Id. */
  int IDENTIFIER = 58;
  /** RegularExpression Id. */
  int NU = 59;
  /** RegularExpression Id. */
  int ND = 60;
  /** RegularExpression Id. */
  int UNKNOWN = 61;

  /** Lexical state. */
  int DEFAULT = 0;
  /** Lexical state. */
  int BLOCK_COMMENT_STATE = 1;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\t\"",
    "\"\\n\"",
    "\"\\r\"",
    "<token of kind 5>",
    "\"/*\"",
    "\"*/\"",
    "<token of kind 8>",
    "\"program\"",
    "\"define\"",
    "\"not\"",
    "\"variable\"",
    "\"is\"",
    "\"natural\"",
    "\"real\"",
    "\"char\"",
    "\"boolean\"",
    "\"execute\"",
    "\"set\"",
    "\"to\"",
    "\"get\"",
    "\"put\"",
    "\"verify\"",
    "\"loop\"",
    "\"while\"",
    "\"do\"",
    "\":-\"",
    "\"{\"",
    "\"}\"",
    "\"[\"",
    "\"]\"",
    "\"(\"",
    "\")\"",
    "\".\"",
    "\",\"",
    "\"+\"",
    "\"-\"",
    "\"**\"",
    "\"*\"",
    "\"/\"",
    "\"%\"",
    "\"%%\"",
    "\"==\"",
    "\"!=\"",
    "\"<\"",
    "\">\"",
    "\"<=\"",
    "\">=\"",
    "\"&\"",
    "\"|\"",
    "\"!\"",
    "<LETTER>",
    "<DIGIT>",
    "<CHAR_CONST>",
    "<NATURAL_CONST>",
    "<REAL_CONST>",
    "<BOOLEAN_CONST>",
    "<IDENTIFIER>",
    "<NU>",
    "<ND>",
    "<UNKNOWN>",
  };

}
