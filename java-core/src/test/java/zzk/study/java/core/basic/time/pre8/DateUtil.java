package zzk.study.java.core.basic.time.pre8;

import lombok.SneakyThrows;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private static SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

    @SneakyThrows
    public static Date parseDate(String dateAsString) {
        return simpleFormat.parse(dateAsString);
    }

    public static Date getDate(long millis) {
        return new Date(millis);
    }

    public static Date getDate(String dateAsString, String pattern) throws ParseException {
        return new SimpleDateFormat(pattern).parse(dateAsString);
    }

    @Test
    void getFragmentInHours(){
        System.out.println(DateUtils.getFragmentInHours(parseDate("2023-10-27 17:34:42"), Calendar.DATE));
        System.out.println(DateUtils.getFragmentInHours(parseDate("2023-10-27 17:34:42"), Calendar.MONTH));
        System.out.println(DateUtils.getFragmentInHours(parseDate("2023-10-28 17:34:42"), Calendar.DAY_OF_MONTH));
        System.out.println(DateUtils.getFragmentInHours(new Date(), Calendar.DAY_OF_MONTH));
    }
}