package zzk.study.java.core.compiler.Syntax;

import java.util.Collections;

public class SyntaxToken extends SyntaxNode{
    private SyntaxKind kind;
    private int position;
    private String text;
    private Object value;



    public SyntaxToken(SyntaxKind kind, int position, String text, Object content) {
        this.kind = kind;
        this.position = position;
        this.text = text;
        this.value = content;
    }

    public SyntaxKind getKind() {
        return kind;
    }

    @Override
    public Iterable<SyntaxNode> getChildren() {
        return Collections.<SyntaxNode>emptyList();
    }

    public void setKind(SyntaxKind kind) {
        this.kind = kind;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SyntaxToken{" +
                "kind=" + kind +
                ", position=" + position +
                ", text='" + text + '\'' +
                ", content=" + value +
                '}';
    }
}
