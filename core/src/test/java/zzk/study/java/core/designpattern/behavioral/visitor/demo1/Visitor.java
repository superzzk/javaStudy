package zzk.study.java.core.designpattern.behavioral.visitor.demo1;

public interface Visitor {

    void visit(XmlElement xe);

    void visit(JsonElement je);
}
