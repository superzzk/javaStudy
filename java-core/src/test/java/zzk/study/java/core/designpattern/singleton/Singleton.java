package zzk.study.java.core.designpattern.singleton;

/**
 *
 **/
public class Singleton {

}

/*
 * Eager initialization :
 * */
class Singleton1 {
    private static Singleton1 instance = new Singleton1(); //eagerness
    private Singleton1() {
        //init
    }

    public static Singleton1 getInstance() {
        return instance;
    }
}

/**
 *
 * Lazy initialization : (Consider this if init is resource heavy and if it is required in only some application flows)
 */
class Singleton2 {
    private static Singleton2 instance;
    private Singleton2() {
        //init
    }

    public static Singleton2 getInstance() {
        if(instance == null) { //laziness
            instance = new Singleton2();
        }
        return instance;
    }
}

/**
 * Thread safe Lazy initialization : (In the lazy init construct above,
 * if there are two or more threads accessing the getInstance method,
 * it might lead to the creation of spurious multiple instances of the singleton!)
 */
class Singleton3 {
    private static Singleton3 instance;
    private Singleton3() {
        //init
    }

    public static synchronized Singleton3 getInstance() { //thread safety
        if(instance == null) {
            instance = new Singleton3();
        }
        return instance;
    }
}

/**
* Double checked locking : (Note that in the above approach, the race condition can be reached only once
 * in the entire application lifecycle, ie when instance is null! What we are doing is introducing
 * a LOT of overhead wth the synchronized keyword.
 * So we need a way to ensure that the locking protection only applies once)
*/
class Singleton4 {
    private volatile static Singleton4 instance;
    private Singleton4() {
        //init
    }

    public static Singleton4 getInstance() {
        if(instance == null) {
            synchronized(instance) { //only lock the FIRST time
                if(instance == null) { //The "double" check
                    instance = new Singleton4();
                }
            }
        }
        return instance;
    }
}

/**
*
* Using an enum! : (The simplest way to define a singleton! And guess what!
 * Enums are lazily initialized by the JVM, ie they are instantiated the first time they are accessed!
 * The creation of an enum is thread safe too, the JVM ensures that! :D)
*/
enum Singleton5 {
    INSTANCE;
}
