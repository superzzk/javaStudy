package zzk.study.java.core.basic.time.pre8;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class AddHoursToDate {

    public Date addHoursToJavaUtilDate(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }

    public Date addHoursToDateUsingInstant(Date date, int hours) {
        return Date.from(date.toInstant()
            .plus(Duration.ofHours(hours)));
    }

    public LocalDateTime addHoursToLocalDateTime(LocalDateTime localDateTime, int hours) {
        return localDateTime.plusHours(hours);
    }

    public LocalDateTime subtractHoursToLocalDateTime(LocalDateTime localDateTime, int hours) {
        return localDateTime.minusHours(hours);
    }

    public ZonedDateTime addHoursToZonedDateTime(ZonedDateTime zonedDateTime, int hours) {
        return zonedDateTime.plusHours(hours);
    }

    public ZonedDateTime subtractHoursToZonedDateTime(ZonedDateTime zonedDateTime, int hours) {
        return zonedDateTime.minusHours(hours);
    }

    public Instant addHoursToInstant(Instant instant, int hours) {
        return instant.plus(hours, ChronoUnit.HOURS);
    }

    public Instant subtractHoursToInstant(Instant instant, int hours) {
        return instant.minus(hours, ChronoUnit.HOURS);
    }


}
