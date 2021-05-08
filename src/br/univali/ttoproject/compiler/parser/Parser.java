/* Parser.java */
/* Generated By:JavaCC: Do not edit this line. Parser.java */
package br.univali.ttoproject.compiler.parser;

public class Parser implements ParserConstants {
    public String errorMessages = "";

  final public void Start() throws ParseException {
    try {
      Program();
      jj_consume_token(0);
    } catch (ParseException e) {

    }
}

  void T(int tk) throws ParseException {token = getNextToken();
    //System.out.println("cur tk: " + token.image + " " + token.kind);

    while (token != null && token.kind != tk && token.kind != EOF) {
        errorMessages += "Unexpected token '" + token.image + "' at line " + token.beginLine + ", column " + token.beginColumn + "\n";

        //System.out.println("[while] cur tk: " + token.image + " " + token.kind);

        token = getNextToken();
    }

    if (token.kind == EOF) {
        throw new ParseException();
    }
  }

  final public void Program() throws ParseException {
    HeaderSel();
    T(PROGRAM);
    T(LBRACE);
    Define();
    Execute();
    T(RBRACE);
    IdentifierSel();
}

  final public void HeaderSel() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case HEADER_COMMENT:{
      jj_consume_token(HEADER_COMMENT);
      break;
      }
    default:
      jj_la1[0] = jj_gen;
      Epsilon();
    }
}

  final public void IdentifierSel() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case IDENTIFIER:{
      jj_consume_token(IDENTIFIER);
      break;
      }
    default:
      jj_la1[1] = jj_gen;
      Epsilon();
    }
}

  final public void Define() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case DEFINE:{
      jj_consume_token(DEFINE);
      T(LBRACE);
      VariableBlock();
      T(RBRACE);
      break;
      }
    default:
      jj_la1[2] = jj_gen;
      Epsilon();
    }
}

  final public void VariableBlock() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case NOT:{
      NotVariable();
      VariableSel1();
      break;
      }
    case VARIABLE:{
      Variable();
      VariableSel2();
      break;
      }
    default:
      jj_la1[3] = jj_gen;
      Epsilon();
    }
}

  final public void VariableSel1() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case VARIABLE:{
      Variable();
      break;
      }
    default:
      jj_la1[4] = jj_gen;
      Epsilon();
    }
}

  final public void VariableSel2() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case NOT:{
      NotVariable();
      break;
      }
    default:
      jj_la1[5] = jj_gen;
      Epsilon();
    }
}

  final public void Execute() throws ParseException {
    try {
      jj_consume_token(EXECUTE);
      CommandBlock();
    } catch (ParseException e) {
errorMessages += "Unexpected token '" + token.image + "' at line " + token.beginLine + ", column " + token.beginColumn + ". "
                       + "Expected: 'execute ...'.\n";
        token = getNextToken();
    }
}

  final public void NotVariable() throws ParseException {
    try {
      jj_consume_token(NOT);
      T(VARIABLE);
      NotVariableDecl();
      NotVariableSel();
    } catch (ParseException e) {
errorMessages += "Unexpected token '" + token.image + "' at line " + token.beginLine + ", column " + token.beginColumn + ". "
                       + "Expected: 'not variable ...'.\n";
        token = getNextToken();
    }
}

  final public void NotVariableSel() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case NATURAL_TYPE:
    case REAL_TYPE:
    case CHAR_TYPE:
    case BOOLEAN_TYPE:{
      NotVariableDecl();
      NotVariableSel();
      break;
      }
    default:
      jj_la1[6] = jj_gen;
      Epsilon();
    }
}

  final public void NotVariableDecl() throws ParseException {
    Type();
    T(IS);
    IdentifierListValue();
}

  final public void IdentifierListValue() throws ParseException {
    try {
      jj_consume_token(IDENTIFIER);
      Value();
      IdentifierListValue1();
    } catch (ParseException e) {
errorMessages += "Unexpected token '" + token.image + "' at line " + token.beginLine + ", column " + token.beginColumn + ". "
                       + "Expected an identifier.\n";
        token = getNextToken();
    }
}

  final public void IdentifierListValue1() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case COMMA:{
      jj_consume_token(COMMA);
      IdentifierListValue();
      break;
      }
    default:
      jj_la1[7] = jj_gen;
      Epsilon();
    }
}

  final public void Variable() throws ParseException {
    try {
      jj_consume_token(VARIABLE);
      VariableDecl();
      VariableSel();
    } catch (ParseException e) {
errorMessages += "Unexpected token '" + token.image + "' at line " + token.beginLine + ", column " + token.beginColumn + ". "
                       + "Expected: 'variable ...'.\n";
        token = getNextToken();
    }
}

  final public void VariableSel() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case NATURAL_TYPE:
    case REAL_TYPE:
    case CHAR_TYPE:
    case BOOLEAN_TYPE:{
      VariableDecl();
      VariableSel();
      break;
      }
    default:
      jj_la1[8] = jj_gen;
      Epsilon();
    }
}

  final public void VariableDecl() throws ParseException {
    Type();
    T(IS);
    IdentifierList();
    T(DOT);
}

  final public void IdentifierList() throws ParseException {
    try {
      jj_consume_token(IDENTIFIER);
      Index();
      IdentifierList1();
    } catch (ParseException e) {
errorMessages += "Unexpected token '" + token.image + "' at line " + token.beginLine + ", column " + token.beginColumn + ". "
                       + "Expected: 'identifier ...'.\n";
        token = getNextToken();
    }
}

  final public void IdentifierList1() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case COMMA:{
      jj_consume_token(COMMA);
      IdentifierList();
      break;
      }
    default:
      jj_la1[9] = jj_gen;
      Epsilon();
    }
}

  final public void Set() throws ParseException {
    try {
      jj_consume_token(SET);
      Expression();
      T(TO);
      IdentifierList();
    } catch (ParseException e) {
errorMessages += "Unexpected token '" + token.image + "' at line " + token.beginLine + ", column " + token.beginColumn + ". "
                       + "Expected: 'set ...'.\n";
        token = getNextToken();
    }
}

  final public void Get() throws ParseException {
    jj_consume_token(GET);
    T(LBRACE);
    IdentifierList();
    T(RBRACE);
}

  final public void Put() throws ParseException {
    try {
      jj_consume_token(PUT);
      T(LBRACE);
      PutList();
      T(RBRACE);
    } catch (ParseException e) {
errorMessages += "Unexpected token '" + token.image + "' at line " + token.beginLine + ", column " + token.beginColumn + ". "
                       + "Expected: 'put ...'.\n";
        token = getNextToken();
    }
}

  final public void PutList() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case IDENTIFIER:{
      IdentifierList();
      break;
      }
    case CHAR_CONST:
    case NATURAL_CONST:
    case REAL_CONST:
    case BOOLEAN_CONST:{
      Value();
      break;
      }
    default:
      jj_la1[10] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

  final public void Verify() throws ParseException {
    jj_consume_token(VERIFY);
    Expression();
    T(IS);
    FalseTrueSel();
}

  final public void FalseTrueSel() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case TRUE:{
      TrueBlock();
      FalseTrueSel1();
      break;
      }
    case FALSE:{
      FalseBlock();
      FalseTrueSel2();
      break;
      }
    default:
      jj_la1[11] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

  final public void FalseTrueSel1() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case FALSE:{
      FalseBlock();
      break;
      }
    default:
      jj_la1[12] = jj_gen;
      Epsilon();
    }
}

  final public void FalseTrueSel2() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case TRUE:{
      TrueBlock();
      break;
      }
    default:
      jj_la1[13] = jj_gen;
      Epsilon();
    }
}

  final public void TrueBlock() throws ParseException {
    jj_consume_token(TRUE);
    CommandBlock();
}

  final public void FalseBlock() throws ParseException {
    jj_consume_token(FALSE);
    CommandBlock();
}

  final public void Loop() throws ParseException {
    jj_consume_token(LOOP);
    CommandBlock();
    T(WHILE);
    Expression();
    T(IS);
    T(TRUE);
}

  final public void While() throws ParseException {
    jj_consume_token(WHILE);
    Expression();
    T(IS);
    T(TRUE);
    T(DO);
    CommandBlock();
}

  final public void CommandBlock() throws ParseException {
    jj_consume_token(LBRACE);
    Command();
    CommandList();
    T(RBRACE);
}

  final public void CommandList() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case SET:
    case GET:
    case PUT:
    case VERIFY:
    case LOOP:
    case WHILE:{
      Command();
      CommandList();
      break;
      }
    default:
      jj_la1[14] = jj_gen;
      Epsilon();
    }
}

  final public void Command() throws ParseException {
    CommandSel();
    T(DOT);
}

  final public void CommandSel() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case SET:{
      Set();
      break;
      }
    case GET:{
      Get();
      break;
      }
    case PUT:{
      Put();
      break;
      }
    case VERIFY:{
      Verify();
      break;
      }
    case LOOP:{
      Loop();
      break;
      }
    case WHILE:{
      While();
      break;
      }
    default:
      jj_la1[15] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

  final public void Expression() throws ParseException {
    ExprArithLogic();
    ExpressionSel();
}

  final public void ExpressionSel() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case EQUAL:{
      jj_consume_token(EQUAL);
      ExprArithLogic();
      break;
      }
    case DIFFERENT:{
      jj_consume_token(DIFFERENT);
      ExprArithLogic();
      break;
      }
    case SMALLER:{
      jj_consume_token(SMALLER);
      ExprArithLogic();
      break;
      }
    case GREATER:{
      jj_consume_token(GREATER);
      ExprArithLogic();
      break;
      }
    case SMALLER_EQUAL:{
      jj_consume_token(SMALLER_EQUAL);
      ExprArithLogic();
      break;
      }
    case GREATER_EQUAL:{
      jj_consume_token(GREATER_EQUAL);
      ExprArithLogic();
      break;
      }
    default:
      jj_la1[16] = jj_gen;
      Epsilon();
    }
}

  final public void ExprArithLogic() throws ParseException {
    Term2();
    LessPriority();
}

  final public void LessPriority() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case PLUS:{
      jj_consume_token(PLUS);
      Term2();
      LessPriority();
      break;
      }
    case MINUS:{
      jj_consume_token(MINUS);
      Term2();
      LessPriority();
      break;
      }
    case OR:{
      jj_consume_token(OR);
      Term2();
      LessPriority();
      break;
      }
    default:
      jj_la1[17] = jj_gen;
      Epsilon();
    }
}

  final public void Term2() throws ParseException {
    Term1();
    MidPriority();
}

  final public void MidPriority() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case MULTIPLICATION:{
      jj_consume_token(MULTIPLICATION);
      Term1();
      MidPriority();
      break;
      }
    case DIVISION:{
      jj_consume_token(DIVISION);
      Term1();
      MidPriority();
      break;
      }
    case INTEGER_DIVISION:{
      jj_consume_token(INTEGER_DIVISION);
      Term1();
      MidPriority();
      break;
      }
    case REST:{
      jj_consume_token(REST);
      Term1();
      MidPriority();
      break;
      }
    case AND:{
      jj_consume_token(AND);
      Term1();
      MidPriority();
      break;
      }
    default:
      jj_la1[18] = jj_gen;
      Epsilon();
    }
}

  final public void Term1() throws ParseException {
    Element();
    GreatPriority();
}

  final public void GreatPriority() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case POWER:{
      jj_consume_token(POWER);
      Element();
      GreatPriority();
      break;
      }
    default:
      jj_la1[19] = jj_gen;
      Epsilon();
    }
}

  final public void Element() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case IDENTIFIER:{
      jj_consume_token(IDENTIFIER);
      Index();
      break;
      }
    case CHAR_CONST:
    case NATURAL_CONST:
    case REAL_CONST:
    case BOOLEAN_CONST:{
      Value();
      break;
      }
    case LPARENTHESES:{
      jj_consume_token(LPARENTHESES);
      Expression();
      T(RPARANTHESES);
      break;
      }
    case NOT:{
      jj_consume_token(NOT);
      T(LPARENTHESES);
      Expression();
      T(RPARANTHESES);
      break;
      }
    default:
      jj_la1[20] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

  final public void Index() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case LBRACKET:{
      jj_consume_token(LBRACKET);
      T(NATURAL_CONST);
      T(RBRACKET);
      break;
      }
    default:
      jj_la1[21] = jj_gen;
      Epsilon();
    }
}

  final public void Value() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case CHAR_CONST:{
      jj_consume_token(CHAR_CONST);
      break;
      }
    case NATURAL_CONST:{
      jj_consume_token(NATURAL_CONST);
      break;
      }
    case REAL_CONST:{
      jj_consume_token(REAL_CONST);
      break;
      }
    case BOOLEAN_CONST:{
      jj_consume_token(BOOLEAN_CONST);
      break;
      }
    default:
      jj_la1[22] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

  final public void Type() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case CHAR_TYPE:{
      jj_consume_token(CHAR_TYPE);
      break;
      }
    case NATURAL_TYPE:{
      jj_consume_token(NATURAL_TYPE);
      break;
      }
    case REAL_TYPE:{
      jj_consume_token(REAL_TYPE);
      break;
      }
    case BOOLEAN_TYPE:{
      jj_consume_token(BOOLEAN_TYPE);
      break;
      }
    default:
      jj_la1[23] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

  final public void Epsilon() throws ParseException {

}

  /** Generated Token Manager. */
  public ParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[24];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static private int[] jj_la1_2;
  static {
	   jj_la1_init_0();
	   jj_la1_init_1();
	   jj_la1_init_2();
	}
	private static void jj_la1_init_0() {
	   jj_la1_0 = new int[] {0x0,0x0,0x400,0x1800,0x1000,0x800,0x3c000,0x0,0x3c000,0x0,0x0,0x0,0x0,0x0,0x3e80000,0x3e80000,0x0,0x0,0x0,0x0,0x800,0x40000000,0x0,0x3c000,};
	}
	private static void jj_la1_init_1() {
	   jj_la1_1 = new int[] {0x10000000,0x20000000,0x0,0x0,0x0,0x0,0x0,0x8,0x0,0x8,0x20f00000,0xc000000,0x8000000,0x4000000,0x0,0x0,0x1f800,0x40030,0x20780,0x40,0x20f00001,0x0,0xf00000,0x0,};
	}
	private static void jj_la1_init_2() {
	   jj_la1_2 = new int[] {0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,};
	}

  /** Constructor with InputStream. */
  public Parser(java.io.InputStream stream) {
	  this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public Parser(java.io.InputStream stream, String encoding) {
	 try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source = new ParserTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 24; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
	  ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
	 try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 24; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public Parser(java.io.Reader stream) {
	 jj_input_stream = new SimpleCharStream(stream, 1, 1);
	 token_source = new ParserTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 24; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
	if (jj_input_stream == null) {
	   jj_input_stream = new SimpleCharStream(stream, 1, 1);
	} else {
	   jj_input_stream.ReInit(stream, 1, 1);
	}
	if (token_source == null) {
 token_source = new ParserTokenManager(jj_input_stream);
	}

	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 24; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public Parser(ParserTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 24; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(ParserTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 24; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
	 Token oldToken;
	 if ((oldToken = token).next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 if (token.kind == kind) {
	   jj_gen++;
	   return token;
	 }
	 token = oldToken;
	 jj_kind = kind;
	 throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
	 if (token.next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 jj_gen++;
	 return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
	 Token t = token;
	 for (int i = 0; i < index; i++) {
	   if (t.next != null) t = t.next;
	   else t = t.next = token_source.getNextToken();
	 }
	 return t;
  }

  private int jj_ntk_f() {
	 if ((jj_nt=token.next) == null)
	   return (jj_ntk = (token.next=token_source.getNextToken()).kind);
	 else
	   return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
	 jj_expentries.clear();
	 boolean[] la1tokens = new boolean[65];
	 if (jj_kind >= 0) {
	   la1tokens[jj_kind] = true;
	   jj_kind = -1;
	 }
	 for (int i = 0; i < 24; i++) {
	   if (jj_la1[i] == jj_gen) {
		 for (int j = 0; j < 32; j++) {
		   if ((jj_la1_0[i] & (1<<j)) != 0) {
			 la1tokens[j] = true;
		   }
		   if ((jj_la1_1[i] & (1<<j)) != 0) {
			 la1tokens[32+j] = true;
		   }
		   if ((jj_la1_2[i] & (1<<j)) != 0) {
			 la1tokens[64+j] = true;
		   }
		 }
	   }
	 }
	 for (int i = 0; i < 65; i++) {
	   if (la1tokens[i]) {
		 jj_expentry = new int[1];
		 jj_expentry[0] = i;
		 jj_expentries.add(jj_expentry);
	   }
	 }
	 int[][] exptokseq = new int[jj_expentries.size()][];
	 for (int i = 0; i < jj_expentries.size(); i++) {
	   exptokseq[i] = jj_expentries.get(i);
	 }
	 return new ParseException(token, exptokseq, tokenImage);
  }

  private boolean trace_enabled;

/** Trace enabled. */
  final public boolean trace_enabled() {
	 return trace_enabled;
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}
