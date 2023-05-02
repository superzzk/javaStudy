package zzk.study.java.core.basic.time.pre8;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.logging.Logger;

public class DateIncrementer {
    private static final Logger log = Logger.getLogger(DateIncrementer.class.getName());
    private static final int INCREMENT_BY_IN_DAYS = 1;

    public static String addOneDay(String date) {
        return LocalDate
          .parse(date)
          .plusDays(INCREMENT_BY_IN_DAYS)
          .toString();
    }

    public static String addOneDayCalendar(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(date));
        c.add(Calendar.DATE, 1);
        return sdf.format(c.getTime());
    }
}
