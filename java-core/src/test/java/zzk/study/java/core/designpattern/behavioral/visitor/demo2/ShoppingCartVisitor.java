package zzk.study.java.core.designpattern.behavioral.visitor.demo2;

public interface ShoppingCartVisitor {
	int visit(Book book);
	int visit(Fruit fruit);
}
