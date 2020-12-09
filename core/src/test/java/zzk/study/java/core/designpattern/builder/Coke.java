package zzk.study.java.core.designpattern.builder;

public class Coke extends ColdDrink {
    @Override
    public float price() {
        return 30.0f;
    }

    public String name() {
        return "Coke";
    }
}
