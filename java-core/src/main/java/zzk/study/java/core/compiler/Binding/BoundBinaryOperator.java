package zzk.study.java.core.compiler.Binding;

import zzk.study.java.core.compiler.Syntax.SyntaxKind;

public class BoundBinaryOperator {
    private SyntaxKind syntaxKind;
    private BoundBinaryOperatorKind kind;
    private Class<?> leftType;
    private Class<?> rightType;
    private Class<?> type;

    public BoundBinaryOperator(SyntaxKind syntaxKind, BoundBinaryOperatorKind kind, Class<?> leftType, Class<?> rightType, Class<?> resultType) {
        this.syntaxKind = syntaxKind;
        this.kind = kind;
        this.leftType = leftType;
        this.rightType = rightType;
        this.type = resultType;
    }

    public BoundBinaryOperator(SyntaxKind syntaxKind, BoundBinaryOperatorKind kind, Class<?> operandType, Class<?> resultType) {
        this(syntaxKind, kind, operandType, operandType, resultType);

    }

    public BoundBinaryOperator(SyntaxKind syntaxKind, BoundBinaryOperatorKind kind, Class<?> type) {
        this(syntaxKind, kind, type, type, type);
    }

    private static BoundBinaryOperator operators[] = {
            new BoundBinaryOperator(SyntaxKind.PlusToken, BoundBinaryOperatorKind.Addition, Integer.class),
            new BoundBinaryOperator(SyntaxKind.MinusToken, BoundBinaryOperatorKind.Subtraction, Integer.class),
            new BoundBinaryOperator(SyntaxKind.StarToken, BoundBinaryOperatorKind.Multiplication, Integer.class),
            new BoundBinaryOperator(SyntaxKind.SlashToken, BoundBinaryOperatorKind.Division, Integer.class),
    };

    public static BoundBinaryOperator bind(SyntaxKind syntaxKind, Class<?> leftType, Class<?> rightType) {
        for (BoundBinaryOperator op : operators) {
            if (op.syntaxKind == syntaxKind && op.leftType == leftType && op.rightType == rightType)
                return op;
        }
        return null;
    }

    public SyntaxKind getSyntaxKind() {
        return syntaxKind;
    }

    public BoundBinaryOperatorKind getKind() {
        return kind;
    }

    public Class<?> getLeftType() {
        return leftType;
    }

    public Class<?> getRightType() {
        return rightType;
    }

    public Class<?> getType() {
        return type;
    }
}
