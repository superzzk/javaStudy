package zzk.study.java.core.compiler.Syntax;

import java.util.ArrayList;
import java.util.List;

import static zzk.study.java.core.compiler.Syntax.SyntaxNode.prettyPrint;

public class Lexer {
    private String text;
    private int position;

    public List<String> getDiagnostics() {
        return diagnostics;
    }

    private List<String> diagnostics = new ArrayList<>();

    public Lexer(String text) {
        this.text = text;
    }

    private Character current(){
        if(position >= text.length())
            return '\0';
        return text.charAt(position);
    }

    private void next(){
        position++;
    }

    public SyntaxToken lex(){

        if(position >= text.length())
            return new SyntaxToken(SyntaxKind.EndOfFileToken, position, "\0", null);

        if (Character.isDigit(current())) {
            int start = position;
            while (Character.isDigit(current()))
                next();
            int end = position;

            final String content = text.substring(start, end);

            return new SyntaxToken(SyntaxKind.NumberToken, start, content, Integer.parseInt(content));
        }

        if (Character.isWhitespace(current())) {
            int start = position;
            while(Character.isWhitespace(current()))
                next();
            final String content = text.substring(start, position);
            return new SyntaxToken(SyntaxKind.WhitespaceToken, start, content, null);
        }

        switch (current()) {
            case '+':
                return new SyntaxToken(SyntaxKind.PlusToken, position++, "+", null);
            case '-':
                return new SyntaxToken(SyntaxKind.MinusToken, position++, "-", null);
            case '*':
                return new SyntaxToken(SyntaxKind.StarToken, position++, "*", null);
            case '/':
                return new SyntaxToken(SyntaxKind.SlashToken, position++, "/", null);
            case '(':
                return new SyntaxToken(SyntaxKind.OpenParenthesisToken, position++, "(", null);
            case ')':
                return new SyntaxToken(SyntaxKind.CloseParenthesisToken, position++, ")", null);
            default:
                diagnostics.add("ERROR: bad character input: " + current());
                return new SyntaxToken(SyntaxKind.BadToken, position++, String.valueOf(text.charAt(position - 1)), null);
        }
    }
}
