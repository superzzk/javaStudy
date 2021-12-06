package zzk.study.java.core.util.concurrent.commonissues.thisescape;

/**
 * we're creating an anonymous inner class derived from the Thread.
 * Since inner classes maintain a reference to their enclosing class, the this reference again escapes from the constructor.
 * */
public class ImplicitEscape {

    public ImplicitEscape() {
        Thread t = new Thread() {

            @Override
            public void run() {
                System.out.println("Started...");
            }
        };

        t.start();
    }
}
