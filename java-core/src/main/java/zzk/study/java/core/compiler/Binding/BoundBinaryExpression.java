package zzk.study.java.core.compiler.Binding;

public class BoundBinaryExpression extends BoundExpression {
    private BoundExpression left;
    private BoundBinaryOperator op;
    private BoundExpression right;
    public BoundBinaryExpression(BoundExpression left, BoundBinaryOperator op, BoundExpression right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }

    @Override
    public Class type() {
        return op.getType();
    }

    public BoundExpression getLeft() {
        return left;
    }

    public BoundBinaryOperator getOp() {
        return op;
    }

    public BoundExpression getRight() {
        return right;
    }

    @Override
    public BoundNodeKind getKind() {
        return BoundNodeKind.UnaryExpression;
    }
}
