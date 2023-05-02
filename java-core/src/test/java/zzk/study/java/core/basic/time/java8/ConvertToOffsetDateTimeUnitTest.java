package zzk.study.java.core.basic.time.java8;

import org.junit.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConvertToOffsetDateTimeUnitTest {

    @Test
    public void whenDateIsNotNull_thenConvertToOffsetDateTime() {
        Date date = new Date();
        OffsetDateTime offsetDateTime = date.toInstant().atOffset(ZoneOffset.UTC);
    }

    @Test
    public void givenDate_whenHasOffset_thenConvertWithOffset() {
        TimeZone prevTimezone = TimeZone.getDefault();
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        Date date = new Date();
        date.setHours(6);
        date.setMinutes(30);

        OffsetDateTime odt = date.toInstant().atOffset(ZoneOffset.ofHoursMinutes(3, 30));

        assertEquals(10, odt.getHour());
        assertEquals(0, odt.getMinute());

        // Reset the timezone to its original value to prevent side effects
        TimeZone.setDefault(prevTimezone);
    }
}
