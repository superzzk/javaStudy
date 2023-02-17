package zzk.study.java.core.compiler.Syntax;

import java.util.Arrays;

public class UnaryExpressionSyntax extends ExpressionSyntax {

    private SyntaxToken operatorToken;
    private ExpressionSyntax operand;

    public UnaryExpressionSyntax(SyntaxToken syntaxToken, ExpressionSyntax expressionSyntax) {
        this.operatorToken = syntaxToken;
        this.operand = expressionSyntax;
    }

    @Override
    public SyntaxKind getKind() {
        return SyntaxKind.UnaryExpression;
    }

    @Override
    public Iterable<SyntaxNode> getChildren() {
        return Arrays.asList(operand, operatorToken);
    }

    public SyntaxToken getOperatorToken() {
        return operatorToken;
    }

    public ExpressionSyntax getOperand() {
        return operand;
    }
}
