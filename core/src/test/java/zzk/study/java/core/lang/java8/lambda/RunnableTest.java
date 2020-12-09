package zzk.study.java.core.lang.java8.lambda;

/**
 * @program: javaStudy
 * @author: zhangzhongkun
 * @create: 2019-04-26 20:40
 **/
public class RunnableTest {

    public static void main(String[] args) {
        repeat(10, () -> System.out.println("Hello, World!"));
        repeat2(10, i -> System.out.println("Countdown: " + (9 - i)));
    }

    public static void repeat(int n, Runnable action)
    {
        for (int i = 0; i < n; i++) action.run();
    }
    public static void repeat2(int n, IntConsumer action)
    {
        for (int i = 0; i < n; i++) action.accept(i);
    }

    interface IntConsumer
    {
        void accept(int value);
    }
}

