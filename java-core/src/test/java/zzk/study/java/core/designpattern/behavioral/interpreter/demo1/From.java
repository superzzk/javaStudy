package zzk.study.java.core.designpattern.behavioral.interpreter.demo1;

import java.util.List;

class From implements Expression {

    private String table;
    private Where where;

    From(String table) {
        this.table = table;
    }

    From(String table, Where where) {
        this.table = table;
        this.where = where;
    }

    @Override
    public List<String> interpret(Context ctx) {
        ctx.setTable(table);
        if (where == null) {
            return ctx.search();
        }
        return where.interpret(ctx);
    }
}