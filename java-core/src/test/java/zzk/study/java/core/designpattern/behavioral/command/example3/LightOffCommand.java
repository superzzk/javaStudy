package zzk.study.java.core.designpattern.behavioral.command.example3;

public class LightOffCommand implements ICommand{
    Light light;
    public LightOffCommand(Light light)
    {
        this.light = light;
    }
    public void Execute()
    {
        this.light.LightOff();
    }
}
