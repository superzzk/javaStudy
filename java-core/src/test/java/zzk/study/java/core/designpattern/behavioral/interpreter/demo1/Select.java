package zzk.study.java.core.designpattern.behavioral.interpreter.demo1;

import java.util.List;

class Select implements Expression {

    private String column;
    private From from;

    Select(String column, From from) {
        this.column = column;
        this.from = from;
    }

    @Override
    public List<String> interpret(Context ctx) {
        ctx.setColumn(column);
        return from.interpret(ctx);
    }
}