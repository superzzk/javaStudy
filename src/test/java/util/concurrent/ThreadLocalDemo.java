package util.concurrent;

/**
 * @program: javaStudy
 * @description:
 * @author: zhangzhongkun
 * @create: 2019-03-07 18:17
 **/

public class ThreadLocalDemo {
    // 创建ThreadLocal的变量

    // 覆盖初始化方法将初始化值定义为0
    private static ThreadLocal<Integer> seqNum = ThreadLocal.withInitial(() -> 0);
    // 下一个序列号
    public int getNextNum() {
        seqNum.set(seqNum.get() + 1);//往ThreadLocalMap中设置值
        return seqNum.get();
    }


    private class TestThread extends Thread {
        private ThreadLocalDemo sn;

        public TestThread(ThreadLocalDemo d) {
            sn = d;
        }
        // 线程产生序列号
        public void run() {
            for (int i = 0; i < 3; i++) {
                System.out.println("thread[" + Thread.currentThread().getName() + "] sn[" + sn.getNextNum() + "]");
            }
        }

    }

    public static void main(String[] args) {
        ThreadLocalDemo sn = new ThreadLocalDemo();
        // 三个线程产生各自的序列号
        TestThread t1 = new ThreadLocalDemo().new TestThread(sn);
        TestThread t2 = new ThreadLocalDemo().new TestThread(sn);
        TestThread t3 = new ThreadLocalDemo().new TestThread(sn);
        t1.start();
        t2.start();
        t3.start();
    }
}
