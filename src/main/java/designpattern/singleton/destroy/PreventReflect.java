package designpattern.singleton.destroy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 通过反射的方式破坏单例模式，可以使用内部标记方式防止
 */
public class PreventReflect {
    private static boolean flag = false;

    private PreventReflect() {
        synchronized (PreventReflect.class) {
            System.out.println(" try to instance");
            if (flag == false) {
                System.out.println("first time instance");
                flag = !flag;
            } else {
                throw new RuntimeException("单例模式被侵犯！");
            }
        }
    }

    private static class SingletonHolder {
        // jvm保证在任何线程访问INSTANCE静态变量之前一定先创建了此实例
        private static final PreventReflect INSTANCE = new PreventReflect();
    }

    public static PreventReflect getInstance() {
        System.out.println("in getInstance");
        return SingletonHolder.INSTANCE;
    }

    public void doSomethingElse() {

    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

        Class<?> classType = PreventReflect.class;
        Constructor<?> c = classType.getDeclaredConstructor(null);
        c.setAccessible(true);
        PreventReflect e1 = (PreventReflect) c.newInstance();

        PreventReflect e2 = PreventReflect.getInstance();

        System.out.println(e1 == e2);
    }
}