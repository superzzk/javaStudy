package com.zzk.study.library.easyrule;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;


@Rule(name = "multi action rule", description = "desc")
public class MultiActionRule {
    @Condition
    public boolean when(@Fact("fact") int num) {
        return num > 1;
    }

    @Action(order = 1)
    public void action1() {
        System.out.println("action 1");
    }

    @Action(order = 2)
    public void action2() {
        System.out.println("action 2");
    }
}
