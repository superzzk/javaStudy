package zzk.study.java.core.compiler.Syntax;

import java.util.Arrays;


public class ParenthesizedExpressionSyntax extends ExpressionSyntax {

    SyntaxToken openParenthesisToken;
    ExpressionSyntax expression;
    SyntaxToken closeParenthesisToken;

    public ParenthesizedExpressionSyntax(SyntaxToken openParenthesisToken, ExpressionSyntax expression, SyntaxToken closeParenthesisToken) {
        this.openParenthesisToken = openParenthesisToken;
        this.expression = expression;
        this.closeParenthesisToken = closeParenthesisToken;
    }

    @Override
    public SyntaxKind getKind() {
        return SyntaxKind.ParenthesizedExpression;
    }

    @Override
    public Iterable<SyntaxNode> getChildren() {
        return Arrays.asList(openParenthesisToken, expression, closeParenthesisToken);
    }
}
