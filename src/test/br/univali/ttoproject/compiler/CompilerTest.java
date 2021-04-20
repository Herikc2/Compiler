package test.br.univali.ttoproject.compiler;

import br.univali.ttoproject.compiler.Compiler;
import br.univali.ttoproject.compiler.parser.*;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestCase {
    public final String input;
    public final List<Token> expected;

    public TestCase(String input, List<Token> expected) {
        this.input = input;
        this.expected = expected;
    }
}

public class CompilerTest {
    @Test
    public void ignoresComments() {
        List<String> testCases = Arrays.asList(":- x y + 1 123 -abd", "/* hello world 1 + 2 */");

        for (String input : testCases) {
            var compiler = new Compiler();

            compiler.setParser(new Parser(new StringReader(input)));

            try {
                var actual = compiler.tokenize();

                assertTrue(actual.isEmpty());
            } catch (Exception e) {
                System.out.println(e);
                assertTrue(false);
            }
        }
    }

    @Test
    public void isCaseInsensitiveWhenLexingKeywords() {
        List<TestCase> testCases = Arrays.asList(
                new TestCase("define",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.DEFINE, "define", 1, 1, 1, 6))),
                new TestCase("Define",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.DEFINE, "Define", 1, 1, 1, 6))),
                new TestCase("DeFiNe",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.DEFINE, "DeFiNe", 1, 1, 1, 6))),
                new TestCase("DEFINE",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.DEFINE, "DEFINE", 1, 1, 1, 6))));

        for (TestCase testCase : testCases) {
            var compiler = new Compiler();

            compiler.setParser(new Parser(new StringReader(testCase.input)));

            try {
                var actual = compiler.tokenize();
                assertEquals(testCase.expected, actual);
            } catch (Exception e) {
                System.out.println(e);
                assertTrue(false);
            }
        }
    }

    @Test
    public void categorizesKeywordTokens() {
        List<TestCase> testCases = Arrays.asList(
                new TestCase("program",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.PROGRAM, "program", 1, 1, 1, 7))),
                new TestCase("define",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.DEFINE, "define", 1, 1, 1, 6))),
                new TestCase("not",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.NOT, "not", 1, 1, 1, 3))),
                new TestCase("variable",
                        Arrays
                                .asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.VARIABLE, "variable", 1, 1, 1, 8))),
                new TestCase("is",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.IS, "is", 1, 1, 1, 2))),
                new TestCase("natural",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.NATURAL_TYPE, "natural", 1, 1, 1, 7))),
                new TestCase("real",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.REAL_TYPE, "real", 1, 1, 1, 4))),
                new TestCase("char",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.CHAR_TYPE, "char", 1, 1, 1, 4))),
                new TestCase("boolean",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.BOOLEAN_TYPE, "boolean", 1, 1, 1, 7))),
                new TestCase("execute",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.EXECUTE, "execute", 1, 1, 1, 7))),
                new TestCase("set",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.SET, "set", 1, 1, 1, 3))),
                new TestCase("to",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.TO, "to", 1, 1, 1, 2))),
                new TestCase("get",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.GET, "get", 1, 1, 1, 3))),
                new TestCase("put",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.PUT, "put", 1, 1, 1, 3))),
                new TestCase("verify",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.VERIFY, "verify", 1, 1, 1, 6))),
                new TestCase("true",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.TRUE, "true", 1, 1, 1, 4))),
                new TestCase("false",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.FALSE, "false", 1, 1, 1, 5))),
                new TestCase("loop",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.LOOP, "loop", 1, 1, 1, 4))),
                new TestCase("while",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.WHILE, "while", 1, 1, 1, 5))),
                new TestCase("do",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.DO, "do", 1, 1, 1, 2))));

        for (TestCase testCase : testCases) {
            var compiler = new Compiler();

            compiler.setParser(new Parser(new StringReader(testCase.input)));

            try {
                var actual = compiler.tokenize();
                assertEquals(testCase.expected, actual);
            } catch (Exception e) {
                System.out.println(e);
                assertTrue(false);
            }
        }
    }

    @Test
    public void categorizesIdentifierTokens() {
        List<TestCase> testCases = Arrays
                .asList(
                        new TestCase("x",
                                Arrays.asList(
                                        new CategorizedToken(TokenCategory.Identifier, ParserConstants.IDENTIFIER, "x", 1, 1, 1, 1))),
                        new TestCase("xyz",
                                Arrays.asList(
                                        new CategorizedToken(TokenCategory.Identifier, ParserConstants.IDENTIFIER, "xyz", 1, 1, 1, 3))),
                        new TestCase("xyz123", Arrays.asList(
                                new CategorizedToken(TokenCategory.Identifier, ParserConstants.IDENTIFIER, "xyz123", 1, 1, 1, 6))));

        for (TestCase testCase : testCases) {
            var compiler = new Compiler();

            compiler.setParser(new Parser(new StringReader(testCase.input)));

            try {
                var actual = compiler.tokenize();
                assertEquals(testCase.expected, actual);
            } catch (Exception e) {
                System.out.println(e);
                assertTrue(false);
            }
        }
    }

    @Test
    public void categorizesLiterals() {
        /*List<TestCase> testCases = Arrays.asList(
                new TestCase("123",
                        Arrays.asList(
                                new CategorizedToken(TokenCategory.IntegerConstant, ParserConstants.UNSIGNED, "123", 1, 1, 1, 3))),
                new TestCase("-123",
                        Arrays.asList(
                                new CategorizedToken(TokenCategory.IntegerConstant, ParserConstants.SIGNED, "-123", 1, 1, 1, 4))),
                new TestCase("3.14",
                        Arrays.asList(
                                new CategorizedToken(TokenCategory.RealConstant, ParserConstants.REAL_UNSIGNED, "3.14", 1, 1, 1, 4))),
                new TestCase("-3.14",
                        Arrays.asList(
                                new CategorizedToken(TokenCategory.RealConstant, ParserConstants.REAL_SIGNED, "-3.14", 1, 1, 1, 5))),
                new TestCase("\"hello world\"", Arrays.asList(new CategorizedToken(TokenCategory.LiteralConstant,
                        ParserConstants.CHAR_CONST, "\"hello world\"", 1, 1, 1, 13))));

        for (TestCase testCase : testCases) {
            var compiler = new Compiler();

            compiler.setParser(new Parser(new StringReader(testCase.input)));

            try {
                var actual = compiler.tokenize();
                assertEquals(testCase.expected, actual);
            } catch (Exception e) {
                System.out.println(e);
                assertTrue(false);
            }
        }*/
    }

    @Test
    public void categorizesSpecialSymbols() {
        List<TestCase> testCases = Arrays
                .asList(
                        new TestCase("{",
                                Arrays.asList(
                                        new CategorizedToken(TokenCategory.SpecialSymbol, ParserConstants.LBRACE, "{", 1, 1, 1, 1))),
                        new TestCase("}",
                                Arrays.asList(
                                        new CategorizedToken(TokenCategory.SpecialSymbol, ParserConstants.RBRACE, "}", 1, 1, 1, 1))),
                        new TestCase("(",
                                Arrays.asList(
                                        new CategorizedToken(TokenCategory.SpecialSymbol, ParserConstants.PARENTHESESL, "(", 1, 1, 1, 1))),
                        new TestCase(")",
                                Arrays.asList(
                                        new CategorizedToken(TokenCategory.SpecialSymbol, ParserConstants.PARANTHESESR, ")", 1, 1, 1, 1))),
                        new TestCase(".",
                                Arrays.asList(new CategorizedToken(TokenCategory.SpecialSymbol, ParserConstants.DOT, ".", 1, 1, 1, 1))),
                        new TestCase(",",
                                Arrays
                                        .asList(new CategorizedToken(TokenCategory.SpecialSymbol, ParserConstants.COMMA, ",", 1, 1, 1, 1))),
                        new TestCase("+",
                                Arrays
                                        .asList(new CategorizedToken(TokenCategory.SpecialSymbol, ParserConstants.PLUS, "+", 1, 1, 1, 1))),
                        new TestCase(
                                "-",
                                Arrays
                                        .asList(new CategorizedToken(TokenCategory.SpecialSymbol, ParserConstants.MINUS, "-", 1, 1, 1, 1))),
                        new TestCase(
                                "**",
                                Arrays.asList(
                                        new CategorizedToken(TokenCategory.SpecialSymbol, ParserConstants.POWER, "**", 1, 1, 1, 2))),
                        new TestCase(
                                "*",
                                Arrays.asList(new CategorizedToken(TokenCategory.SpecialSymbol, ParserConstants.MULTIPLICATION, "*", 1,
                                        1, 1, 1))),
                        new TestCase(
                                "/",
                                Arrays.asList(
                                        new CategorizedToken(TokenCategory.SpecialSymbol, ParserConstants.DIVISION, "/", 1, 1, 1, 1))),
                        new TestCase("%",
                                Arrays.asList(new CategorizedToken(TokenCategory.SpecialSymbol, ParserConstants.INTEGER_DIVISION, "%",
                                        1, 1, 1, 1))),
                        new TestCase("%%",
                                Arrays
                                        .asList(new CategorizedToken(TokenCategory.SpecialSymbol, ParserConstants.REST, "%%", 1, 1, 1, 2))),
                        new TestCase("==",
                                Arrays.asList(
                                        new CategorizedToken(TokenCategory.SpecialSymbol, ParserConstants.EQUAL, "==", 1, 1, 1, 2))),
                        new TestCase("!=",
                                Arrays.asList(
                                        new CategorizedToken(TokenCategory.SpecialSymbol, ParserConstants.DIFFERENT, "!=", 1, 1, 1, 2))),
                        new TestCase(
                                "<",
                                Arrays.asList(
                                        new CategorizedToken(TokenCategory.SpecialSymbol, ParserConstants.SMALLER, "<", 1, 1, 1, 1))),
                        new TestCase(">",
                                Arrays.asList(
                                        new CategorizedToken(TokenCategory.SpecialSymbol, ParserConstants.LARGER, ">", 1, 1, 1, 1))),
                        new TestCase("<=",
                                Arrays.asList(new CategorizedToken(TokenCategory.SpecialSymbol, ParserConstants.SMALLER_EQUAL, "<=", 1,
                                        1, 1, 2))),
                        new TestCase(">=",
                                Arrays.asList(
                                        new CategorizedToken(TokenCategory.SpecialSymbol, ParserConstants.LARGER_EQUAL, ">=", 1, 1, 1, 2))),
                        new TestCase("&",
                                Arrays.asList(new CategorizedToken(TokenCategory.SpecialSymbol, ParserConstants.AND, "&", 1, 1, 1, 1))),
                        new TestCase("|",
                                Arrays.asList(new CategorizedToken(TokenCategory.SpecialSymbol, ParserConstants.OR, "|", 1, 1, 1, 1))),
                        new TestCase("!", Arrays.asList(
                                new CategorizedToken(TokenCategory.SpecialSymbol, ParserConstants.NOT_SYMBOL, "!", 1, 1, 1, 1))));

        for (TestCase testCase : testCases) {
            var compiler = new Compiler();

            compiler.setParser(new Parser(new StringReader(testCase.input)));

            try {
                var actual = compiler.tokenize();

                assertEquals(testCase.expected, actual);
            } catch (Exception e) {
                System.out.println(e);
                assertTrue(false);
            }
        }
    }

    @Test
    public void categorizesUnknownTokens() {
        List<TestCase> testCases = Arrays.asList(new TestCase("?",
                Arrays.asList(new CategorizedToken(TokenCategory.Unknown, ParserConstants.UNKNOWN, "?", 1, 1, 1, 1))));

        for (TestCase testCase : testCases) {
            var compiler = new Compiler();

            compiler.setParser(new Parser(new StringReader(testCase.input)));

            try {
                var actual = compiler.tokenize();

                assertEquals(testCase.expected, actual);
            } catch (Exception e) {
                System.out.println(e);
                assertTrue(false);
            }
        }
    }
}
