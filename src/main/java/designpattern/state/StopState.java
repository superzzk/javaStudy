package designpattern.state;


/**
 * @program: designpattern
 * @description:
 * @author: zhangzhongkun
 * @create: 2019-04-01 10:33
 **/
public class StopState implements State {
    public void doAction(Context context) {
        System.out.println("Player is in stop state");
        context.setState(this);
    }

    public String toString(){
        return "Stop State";
    }
}
