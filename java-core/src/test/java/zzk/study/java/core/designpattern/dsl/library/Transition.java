package zzk.study.java.core.designpattern.dsl.library;

import lombok.Data;


@Data
public class Transition {
    private final State source, target;
    private final Event trigger;

    public Transition(State source, Event trigger, State target) {
        this.source = source;
        this.target = target;
        this.trigger = trigger;
    }
}
