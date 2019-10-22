package thread;

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