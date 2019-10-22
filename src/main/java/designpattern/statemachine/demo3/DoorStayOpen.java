package designpattern.statemachine.demo3;

public class DoorStayOpen extends DoorState {
    public DoorStayOpen(Door2 door) {
        super(door);
    }

    public void touch() {
        door.setState(door.CLOSING);
    }
}