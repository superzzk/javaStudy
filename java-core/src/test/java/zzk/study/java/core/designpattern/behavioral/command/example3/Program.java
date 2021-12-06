package zzk.study.java.core.designpattern.behavioral.command.example3;

/**
 * @program: javaStudy
 * @description:
 * @author: zhangzhongkun
 * @create: 2019-04-09 09:24
 **/
public class Program {
    public static void main(String[] args)
    {
        //家中的电器
        Fan fan=new Fan();
        Light light=new Light();
        Heater heater=new Heater();

        //电器分别对应的命令
        FanOffCommand fanOffCommand=new FanOffCommand(fan);
        FanOnCommand fanOnCommand=new FanOnCommand(fan);
        LightOnCommand lightOnCommand=new LightOnCommand(light);
        LightOffCommand lightOffCommand=new LightOffCommand(light);
        HeaterOnCommand heaterOnCommand=new HeaterOnCommand(heater);
        HeaterOffCommand heaterOffCommand=new HeaterOffCommand(heater);
        RemoteControl remoteControl = new RemoteControl();

        //设置遥控器
        remoteControl.SetCommand(0, fanOnCommand, fanOffCommand);
        remoteControl.SetCommand(1, lightOnCommand, lightOffCommand);
        remoteControl.SetCommand(2, heaterOnCommand, heaterOffCommand);
        //分别测试遥控器的命令
        remoteControl.OnButtonWasPress(1);
        remoteControl.OffButtonWasPress(1);
        remoteControl.OnButtonWasPress(0);
        remoteControl.OffButtonWasPress(0);

    }
}
