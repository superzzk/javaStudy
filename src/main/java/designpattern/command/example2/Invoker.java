package designpattern.command.example2;

public class Invoker {
    private ICommand command = null;

    //设置命令
    public void SetCommand(ICommand command) {
        this.command = command;
    }

    //执行命令
    public void RunCommand() {
        //可以进行命令的日志记录
        command.Execute();
    }
}
