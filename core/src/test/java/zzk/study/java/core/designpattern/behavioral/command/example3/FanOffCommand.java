package zzk.study.java.core.designpattern.behavioral.command.example3;

public class FanOffCommand implements ICommand{
    private Fan fan;
    public FanOffCommand(Fan fan)
    {
        this.fan = fan;
    }

    @Override
    public void Execute()
    {
        this.fan.FanOff();
    }
}
