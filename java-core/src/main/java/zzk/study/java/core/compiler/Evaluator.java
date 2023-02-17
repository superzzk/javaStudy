package zzk.study.java.core.compiler;

import zzk.study.java.core.compiler.Binding.BoundBinaryExpression;
import zzk.study.java.core.compiler.Binding.BoundExpression;
import zzk.study.java.core.compiler.Binding.BoundLiteralExpression;
import zzk.study.java.core.compiler.Binding.BoundUnaryExpression;

import java.util.Objects;


public class Evaluator {
    private BoundExpression root;

    public Evaluator(BoundExpression root) {
        this.root = root;
    }

    public Object evaluate() throws Exception {
        return evaluateExpression(root);
    }

    private Object evaluateExpression(BoundExpression node) throws Exception {
        if (node instanceof BoundLiteralExpression)
            return ((BoundLiteralExpression) node).getValue();

        if (node instanceof BoundUnaryExpression) {
            BoundUnaryExpression uExpression = (BoundUnaryExpression) node;
            final Object operand = evaluateExpression(uExpression.getOperand());

            switch (uExpression.getOp().getKind()) {
                case Identity:
                    return (int) operand;
                case Negation:
                    return -(int) operand;
                case LogicalNegation:
                    return !(boolean) operand;
                default:
                    throw new Exception("Unexpected unary operator " + uExpression.getOp());
            }
        }

        if (node instanceof BoundBinaryExpression) {
            BoundBinaryExpression binaryExpression = (BoundBinaryExpression) node;
            final Object left = evaluateExpression(binaryExpression.getLeft());
            final Object right = evaluateExpression(binaryExpression.getRight());

            switch (binaryExpression.getOp().getKind()) {
                case Addition:
                    return (int) left + (int) right;
                case Subtraction:
                    return (int) left - (int) right;
                case Multiplication:
                    return (int) left * (int) right;
                case Division:
                    return (int) left / (int) right;
                case LogicalAnd:
                    return (boolean) left && (boolean) right;
                case LogicalOr:
                    return (boolean) left || (boolean) right;
                case Equals:
                    return Objects.equals(left, right);
                case NotEquals:
                    return !Objects.equals(left, right);
                default:
                    throw new Exception("Unexpected binary operator " + binaryExpression.getOp());
            }
        }

        throw new Exception("Unexpected node " + node.getKind());
    }
}

