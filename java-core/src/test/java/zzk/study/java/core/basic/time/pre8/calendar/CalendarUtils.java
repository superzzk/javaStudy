package zzk.study.java.core.basic.time.pre8.calendar;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtils {

    public static Calendar getPlusDays(Date date, int amount) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, amount);
        return calendar;
    }
}