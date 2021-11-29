package com.zzk.study.commons.lang3.time;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtilsTest {
    public static String addOneDayApacheCommons(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date incrementedDate = DateUtils.addDays(sdf.parse(date), 1);
        return sdf.format(incrementedDate);
    }

    public Date addHoursWithApacheCommons(Date date, int hours) {
        return DateUtils.addHours(date, hours);
    }

    public static boolean isSameDayUsingApacheCommons(Date date1, Date date2) {
        return DateUtils.isSameDay(date1, date2);
    }
}
