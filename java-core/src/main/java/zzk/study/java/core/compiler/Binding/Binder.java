package zzk.study.java.core.compiler.Binding;

import zzk.study.java.core.compiler.Syntax.BinaryExpressionSyntax;
import zzk.study.java.core.compiler.Syntax.ExpressionSyntax;
import zzk.study.java.core.compiler.Syntax.LiteralExpressionSyntax;
import zzk.study.java.core.compiler.Syntax.UnaryExpressionSyntax;

import java.util.ArrayList;
import java.util.List;

public class Binder {
    private List<String> diagnostics = new ArrayList<>();

    public BoundExpression bindExpression(ExpressionSyntax syntax) throws Exception {
        switch (syntax.getKind()) {
            case LiteralExpression:
                return bindLiteralExpression((LiteralExpressionSyntax)syntax);
            case UnaryExpression:
                return bindUnaryExpression((UnaryExpressionSyntax)syntax);
            case BinaryExpression:
                return bindBinaryExpression((BinaryExpressionSyntax)syntax);
            default:
                throw new Exception("Unexpected syntax" +  syntax.getKind());
        }
    }

    private BoundExpression bindBinaryExpression(BinaryExpressionSyntax syntax) throws Exception {
        final BoundExpression boundLeft = bindExpression(syntax.getLeft());
        final BoundExpression boundRight = bindExpression(syntax.getRight());
        final BoundBinaryOperator boundOperator = BoundBinaryOperator.bind(syntax.getOperatorToken().getKind(), boundLeft.type(), boundRight.type());

        if (boundOperator == null)
        {
            diagnostics.add("Binary operator " + syntax.getOperatorToken().getText() + " is not defined for types "+ boundLeft.type()+ " and " + boundRight.type());
            return boundLeft;
        }

        return new BoundBinaryExpression(boundLeft, boundOperator, boundRight);
    }

    private BoundExpression bindUnaryExpression(UnaryExpressionSyntax syntax) throws Exception {
        final BoundExpression boundOperand = bindExpression(syntax.getOperand());
        final BoundUnaryOperator boundOperator = BoundUnaryOperator.bind(syntax.getKind(), boundOperand.type());
        if (boundOperator == null) {
            diagnostics.add("Unary operator " + syntax.getOperatorToken().getText() + " is not defined for type " + boundOperand.type());
            return boundOperand;
        }
        return new BoundUnaryExpression(boundOperator, boundOperand);
    }

    private BoundExpression bindLiteralExpression(LiteralExpressionSyntax syntax) {
        Object value = syntax == null ? 0 : syntax.getValue();
        return new BoundLiteralExpression(value);
    }
}
