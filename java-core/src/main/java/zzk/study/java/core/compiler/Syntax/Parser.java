package zzk.study.java.core.compiler.Syntax;


import java.util.ArrayList;
import java.util.List;

public class Parser {
    private SyntaxToken[] tokens;
    private int position;

    private List<String> diagnostics = new ArrayList<>();

    /**
     * 将输入text中的token全部提取出，存储在tokens中
     */
    public Parser(String text) {
        List<SyntaxToken> tokenList = new ArrayList<>();
        Lexer lexer = new Lexer(text);

        SyntaxToken token;
        do {
            token = lexer.lex();
            if (token.getKind().equals(SyntaxKind.WhitespaceToken) ||
                    token.getKind().equals(SyntaxKind.BadToken))
                continue;

            tokenList.add(token);
        } while (!token.getKind().equals(SyntaxKind.EndOfFileToken));
        tokens = tokenList.toArray(new SyntaxToken[0]);
        this.diagnostics.addAll(lexer.getDiagnostics());
    }

    private SyntaxToken peek(int offset) {
        int index = position + offset;
        if (index >= tokens.length)
            return tokens[tokens.length - 1];
        return tokens[index];
    }

    private SyntaxToken current() {
        return peek(0);
    }

    private SyntaxToken matchToken(SyntaxKind kind) {
        if (current().getKind().equals(kind)) {
            return nextToken();
        }
        diagnostics.add("ERROR: Unexpected token <" + current().getKind() + ">, expected <" + kind + ">");
        return new SyntaxToken(kind, current().getPosition(), null, null);
    }

    private SyntaxToken nextToken() {
        final SyntaxToken current = current();
        position++;
        return current;
    }

    public SyntaxTree parse() {
        final ExpressionSyntax expression = parseExpression();
        final SyntaxToken endOfFileToken = matchToken(SyntaxKind.EndOfFileToken);
        return new SyntaxTree(diagnostics, expression, endOfFileToken);
    }

    public ExpressionSyntax parseExpression() {
        return parseExpression(0);
    }
    public ExpressionSyntax parseExpression(int parentPrecedence) {
        ExpressionSyntax left;
        final int unaryOperatorPrecedence = current().getKind().getUnaryOperatorPrecedence();
        if (unaryOperatorPrecedence != 0 && unaryOperatorPrecedence >= parentPrecedence) {
            final SyntaxToken operatorToken = nextToken();
            final ExpressionSyntax operand = parseExpression(unaryOperatorPrecedence);
            left = new UnaryExpressionSyntax(operatorToken, operand);
        } else {
            left = parsePrimaryExpression();
        }
        while (true) {
            final int precedence = current().getKind().getBinaryOperatorPrecedence();
            if(precedence==0 || precedence <= parentPrecedence)
                break;
            final SyntaxToken operatorToken = nextToken();
            final ExpressionSyntax operand = parseExpression(precedence);
            left = new BinaryExpressionSyntax(left, operatorToken, operand);
        }
        return left;
    }

    public ExpressionSyntax parsePrimaryExpression() {
        if (current().getKind().equals(SyntaxKind.OpenParenthesisToken)) {
            final SyntaxToken left = nextToken();
            final ExpressionSyntax expression = parseExpression();
            final SyntaxToken right = matchToken(SyntaxKind.CloseParenthesisToken);
            return new ParenthesizedExpressionSyntax(left, expression, right);
        }
        final SyntaxToken token = matchToken(SyntaxKind.NumberToken);
        return new LiteralExpressionSyntax(token);
    }
}
