package designpattern.statemachine.demo3;

public class DoorOpen extends DoorState {
    public DoorOpen(Door2 door) {
        super(door);
    }

    public void touch() {
        door.setState(door.OPEN);
    }
}
