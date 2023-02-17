package zzk.study.java.core.compiler;

import org.junit.Test;
import zzk.study.java.core.compiler.Syntax.SyntaxNode;
import zzk.study.java.core.compiler.Syntax.SyntaxTree;

/**
 * TODO:
 *
 * @author zhangzhongkun02
 * @date 2022/5/9 9:59 PM
 */
public class SyntaxTreeTest {
    @Test
    public void test() {
        SyntaxTree syntaxTree = SyntaxTree.parse("1+2+3");
        SyntaxNode.prettyPrint(syntaxTree.getRoot());
    }
    @Test
    public void test_unary_expression() {
        SyntaxTree syntaxTree = SyntaxTree.parse(" -1 + 2");
        SyntaxNode.prettyPrint(syntaxTree.getRoot());
    }
    @Test
    public void test_plus_and_star_token_precedence() {
        SyntaxTree syntaxTree = SyntaxTree.parse("1 + 2 * 3 + 4");
        SyntaxNode.prettyPrint(syntaxTree.getRoot());
    }
    @Test
    public void test_parenthesis_precedence() {
        SyntaxTree syntaxTree = SyntaxTree.parse(" 2 * ( 3 + 1 )");
        SyntaxNode.prettyPrint(syntaxTree.getRoot());
    }
}
