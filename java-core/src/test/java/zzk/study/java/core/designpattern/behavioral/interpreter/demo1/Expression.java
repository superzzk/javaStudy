package zzk.study.java.core.designpattern.behavioral.interpreter.demo1;

import java.util.List;

interface Expression {
    List<String> interpret(Context ctx);
}