package zzk.study.java.core.designpattern.builder;

public class Pepsi extends ColdDrink {
    @Override
    public float price() {
        return 35.0f;
    }

    public String name() {
        return "Pepsi";
    }
}
