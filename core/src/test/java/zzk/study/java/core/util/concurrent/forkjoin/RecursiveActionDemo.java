package zzk.study.java.core.util.concurrent.forkjoin;

import org.junit.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

public class RecursiveActionDemo {

    @Test
    public void test() throws InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        // 提交可分解的PrintTask任务
        forkJoinPool.submit(new Action(0, 1000));

        //阻塞当前线程直到 ForkJoinPool 中所有的任务都执行结束
        forkJoinPool.awaitTermination(2, TimeUnit.SECONDS);

        // 关闭线程池
        forkJoinPool.shutdown();
    }

    public static class Action extends RecursiveAction {
        /**
         * 每个"小任务"最多只打印20个数
         */
        private static final int MAX = 20;

        private int start;
        private int end;

        public Action(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            //当end-start的值小于MAX时，开始打印
            if ((end - start) < MAX) {
                for (int i = start; i < end; i++) {
                    System.out.println(Thread.currentThread().getName() + "i的值" + i);
                }
            } else {
                // 将大任务分解成两个小任务
                int middle = (start + end) / 2;
                Action left = new Action(start, middle);
                Action right = new Action(middle, end);
                left.fork();
                right.fork();
            }
        }
    }
}
