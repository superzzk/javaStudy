package zzk.study.java.core.basic.time.java8;

import java.time.ZonedDateTime;

public class ZonedDateTimeExtractYearMonthDayIntegerValues {

    int getYear(ZonedDateTime zonedDateTime) {
        return zonedDateTime.getYear();
    }

    int getMonth(ZonedDateTime zonedDateTime) {
        return zonedDateTime.getMonthValue();
    }

    int getDay(ZonedDateTime zonedDateTime) {
        return zonedDateTime.getDayOfMonth();
    }
}
