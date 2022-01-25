package zzk.study.java.core.designpattern.behavioral.interpreter.caculator;

public class TerminalExpression2 implements Expression {

    String variable;

    public TerminalExpression2(String variable){
        this.variable = variable;
    }

    @Override
    public int interpreter(Context context) {
        //因为要兼容之前的版本
        Integer lookup = context.lookup(this);
        if (lookup == null)
            //若在map中能找到对应的数则返回
            return Integer.valueOf(variable);
        return lookup;
    }
}