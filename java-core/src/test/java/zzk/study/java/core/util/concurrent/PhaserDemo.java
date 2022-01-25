package zzk.study.java.core.util.concurrent;

import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * 假设小张，小李，小王，三个人约好共同去旅游，旅游路线是北京，上海，杭州，规则是他们都可以采用自己的路线去到达目的地，
 * 但是必须是所有人都到达某一个城市集合后，他们才能再次出发下一个城市。
 */
public class PhaserDemo {

    public static void main(String[] args) throws InterruptedException {
        Phaser phaser = new Phaser() {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println("=================step-" + phase + "===================" + registeredParties);
                return super.onAdvance(phase, registeredParties);
            }
        };

        Bus bus1 = new Bus(phaser, "小张");
        Bus bus2 = new Bus(phaser, "小李");
        Bus bus3 = new Bus(phaser, "小王");

        bus1.start();
        bus2.start();
        bus3.start();

        System.out.println(phaser.getRegisteredParties());
    }

    static public class Bus extends Thread {
        private Phaser phaser;
        private Random random;

        public Bus(Phaser phaser, String name) {
            this.phaser = phaser;
            setName(name);
            random = new Random();
            phaser.register();
        }

        @SneakyThrows
        @Override
        public void run() {
            try {
                int s = random.nextInt(3);
                TimeUnit.SECONDS.sleep(s);
                System.out.println(this.getName() + "  准备好了，旅行路线=北京=>上海=>杭州 ");
                phaser.arriveAndAwaitAdvance();// 等待所有的汽车准备好
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            trip(5, "北京");
            trip(5, "上海");
            trip(3, "杭州");
        }

        private void trip(int sleepRange, String cityName) throws InterruptedException {
            System.out.println(this.getName() + " 准备去" + cityName + "....");
            int sleep = random.nextInt(sleepRange);
            TimeUnit.SECONDS.sleep(sleep);

            System.out.println(this.getName() + " 达到" + cityName + "...... ");
            if (this.getName().equals("小王1")) { //  测试掉队的情况
                TimeUnit.SECONDS.sleep(7);
                phaser.arriveAndDeregister();
            } else {
                phaser.arriveAndAwaitAdvance();
            }
        }
    }
}