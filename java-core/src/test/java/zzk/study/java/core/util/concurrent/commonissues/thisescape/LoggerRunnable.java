package zzk.study.java.core.util.concurrent.commonissues.thisescape;

/**
 *  why we shouldn't start a thread inside a constructor.
 *  Every time we make an object available to any other code outside of its current scope, we basically publish that object.
 *  For instance, publishing happens when we return an object, store it into a public reference, or even pass it to another method.
 *
 * When we publish an object that we shouldn't have, we say that the object has escaped.
 *
 * There are many ways that we can let an object reference escape, such as publishing the object before its full construction.
 * As a matter of fact, this is one of the common forms of escape: when the this reference escapes during object construction.
 * other threads may see that object in an improper and not fully-constructed state. This, in turn, can cause weird thread-safety complications.
 *
 * */
public class LoggerRunnable implements Runnable {

    public LoggerRunnable() {
        Thread thread = new Thread(this); // this escapes
        thread.start();
    }

    @Override
    public void run() {
        System.out.println("Started...");
    }
}
