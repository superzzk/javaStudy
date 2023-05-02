package zzk.study.java.core.basic.time.java8;

import net.sf.cglib.core.Local;
import org.junit.Assert;
import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

public class LocalDataDemo {

	@Test
	public void demo1() {
		//Current Date
		LocalDate today = LocalDate.now();
		System.out.println("Current Date=" + today);

		//Creating LocalDate by providing input arguments
		LocalDate firstDay_2014 = LocalDate.of(2014, Month.JANUARY, 1);
		System.out.println("Specific Date=" + firstDay_2014);

		//Current date in "Asia/Kolkata", you can get it from ZoneId javadoc
		LocalDate todayKolkata = LocalDate.now(ZoneId.of("Asia/Kolkata"));
		System.out.println("Current Date in IST=" + todayKolkata);

		//Getting date from the base date i.e 01/01/1970
		LocalDate dateFromBase = LocalDate.ofEpochDay(365);
		System.out.println("365th day from base date= " + dateFromBase);

		LocalDate hundredDay2014 = LocalDate.ofYearDay(2014, 100);
		System.out.println("100th day of 2014=" + hundredDay2014);
	}
	/**
	 * 用long表示当前时间
	 * */
	public void currentTimeAsLong() {
		System.out.println("当前时间用long表示:" + OffsetDateTime.now().toEpochSecond());
		System.out.println("当前时间用long表示:" + LocalDateTime.now().toEpochSecond(OffsetDateTime.now().getOffset()));
		System.out.println("当前时间用long表示:" + (new Date()).toInstant().getEpochSecond());

		LocalDate today = LocalDate.now();
		long todayBeginTime = today.atStartOfDay().toEpochSecond(OffsetDateTime.now().getOffset());
		System.out.println("今日起始用long表示:" + todayBeginTime);
		long yesterdayBeginTime = today.minusDays(1).atStartOfDay().toEpochSecond(OffsetDateTime.now().getOffset());
		System.out.println("昨日起始用long表示:" + yesterdayBeginTime);

		Assert.assertEquals(86400L, todayBeginTime - yesterdayBeginTime);
	}


	@Test
	public void parse() {
		// 解析日期，格式必须是yyyy-MM-dd
		LocalDate today = LocalDate.parse("2018-01-12");
		System.out.println(today);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		System.out.println(today.format(dtf));
	}

	@Test
	public void dayOfMonth_dayOfYear() {
		final LocalDate today = LocalDate.now();
		// 取本月第一天
		today.with(TemporalAdjusters.firstDayOfMonth());
		today.withDayOfMonth(1);
		// 取本月第2天
		today.withDayOfMonth(2);
		// 下月第一天
		today.with(TemporalAdjusters.firstDayOfNextMonth());
		// 下年第一天
		today.with(TemporalAdjusters.firstDayOfNextYear());
	}

	@Test
	public void plusAndMinus() {
		final LocalDate today = LocalDate.now();
		// 明年的这一天
		LocalDate localDate = today.plusYears(1);
		// 当前日期加上往后推20天
		LocalDate plusDate = today.plus(20, ChronoUnit.DAYS);
		LocalDate plusYear = today.plus(10, ChronoUnit.YEARS);
		// 当前日期往前推10天
		LocalDate minusDay = today.minusDays(10);
		LocalDate minusYear = today.minus(10, ChronoUnit.YEARS);
	}

	@Test
	public void compare() {
		final LocalDate today = LocalDate.now();

		// 比较日期大小
		boolean b1 = today.equals(LocalDate.of(2018, 04, 27));
		// 判断日期前后  -> false
		boolean b3 = today.isAfter(LocalDate.of(2018, 04, 26));//false
		boolean b5 = today.isBefore(LocalDate.of(2018, 04, 26));//false
		// 计算两个日期之间的时间间隔   格式为：x年x月x天
		Period between = Period.between(today, LocalDate.of(2018, 05, 28));
		long bwDays = ChronoUnit.DAYS.between(today, LocalDate.of(2018, 05, 28));
	}

	@Test
	public void date_to_localDate() {
		Date date = new Date();
		Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();

        // atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();
        System.out.println("Date = " + date);
        System.out.println("LocalDate = " + localDate);
    }

    @Test
    public void localDate_to_date() {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = LocalDate.now();
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);

        Date date = Date.from(zdt.toInstant());

        System.out.println("LocalDate = " + localDate);
        System.out.println("Date = " + date);
    }

    @Test
    public void current_date_plus_one_day() {
        Date date = new Date();
        LocalDate today = date2LocalDate(date);
        LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);

        System.out.println(localDate2Date(today));
        System.out.println(localDate2Date(tomorrow));
    }

    @Test
    public void current_date_plus_one_month() {
        Date date = new Date();
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        System.out.println(zoneId);

        LocalDate localDate = instant.atZone(zoneId).toLocalDate();
        LocalDate afterOneMonth = localDate.plusMonths(1);

        System.out.println(localDate);
        System.out.println(localDate2Date(localDate));
        System.out.println(afterOneMonth);
        System.out.println(localDate2Date(afterOneMonth));
    }

    private static Date localDate2Date(LocalDate localDate) {
        if (null == localDate) {
            return null;
        }
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());

    }

    private static LocalDate date2LocalDate(Date date) {
        if (null == date) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Date转换为LocalDateTime
     *
     * @param date
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        return localDateTime;
    }

    /**
     * LocalDateTime转换为Date
     *
     * @param localDateTime
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        Date date = Date.from(zdt.toInstant());
        return date;
    }

}
