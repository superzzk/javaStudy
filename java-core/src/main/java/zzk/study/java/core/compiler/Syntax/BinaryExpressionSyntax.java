package zzk.study.java.core.compiler.Syntax;


import java.util.Arrays;

public class BinaryExpressionSyntax extends ExpressionSyntax {
    private ExpressionSyntax left;
    private ExpressionSyntax right;
    private SyntaxToken operatorToken;

    public BinaryExpressionSyntax(ExpressionSyntax left, SyntaxToken operatorToken, ExpressionSyntax right) {
        this.left = left;
        this.operatorToken = operatorToken;
        this.right = right;
    }

    @Override
    public SyntaxKind getKind() {
        return SyntaxKind.BinaryExpression;
    }

    @Override
    public Iterable<SyntaxNode> getChildren() {
        return Arrays.asList(left, operatorToken, right);
    }

    public ExpressionSyntax getLeft() {
        return left;
    }

    public ExpressionSyntax getRight() {
        return right;
    }

    public SyntaxToken getOperatorToken() {
        return operatorToken;
    }
}
