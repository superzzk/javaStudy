package designpattern.behavioral.visitor.demo2;

public interface ItemElement {
	public int accept(ShoppingCartVisitor visitor);
}
