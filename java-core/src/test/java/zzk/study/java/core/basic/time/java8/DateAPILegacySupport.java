package zzk.study.java.core.basic.time.java8;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateAPILegacySupport {

    @Test
    public void fromLegacy() {
        //Date to Instant
        Instant timestamp = new Date().toInstant();
        //Now we can convert Instant to LocalDateTime or other similar classes
        LocalDateTime date = LocalDateTime.ofInstant(timestamp, ZoneId.of(ZoneId.SHORT_IDS.get("PST")));
        System.out.println("Date = " + date);

        //Calendar to Instant
        Instant time = Calendar.getInstance().toInstant();
        System.out.println(time);
        //TimeZone to ZoneId
        ZoneId defaultZone = TimeZone.getDefault().toZoneId();
        System.out.println(defaultZone);

    }
    @Test
    public void toLegacy() {
        Date dt = Date.from(Instant.now());
        System.out.println(dt);

        ZoneId defaultZone = TimeZone.getDefault().toZoneId();
        TimeZone tz = TimeZone.getTimeZone(defaultZone);
        System.out.println(tz);

        //ZonedDateTime from specific Calendar
        ZonedDateTime gregorianCalendarDateTime = new GregorianCalendar().toZonedDateTime();
        System.out.println(gregorianCalendarDateTime);

        GregorianCalendar gc = GregorianCalendar.from(gregorianCalendarDateTime);
        System.out.println(gc);
    }
}