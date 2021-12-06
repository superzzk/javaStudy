package zzk.study.java.core.designpattern.behavioral.command.example3;

public class HeaterOffCommand implements ICommand{
    Heater heater;
    public HeaterOffCommand(Heater heater)
    {
        this.heater = heater;
    }
    public void Execute()
    {
        this.heater.HeaterOff();
    }
}
