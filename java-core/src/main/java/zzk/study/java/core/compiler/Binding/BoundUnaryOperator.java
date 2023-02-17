package zzk.study.java.core.compiler.Binding;

import zzk.study.java.core.compiler.Syntax.SyntaxKind;

/**
 * TODO:
 *
 * @author zhangzhongkun02
 * @date 2022/5/12 9:00 PM
 */
public class BoundUnaryOperator {
    private SyntaxKind syntaxKind;
    private BoundUnaryOperatorKind kind;
    private Class<?> operandType;
    private Class<?> type;

    public BoundUnaryOperator(SyntaxKind syntaxKind, BoundUnaryOperatorKind kind, Class operandType, Class type) {
        this.syntaxKind = syntaxKind;
        this.kind = kind;
        this.operandType = operandType;
        this.type = type;
    }

    private static BoundUnaryOperator[] operators = {
            new BoundUnaryOperator(SyntaxKind.BangToken, BoundUnaryOperatorKind.LogicalNegation, Boolean.class),
            new BoundUnaryOperator(SyntaxKind.PlusToken, BoundUnaryOperatorKind.Identity, Integer.class),
            new BoundUnaryOperator(SyntaxKind.MinusToken, BoundUnaryOperatorKind.Negation, Integer.class),
    };

    public BoundUnaryOperator(SyntaxKind syntaxKind, BoundUnaryOperatorKind kind, Class<?> operandType) {
        this(syntaxKind, kind, operandType, operandType);
    }

    public static BoundUnaryOperator bind(SyntaxKind syntaxKind, Class<?> operandType) {
        for (BoundUnaryOperator op : operators) {
            if (op.syntaxKind == syntaxKind && op.operandType == operandType)
                return op;
        }
        return null;
    }

    public SyntaxKind getSyntaxKind() {
        return syntaxKind;
    }

    public BoundUnaryOperatorKind getKind() {
        return kind;
    }

    public Class<?> getOperandType() {
        return operandType;
    }

    public Class<?> getType() {
        return type;
    }

    public static BoundUnaryOperator[] getOperators() {
        return operators;
    }
}
