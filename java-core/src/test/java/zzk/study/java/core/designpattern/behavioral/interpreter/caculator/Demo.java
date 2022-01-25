package zzk.study.java.core.designpattern.behavioral.interpreter.caculator;


import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class Demo {
    public static void main(String[] args) {

        Context context = new Context();
        TerminalExpression a = new TerminalExpression("a");
        TerminalExpression b = new TerminalExpression("b");
        TerminalExpression c = new TerminalExpression("c");
        context.add(a, 4);
        context.add(b, 8);
        context.add(c, 2);

        // (4+8)-2
        Assertions.assertEquals(10, new MinusOperation(new PlusOperation(a,b), c).interpreter(context));
    }

    /**
     * 自动构建语法树
     * */
    @Test
    public void terminalExpression2(){
        Context context = new Context();
        String str = "4+8-2+9+9-8";
        Expression build = Context.build(str);
        System.out.println(str + "=" + build.interpreter(context));
    }
}

