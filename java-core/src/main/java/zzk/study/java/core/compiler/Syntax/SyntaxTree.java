package zzk.study.java.core.compiler.Syntax;


import java.util.List;

public class SyntaxTree {
    private List<String> diagnostics;
    private SyntaxToken endOfFileToken;
    private ExpressionSyntax root;

    public List<String> getDiagnostics() {
        return diagnostics;
    }

    public ExpressionSyntax getRoot() {
        return root;
    }

    public SyntaxToken getEndOfFileToken() {
        return endOfFileToken;
    }

    private SyntaxToken syntaxToken;

    public SyntaxTree(List<String> diagnostics, ExpressionSyntax expressionSyntax, SyntaxToken endOfFileToken) {
        this.diagnostics = diagnostics;
        this.root = expressionSyntax;
        this.endOfFileToken = endOfFileToken;
    }

    public static SyntaxTree parse(String text) {
        Parser parser = new Parser(text);
        return parser.parse();
    }
}
