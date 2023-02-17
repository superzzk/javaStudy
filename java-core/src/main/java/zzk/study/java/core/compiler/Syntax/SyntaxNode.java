package zzk.study.java.core.compiler.Syntax;

public abstract class SyntaxNode {
    public abstract SyntaxKind getKind();
    public abstract Iterable<SyntaxNode> getChildren();


    public static void prettyPrint(SyntaxNode node) {
        prettyPrint(node, "");
    }

    public static void prettyPrint(SyntaxNode node, String indent){
        System.out.print(indent);
        System.out.print(node.getKind());

        if (node instanceof SyntaxToken && ((SyntaxToken) node).getValue() != null) {
            System.out.print(" ");
            System.out.print(((SyntaxToken) node).getValue());
        }
        System.out.println();
        indent += "    ";
        String finalIndent = indent;
        node.getChildren().forEach(n->prettyPrint(n, finalIndent));
    }
}
