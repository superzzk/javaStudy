package zzk.study.java.core.designpattern.behavioral.command.example3;

public class LightOnCommand implements ICommand{
    Light light;
    public LightOnCommand(Light light)
    {
        this.light = light;
    }

    public void Execute()
    {
        light.LightOn();
    }
}
