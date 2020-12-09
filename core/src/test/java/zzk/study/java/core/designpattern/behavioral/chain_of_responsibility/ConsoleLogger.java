package zzk.study.java.core.designpattern.behavioral.chain_of_responsibility;

/**
 * @program: designpattern
 * @description:
 * @author: zhangzhongkun
 * @create: 2019-04-01 16:11
 **/
public class ConsoleLogger extends AbstractLogger {
    public ConsoleLogger(int level){
        this.level = level;
    }

    @Override
    protected void write(String message) {
        System.out.println("Standard Console::Logger: " + message);
    }
}
