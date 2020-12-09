package zzk.study.java.core.designpattern.behavioral.command.example2;

/**
 * @program: javaStudy
 * @description:
 * @author: zhangzhongkun
 * @create: 2019-04-09 09:03
 **/
public class ConcreteCommandB implements ICommand {
    private Receiver receiver = null;

    public ConcreteCommandB(Receiver receiver) {
        this.receiver = receiver;
    }

    public void Execute() {
        this.receiver.DoB();
    }
}
