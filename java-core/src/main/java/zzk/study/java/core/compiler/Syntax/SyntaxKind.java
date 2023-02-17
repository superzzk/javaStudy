package zzk.study.java.core.compiler.Syntax;

public enum SyntaxKind {
    // Tokens
    BadToken,
    EndOfFileToken,
    WhitespaceToken,
    NumberToken,
    PlusToken,
    MinusToken,
    StarToken,
    SlashToken,
    OpenParenthesisToken,
    CloseParenthesisToken,

    // Expressions
    LiteralExpression,
    UnaryExpression,
    BinaryExpression,
    ParenthesizedExpression, BangToken;

    public int getUnaryOperatorPrecedence() {
        switch (this) {
            case PlusToken:
            case MinusToken:
                return 3;

            default:
                return 0;
        }
    }

    public int getBinaryOperatorPrecedence() {
        switch (this) {
            case StarToken:
            case SlashToken:
                return 2;

            case PlusToken:
            case MinusToken:
                return 1;

            default:
                return 0;
        }
    }
}