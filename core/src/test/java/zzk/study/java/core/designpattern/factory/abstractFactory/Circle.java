package zzk.study.java.core.designpattern.factory.abstractFactory;

public class Circle implements Shape{
    @Override
    public void draw() {
        System.out.println("Inside Circle::draw() method.");
    }
}
