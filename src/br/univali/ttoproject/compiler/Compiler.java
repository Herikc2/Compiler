package br.univali.ttoproject.compiler;

import br.univali.ttoproject.compiler.parser.CategorizedToken;
import br.univali.ttoproject.compiler.parser.ParseException;
import br.univali.ttoproject.compiler.parser.Parser;
import br.univali.ttoproject.compiler.parser.ParserConstants;
import br.univali.ttoproject.ide.components.Log;
import br.univali.ttoproject.vm.Instruction;

import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Compiler {

    private Parser parser;

    public Compiler() {
        setParser(null);
    }

    private String buildLexicalErrorMessage(CategorizedToken token) {
        return "Lexical error at line " + token.beginLine + ", column " + token.beginColumn
                + ". The following character '" + token.image + "' is invalid.\n";
    }

    public ArrayList<Instruction<Integer, Object>> compile(String code, Log log) {
        var messages = "";
        ArrayList<Instruction<Integer, Object>> program = new ArrayList<>();

        parser = new Parser(new StringReader(code));
        messages += lexer();
        parser.ReInit(new StringReader(code));
        parser();
        messages += parser.errorMessages;

        if (messages.isEmpty()) {
            log.setText("Program successfully compiled.");

            // debug
//            program.add(new Instruction<>(VMConstants.LDS, "Digite: "));
//            program.add(new Instruction<>(VMConstants.WRT, VMConstants.NULL_PARAM));
//            program.add(new Instruction<>(VMConstants.REA, VMConstants.CHAR));
//            program.add(new Instruction<>(VMConstants.LDS, "Bem vindo, "));
//            program.add(new Instruction<>(VMConstants.WRT, VMConstants.NULL_PARAM));
//            program.add(new Instruction<>(VMConstants.WRT, VMConstants.NULL_PARAM));
//            program.add(new Instruction<>(VMConstants.LDS, "."));
//            program.add(new Instruction<>(VMConstants.WRT, VMConstants.NULL_PARAM));
            // debug

        } else {
            log.setText(messages);
            log.requestFocus();

            return null;
        }

        return program;
    }

    public String lexer() {
        StringBuilder messages = new StringBuilder();

        CategorizedToken token = (CategorizedToken) parser.getNextToken();
        while (token.kind != ParserConstants.EOF) {
            if (token.isUnknownKind()) {
                messages.append(buildLexicalErrorMessage(token));
            } else {
                // DEBUG DO TOKEN
                //messages += token.toString();
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

    public Parser getParser() {
        return parser;
    }

    public void setParser(Parser parser) {
        this.parser = parser;
    }
}
