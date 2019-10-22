package designpattern.command.example3;

public class FanOnCommand implements ICommand{
    private Fan fan;
    public FanOnCommand(Fan fan)
    {
        this.fan = fan;
    }

    @Override
    public void Execute()
    {
        this.fan.FanOn();
    }
}
