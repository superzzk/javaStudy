package zzk.study.java.core.compiler.Binding;

public class BoundUnaryExpression extends BoundExpression {
    private BoundUnaryOperator op;
    private BoundExpression operand;
    public BoundUnaryExpression(BoundUnaryOperator op, BoundExpression operand) {
        this.op = op;
        this.operand = operand;
    }

    @Override
    public Class type() {
        return op.getType();
    }

    @Override
    public BoundNodeKind getKind() {
        return BoundNodeKind.UnaryExpression;
    }

    public BoundExpression getOperand() {
        return operand;
    }

    public BoundUnaryOperator getOp() {
        return op;
    }
}
