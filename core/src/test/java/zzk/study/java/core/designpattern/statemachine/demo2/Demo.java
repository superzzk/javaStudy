package zzk.study.java.core.designpattern.statemachine.demo2;


public class Demo {
    public static void main(String[] args) {
        Lift lift = new Lift();
        lift.setState(new OpenState(lift));
        lift.open();
        lift.close();
        lift.run();
        lift.stop();
    }
}
