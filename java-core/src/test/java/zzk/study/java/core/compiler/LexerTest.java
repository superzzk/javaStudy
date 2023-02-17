package zzk.study.java.core.compiler;

import org.junit.Test;
import zzk.study.java.core.compiler.Syntax.Lexer;
import zzk.study.java.core.compiler.Syntax.SyntaxKind;
import zzk.study.java.core.compiler.Syntax.SyntaxToken;

/**
 * TODO:
 *
 * @author zhangzhongkun02
 * @date 2022/5/8 7:53 PM
 */
public class LexerTest {

    @Test
    public void test() {
        Lexer lexer = new Lexer("1+1");
        while (true) {
            final SyntaxToken token = lexer.lex();
            if (token.getKind().equals(SyntaxKind.EndOfFileToken))
                break;
            System.out.println(token);
        }
    }
}
