package zzk.study.java.core.designpattern.behavioral.visitor.demo1;

public abstract class Element {

    public String uuid;

    public Element(String uuid) {
        this.uuid = uuid;
    }

    public abstract void accept(Visitor v);
}