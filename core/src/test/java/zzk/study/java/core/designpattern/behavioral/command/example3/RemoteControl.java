package zzk.study.java.core.designpattern.behavioral.command.example3;

public class RemoteControl {
    private ICommand[] onCommands;
    private ICommand[] offCommands;
    public RemoteControl()
    {
        ICommand noCommand=new NoCommand();
        onCommands = new ICommand[4];
        offCommands = new ICommand[4];
        for (int i = 0; i < 4; i++)
        {
            onCommands[i] = noCommand;
            offCommands[i] = noCommand;
        }
    }

    public void SetCommand(int slot, ICommand onCommand, ICommand offCommand)
    {
        onCommands[slot] = onCommand;
        offCommands[slot] = offCommand;
    }
    public void OnButtonWasPress(int slot)
    {
        onCommands[slot].Execute();
    }
    public void OffButtonWasPress(int slot)
    {
        offCommands[slot].Execute();
    }
}
