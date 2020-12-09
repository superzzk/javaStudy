package zzk.study.java.core.designpattern.statemachine.demo3;

public class Door2 {
    public final DoorState CLOSED = new DoorClosed(this);
    public final DoorState CLOSING = new DoorClosing(this);
    public final DoorState OPEN = new DoorOpen(this);
    public final DoorState OPENING = new DoorOpening(this);
    public final DoorState STAYOPEN = new DoorStayOpen(this);

    private DoorState state = CLOSED;

    public void touch() {
        state.touch();
    }

    public void complete() {
        state.complete();
    }

    public void timeout() {
        state.timeout();
    }

    public String status() {
        return state.status();
    }

    protected void setState(DoorState state) {
        this.state = state;
    }

    public static void main(String[] args){
        Door2 door=new Door2();

        //1. 初始状态
        System.out.println(door.status());

        //2. 转移到Opening状态
        door.touch();
        System.out.println(door.status());

        //3. 转移到Open状态
        door.complete();
        System.out.println(door.status());

        //4. 转移到Closing状态
        door.timeout();
        System.out.println(door.status());

        //5. 回到Closed状态
        door.complete();
        System.out.println(door.status());
    }
}