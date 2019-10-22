package thread;

class Example1 extends Thread{
    public void run(){
        System.out.println("My thread is in running state.");
    }
    public static void main(String args[]){
        Example1 obj=new Example1();
        obj.start();
    }
}