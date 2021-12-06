package zzk.study.java.core.designpattern.factory.abstractFactory;


public class Blue implements Color {
    @Override
    public void fill() {
        System.out.println("Inside Blue::fill() method.");
    }
}
