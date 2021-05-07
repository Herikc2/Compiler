package br.univali.ttoproject.compiler;

import br.univali.ttoproject.compiler.parser.CategorizedToken;
import br.univali.ttoproject.compiler.parser.ParseException;
import br.univali.ttoproject.compiler.parser.Parser;
import br.univali.ttoproject.compiler.parser.ParserConstants;

import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Compiler {

    /*
     * TODO: Trabalho 4
     *
     * [X] Corrigir Léxico
     * [X] Corrigir Interface
     * [ ] Implementar Parser
     *     OBS: Será levada em consideração a qualidade das mensagens de erro (portanto, personalizar as mensagens de
     *          erro fornecidas pelo JavaCC)
     *     OBS: Realizar o tratamento de erros sintáticos conforme orientações do capítulo 5 da obra referenciada no
     *          plano de ensino Como construir um compilador utilizando ferramentas Java, de Márcio Eduardo Delamaro.
     *     OBS: A análise sintática NÃO deve ser interrompida no primeiro erro sintático.
     *     [X] Entrada: lista (arquivo ou estrutura de dados lista) de tokens com suas respectivas categorias (números)
     *                  de acordo com a tabela de símbolos terminais específica para a linguagem (saída do analisador léxico).
     *     [ ] Saída: mensagem indicando que o programa está sintaticamente correto (programa compilado com sucesso)
     *                OU
     *                mensagens de erro indicando a ocorrência de erro(s) léxico(s) ou sintático. Neste caso, indicar a
     *                linha onde ocorreu o erro e o tipo de erro encontrado fazendo um diagnóstico de boa qualidade,
     *                ou seja, emitindo uma mensagem adequada, tal como palavra reservada inválida, constante literal
     *                não finalizada, expressão aritmética inválida, encontrado . esperado ;, etc...
     *     [ ] Mensagens: Personalizar as mensagens de erro fornecidas pelo JavaCC
     *
     * [ ] Entregar:
     *     [ ] Documento PDF contendo a GLC da linguagem 2021.1 (na notação BNF, transcrever do JavaCC) e as mensagens
     *         de erros léxico e sintáticos
     *     [ ] Cópias do programa fonte (tdo o projeto) e do programa executável (gerar o .JAR) postados no ambiente.
     *     [ ] COMPACTAR os arquivos com o número da equipe (conforme a correção do analisador léxico)
     */

    private Parser parser;

    public Compiler() {
        setParser(null);
    }

    private String buildLexicalErrorMessage(CategorizedToken token) {
        return "Lexical error at line " + token.beginLine + ", column " + token.beginColumn
                + ". The following character '" + token.image + "' is invalid.\n";
    }

    public String compile(String code){
        parser = new Parser(new StringReader(code));

        //parser.errorMessages += lexer(code);
        parser();

        return parser.errorMessages;
    }

    public String lexer(String code) {
        String messages = "";

        var lp = new Parser(new StringReader(code));

        CategorizedToken token = (CategorizedToken) lp.getNextToken();
        while (token.kind != ParserConstants.EOF) {
            if(token.isUnknownKind()){
                messages += buildLexicalErrorMessage(token);
            }
            token = (CategorizedToken) lp.getNextToken();
        }

        return messages;
    }

    public void parser() {
        try {
            parser.Start();
        } catch (ParseException e) {
            //messages += e.getMessage();
            //return messages;
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
