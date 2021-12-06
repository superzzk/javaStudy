package zzk.study.java.core.designpattern.statemachine.demo3;

public class DoorClosing extends DoorState {
    public DoorClosing(Door2 door) {
        super(door);
    }

    public void touch() {
        door.setState(door.CLOSING);
    }
}
