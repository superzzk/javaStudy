package zzk.study.java.core.designpattern.factory.abstractFactory;

public class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("Inside Rectangle::draw() method.");
    }
}
