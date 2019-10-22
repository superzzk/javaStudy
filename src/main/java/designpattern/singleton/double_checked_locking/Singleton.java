package designpattern.singleton.double_checked_locking;

public class Singleton {
    // java中使用双重检查锁定机制,由于Java编译器和JIT的优化的原因系统无法保证我们期望的执行次序。
    // 在java5.0修改了内存模型,使用volatile声明的变量可以强制屏蔽编译器和JIT的优化工作
    private volatile static Singleton singleton;
    private Singleton (){}
    public static Singleton getSingleton() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
