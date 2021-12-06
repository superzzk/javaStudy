package zzk.study.java.core.designpattern.behavioral.command.example3;

public class HeaterOnCommand implements ICommand{
    Heater heater;
    public HeaterOnCommand(Heater heater)
    {
        this.heater = heater;
    }
    public void Execute()
    {
        this.heater.HeaterOn();
    }
}
