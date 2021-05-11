package br.univali.ttoproject.compiler;

import br.univali.ttoproject.compiler.parser.CategorizedToken;
import br.univali.ttoproject.compiler.parser.ParseException;
import br.univali.ttoproject.compiler.parser.Parser;
import br.univali.ttoproject.compiler.parser.ParserConstants;
import br.univali.ttoproject.ide.components.Log;
import br.univali.ttoproject.vm.Instruction;
import br.univali.ttoproject.vm.VMConstants;

import javax.swing.*;
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

    public ArrayList<Instruction<Integer, Object>> compile(String code, Log log){
        var message = "";

        // debug
        ArrayList<Instruction<Integer, Object>> program = new ArrayList<>();
        program.add(new Instruction<>(VMConstants.LDS, "Digite: "));
        program.add(new Instruction<>(VMConstants.WRT, 0));
        program.add(new Instruction<>(VMConstants.REA, 2));
        program.add(new Instruction<>(VMConstants.WRT, 0));
        // debug

        parser = new Parser(new StringReader(code));
        try {
            parser.Start();
        } catch (ParseException e) {
            message += e.getMessage();
        }

        if (message.isEmpty()){
            log.setText("Program successfully compiled.");
        } else {
            log.setText(message);
            log.requestFocus();
        }

        return program;
    }

    public String lexer(String code) {
        String messages = "";

        //var lp = new Parser(new StringReader(code));

        CategorizedToken token = (CategorizedToken) parser.getNextToken();
        while (token.kind != ParserConstants.EOF) {
            if(token.isUnknownKind()){
                messages += buildLexicalErrorMessage(token);
            } else {
                // DEBUG DO TOKEN
                //messages += token.toString();
            }
            token = (CategorizedToken) parser.getNextToken();
        }

        return messages;
    }

    public void parser() {
        try {
            parser.Start();
        } catch (ParseException e) {
            //messages += e.getMessage();
            //return e.getMessage();
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
