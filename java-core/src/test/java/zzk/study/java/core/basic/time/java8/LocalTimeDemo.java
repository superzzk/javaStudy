package zzk.study.java.core.basic.time.java8;

import org.junit.Test;

import java.time.LocalTime;
import java.time.ZoneId;

public class LocalTimeDemo {

    @Test
    public void demo1() {
        //Current Time
        LocalTime time = LocalTime.now();
        System.out.println("Current Time=" + time);

        //Creating LocalTime by providing input arguments
        LocalTime specificTime = LocalTime.of(12, 20, 25, 40);
        System.out.println("Specific Time of Day=" + specificTime);

        //Current date in "Asia/Kolkata", you can get it from ZoneId javadoc
        LocalTime timeKolkata = LocalTime.now(ZoneId.of("Asia/Kolkata"));
        System.out.println("Current Time in IST=" + timeKolkata);

        LocalTime specificSecondTime = LocalTime.ofSecondOfDay(10000);
        System.out.println("10000th second time= " + specificSecondTime);
    }
}
