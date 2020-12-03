package com.zzk.study.lang.thread;

class Example1 extends Thread{
    public void run(){
        System.out.println("My thread is in running state.");
    }
    public static void main(String args[]){
        Example1 obj=new Example1();
        obj.start();
    }
}

class Example3 implements Runnable{
    public void run(){
        System.out.println("My thread is in running state.");
    }
    public static void main(String args[]){
        Example3 obj=new Example3();
        Thread tobj =new Thread(obj);
        tobj.start();
    }
}