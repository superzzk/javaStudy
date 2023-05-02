package zzk.study.java.core.basic.time.java8;

import org.junit.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class LocalDateTimeDemo {

    @Test
    public void demo1() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Current DateTime="+now);

        //Current Date using LocalDate and LocalTime
        now = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        System.out.println("Current DateTime="+now);

        //Creating LocalDateTime by providing input arguments
        LocalDateTime specificDate = LocalDateTime.of(2014, Month.JANUARY, 1, 10, 10, 30);
        System.out.println("Specific Date="+specificDate);

        //Current date in "Asia/Kolkata", you can get it from ZoneId javadoc
        LocalDateTime todayKolkata = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
        System.out.println("Current Date in IST="+todayKolkata);

        //Getting date from the base date i.e 01/01/1970
        LocalDateTime dateFromBase = LocalDateTime.ofEpochSecond(10000, 0, ZoneOffset.UTC);
        System.out.println("10000th second time from 01/01/1970= "+dateFromBase);
    }

    int getYear(LocalDateTime localDateTime) {
        return localDateTime.getYear();
    }

    int getMonth(LocalDateTime localDateTime) {
        return localDateTime.getMonthValue();
    }

    int getDay(LocalDateTime localDateTime) {
        return localDateTime.getDayOfMonth();
    }
}
