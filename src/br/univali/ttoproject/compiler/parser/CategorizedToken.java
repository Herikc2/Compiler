package br.univali.ttoproject.compiler.parser;

import java.util.Objects;

public class CategorizedToken extends Token {
    public final TokenCategory category;

    public CategorizedToken(TokenCategory category, int kind, String image) {
        this.category = category;
        this.kind = kind;
        this.image = image;
    }

    public CategorizedToken(TokenCategory category, int kind, String image, int beginLine, int endLine, int beginColumn, int endColumn) {
        this.category = category;
        this.kind = kind;
        this.image = image;
        this.beginLine = beginLine;
        this.endLine = endLine;
        this.beginColumn = beginColumn;
        this.endColumn = endColumn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategorizedToken that = (CategorizedToken) o;
        return category == that.category && kind == that.kind && image.equals(that.image) && beginLine == that.beginLine
                && endLine == that.endLine && beginColumn == that.beginColumn && endColumn == that.endColumn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(category);
    }

    @Override
    public String toString() {
        return "Token '" + image + "' of category " + category + " at line " + beginLine + ", column " + beginColumn + ".\n";
    }

    public boolean isUnknownKind() {
        return kind == ParserConstants.UNKNOWN;
    }
}
