package zzk.study.java.core.designpattern.behavioral.interpreter.caculator;

public class TerminalExpression implements Expression {

    String variable;

    public TerminalExpression(String variable) {

        this.variable = variable;
    }

    @Override
    public int interpreter(Context context) {
        return context.lookup(this);
    }
}
