package basic.annotation;

import java.util.Date;

public class SuppressWarningTest {

    //@SuppressWarnings(value={"deprecation"})
    private static void doSomething(){
        Date date = new Date(113, 8, 26);
        System.out.println(date);
    }

    @SuppressWarnings(value={"deprecation"})
    private static void doSomething2(){
        Date date = new Date(113, 8, 26);
        System.out.println(date);
    }

    public static void main(String[] args) {
        doSomething();
        doSomething2();
    }
}