package zzk.study.java.core.basic.time.pre8.date;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class DateComparisonUtils {

    public static boolean isSameDayUsingLocalDate(Date date1, Date date2) {
        LocalDate localDate1 = date1.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
        LocalDate localDate2 = date2.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
        return localDate1.isEqual(localDate2);
    }

    public static boolean isSameDayUsingInstant(Date date1, Date date2) {
        Instant instant1 = date1.toInstant()
            .truncatedTo(ChronoUnit.DAYS);
        Instant instant2 = date2.toInstant()
            .truncatedTo(ChronoUnit.DAYS);
        return instant1.equals(instant2);
    }

    public static boolean isSameDayUsingSimpleDateFormat(Date date1, Date date2) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return fmt.format(date1)
            .equals(fmt.format(date2));
    }

    public static boolean isSameDayUsingCalendar(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
                && calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
    }



}
