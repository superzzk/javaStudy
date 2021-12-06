package zzk.study.java.core.designpattern.statemachine.demo2;

/**
 * 定义电梯的状态：打开、关闭、运行、停止
 */
interface LiftState {
    void open();

    void close();

    void run();

    void stop();
}