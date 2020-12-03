package com.zzk.study.util.concurrent.synchronization.cyclic_barrier;


import org.junit.Test;

import java.util.concurrent.*;

public class TravalDemo {
    @Test
    public void demo() throws Exception{

        CyclicBarrier cyclicBarrier = new CyclicBarrier(3,new TourGuideTask());
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        //哈登最大牌，到的最晚
        executorService.execute(new TravelTask(cyclicBarrier,"哈登",5));
        executorService.execute(new TravelTask(cyclicBarrier,"保罗",3));
        executorService.execute(new TravelTask(cyclicBarrier,"戈登",1));

        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
    }

    class TourGuideTask implements Runnable{

        @Override
        public void run() {
            System.out.println("****导游分发护照签证****");
            try {
                //模拟发护照签证需要2秒
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class TravelTask implements Runnable{

        private CyclicBarrier cyclicBarrier;
        private String name;
        private int arriveTime;//赶到的时间

        public TravelTask(CyclicBarrier cyclicBarrier, String name, int arriveTime){
            this.cyclicBarrier = cyclicBarrier;
            this.name = name;
            this.arriveTime = arriveTime;
        }

        @Override
        public void run() {
            try {
                //模拟达到需要花的时间
                Thread.sleep(arriveTime * 1000);
                System.out.println(name +"到达集合点");
                cyclicBarrier.await();
                System.out.println(name +"开始旅行啦～～");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
