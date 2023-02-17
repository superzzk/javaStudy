package zzk.study.java.core.compiler.Syntax;

import java.util.Collections;

public class LiteralExpressionSyntax extends ExpressionSyntax {
    private SyntaxToken literalToken;
    private Object value;

    public LiteralExpressionSyntax(SyntaxToken literalToken) {
        this.literalToken = literalToken;
        this.value = literalToken.getValue();
    }

    public LiteralExpressionSyntax(SyntaxToken literalToken, Object value) {
        this.value = value;
        this.literalToken = literalToken;
    }

    @Override
    public SyntaxKind getKind() {
        return SyntaxKind.LiteralExpression;
    }

    @Override
    public Iterable<SyntaxNode> getChildren() {
        return Collections.singletonList(literalToken);
    }

    public Object getValue() {
        return value;
    }
}
