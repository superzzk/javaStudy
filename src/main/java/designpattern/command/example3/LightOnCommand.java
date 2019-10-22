package designpattern.command.example3;

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
