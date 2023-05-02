package zzk.study.java.core.basic.time.java8;

import java.time.LocalDate;

public class LocalDateExtractYearMonthDayIntegerValues {

    int getYear(LocalDate localDate) {
        return localDate.getYear();
    }

    int getMonth(LocalDate localDate) {
        return localDate.getMonthValue();
    }

    int getDay(LocalDate localDate) {
        return localDate.getDayOfMonth();
    }
}
