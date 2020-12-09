package zzk.study.java.core.designpattern.behavioral.observer;

/**
 * @program: javaStudy
 * @author: zhangzhongkun
 * @create: 2019-06-10 18:39
 **/
public abstract class Observer {
    protected Subject subject;
    public abstract void update();
}
