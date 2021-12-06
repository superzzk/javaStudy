package zzk.study.java.core.designpattern.factory.simple_factory;


public class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("Inside Rectangle::draw() method.");
    }
}
