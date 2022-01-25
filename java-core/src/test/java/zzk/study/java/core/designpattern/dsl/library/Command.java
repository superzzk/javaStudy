package zzk.study.java.core.designpattern.dsl.library;

/**
 * TODO:
 *
 * @author zhangzhongkun02
 * @date 2022/1/14 10:48 AM
 */
public class Command extends AbstractEvent{

    public Command(String name, String code) {
        this.setName(name);
        this.setCode(code);
    }
}
