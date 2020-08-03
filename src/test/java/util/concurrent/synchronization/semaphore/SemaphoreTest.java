package util.concurrent.synchronization.semaphore;

public class SemaphoreTest {
    public static void main(String args[]) {
        SemaphoreService service = new SemaphoreService();
        for (int i = 0; i < 10; i++) {
            MyThread t = new MyThread("lang/thread" + (i + 1), service);
            t.start();// 这里使用 t.run() 也可以运行，但是不是并发执行了
        }
    }
}