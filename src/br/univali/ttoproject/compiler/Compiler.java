package br.univali.ttoproject.compiler;

import br.univali.ttoproject.compiler.parser.CategorizedToken;
import br.univali.ttoproject.compiler.parser.ParseException;
import br.univali.ttoproject.compiler.parser.Parser;
import br.univali.ttoproject.compiler.parser.ParserConstants;

import java.io.FileNotFoundException;
import java.io.Reader;
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

    public String parse(Reader reader) {
        parser = new Parser(reader);
        String messages = "";
        try {
            parser.Program();
        } catch (ParseException e) {
            messages = e.getMessage();
            return messages;
            //e.printStackTrace();
        } finally {
            messages = "Compiled with success.";
        }
        return messages;
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
