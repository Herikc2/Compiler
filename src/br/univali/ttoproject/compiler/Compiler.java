package br.univali.ttoproject.compiler;

import br.univali.ttoproject.compiler.parser.CategorizedToken;
import br.univali.ttoproject.compiler.parser.ParseException;
import br.univali.ttoproject.compiler.parser.Parser;
import br.univali.ttoproject.compiler.parser.ParserConstants;
import br.univali.ttoproject.vm.Instruction;

import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Compiler {

    private Parser parser;

    private ArrayList<Instruction<Integer, Object>> program;
    private String messages;

    public Compiler() {
        setParser(null);
    }

    private String buildLexicalErrorMessage(CategorizedToken token) {
        return "Lexical error at line " + token.beginLine + ", column " + token.beginColumn
                + ". The following character '" + token.image + "' is invalid.\n";
    }

    public boolean compile(String input) {
        parser = new Parser(new StringReader(input));

        try {
            parser.Start();

            if (parser.getErrorMessages().isEmpty()) {
                this.messages = "Program successfully compiled.";
                this.program = parser.getSemanticAnalysis().getProgram();
                return true;
            } else {
                this.messages = parser.getErrorMessages();
                this.program = null;
                return false;
            }
        } catch (ParseException e) {
            this.messages += e.getMessage();
            this.program = null;
            return false;
        }
    }

    public String lexer() {
        StringBuilder messages = new StringBuilder();

        CategorizedToken token = (CategorizedToken) parser.getNextToken();
        while (token.kind != ParserConstants.EOF) {
            if (token.isUnknownKind()) {
                messages.append(buildLexicalErrorMessage(token));
            } else {
                // DEBUG DO TOKEN
                //messages.append(token);
            }
            token = (CategorizedToken) parser.getNextToken();
        }

        return messages.toString();
    }

    public void parser() {
        try {
            parser.Start();
        } catch (ParseException e) {
            //e.printStackTrace();
        }
    }

    public String build(Reader reader) {
        parser = new Parser(reader);

        try {
            return tokenize()
                    .stream()
                    .map((token) -> token.isUnknownKind() ? buildLexicalErrorMessage(token) : token.toString())
                    .collect(Collectors.joining());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "File not found";
        } catch (Error e) {
            return e.getMessage();
        }
    }

    public List<CategorizedToken> tokenize() throws FileNotFoundException {
        List<CategorizedToken> tokens = new ArrayList<>();

        CategorizedToken token = (CategorizedToken) parser.getNextToken();

        while (token.kind != ParserConstants.EOF) {
            tokens.add(token);
            token = (CategorizedToken) parser.getNextToken();
        }

        return tokens;
    }

    public ArrayList<Instruction<Integer, Object>> getProgram() {
        return program;
    }

    public String getMessages() {
        return messages;
    }

    public Parser getParser() {
        return parser;
    }

    public void setParser(Parser parser) {
        this.parser = parser;
    }
}
