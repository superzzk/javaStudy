package zzk.study.java.core.designpattern.factory.simple_factory;


public class Square implements Shape{
    @Override
    public void draw() {
        System.out.println("Inside Square::draw() method.");
    }
}
