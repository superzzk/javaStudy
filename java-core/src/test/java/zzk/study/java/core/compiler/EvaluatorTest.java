package zzk.study.java.core.compiler;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import zzk.study.java.core.compiler.Binding.Binder;
import zzk.study.java.core.compiler.Binding.BoundExpression;
import zzk.study.java.core.compiler.Syntax.SyntaxNode;
import zzk.study.java.core.compiler.Syntax.SyntaxTree;

public class EvaluatorTest {
    @Test
    public void test() throws Exception {
        SyntaxTree syntaxTree = SyntaxTree.parse("1 + 2 * 3 + 4");
        SyntaxNode.prettyPrint(syntaxTree.getRoot());

        Binder binder = new Binder();
        final BoundExpression boundExpression = binder.bindExpression(syntaxTree.getRoot());

        Evaluator e = new Evaluator(boundExpression);
        final Object result = e.evaluate();
        Assertions.assertEquals(11, result);
    }
}
