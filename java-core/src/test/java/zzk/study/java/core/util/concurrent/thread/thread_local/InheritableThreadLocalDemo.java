package zzk.study.java.core.util.concurrent.thread.thread_local;

import org.junit.Assert;
import org.junit.Test;

public class InheritableThreadLocalDemo {
    public static  final  InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();

    public static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    @Test
    public void test() throws Exception {
        inheritableThreadLocal.set("inheritableThreadLocal hello");
        threadLocal.set("threadLocal world");
        new Thread(()->{
            // child thread
            Assert.assertSame("inheritableThreadLocal hello", inheritableThreadLocal.get());
            Assert.assertNull(threadLocal.get());

            // grandson thread
            new Thread(()->{
                Assert.assertSame("inheritableThreadLocal hello", inheritableThreadLocal.get());
                Assert.assertNull(threadLocal.get());
            }).start();

        }).start();

        Thread.sleep(1000);
    }
}
