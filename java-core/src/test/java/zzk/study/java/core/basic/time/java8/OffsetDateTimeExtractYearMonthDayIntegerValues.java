package zzk.study.java.core.basic.time.java8;

import java.time.OffsetDateTime;

public class OffsetDateTimeExtractYearMonthDayIntegerValues {

    int getYear(OffsetDateTime offsetDateTime) {
        return offsetDateTime.getYear();
    }

    int getMonth(OffsetDateTime offsetDateTime) {
        return offsetDateTime.getMonthValue();
    }

    int getDay(OffsetDateTime offsetDateTime) {
        return offsetDateTime.getDayOfMonth();
    }
}
