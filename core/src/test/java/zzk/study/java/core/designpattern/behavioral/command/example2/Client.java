package zzk.study.java.core.designpattern.behavioral.command.example2;


public class Client {
    public Client() {
        Receiver receiver = new Receiver();
        Invoker invoker = new Invoker();
        invoker.SetCommand(new ConcreteCommandA(receiver));
        invoker.RunCommand();
        invoker.SetCommand(new ConcreteCommandB(receiver));
        invoker.RunCommand();
    }
}
