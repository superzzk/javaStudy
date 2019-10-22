package designpattern.observer_pattern;

/**
 * @program: javaStudy
 * @author: zhangzhongkun
 * @create: 2019-06-10 18:39
 **/
public abstract class Observer {
    protected Subject subject;
    public abstract void update();
}
