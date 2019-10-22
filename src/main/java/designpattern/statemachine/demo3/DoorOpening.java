package designpattern.statemachine.demo3;

public class DoorOpening extends DoorState {
    public DoorOpening(Door2 door) {
        super(door);
    }

    public void touch() {
        door.setState(door.OPENING);
    }
}
