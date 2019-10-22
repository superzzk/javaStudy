package designpattern.factory.abstractFactory;


import designpattern.factory.simple_factory.Shape;

public abstract class AbstractFactory {
    public abstract Color getColor(String color);
    public abstract Shape getShape(String shape) ;

}
