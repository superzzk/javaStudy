package zzk.study.java.core.designpattern.statemachine.demo3;

public abstract class DoorState {
    protected Door2 door;

    public abstract void touch();

    public void complete() {
        if (door.status().equals("DoorOpening")) {
            door.setState(door.OPEN);
        }
        if (door.status().equals("DoorClosing")) {
            door.setState(door.CLOSED);
        }
    }

    public void timeout() {
        if (door.status().equals("DoorOpen")) {
            door.setState(door.CLOSING);
        }
    }

    public String status() {
        String s = getClass().getName();
        return s.substring(s.lastIndexOf('.') + 1);
    }

    public DoorState(Door2 door) {
        this.door = door;
    }
}