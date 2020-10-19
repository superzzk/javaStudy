package util.datetime;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

public class LocalDataDemo {

	@Test
	public void demo1(){
		// 获取当前日期
		LocalDate now = LocalDate.now();
		System.out.println(now);
		System.out.println(now.toString().replace("-",""));
		// 设置日期
		LocalDate now2 = LocalDate.of(2099, 2, 28);
		System.out.println(now2);
		// 解析日期，格式必须是yyyy-MM-dd
		LocalDate now3 = LocalDate.parse("2018-01-12");
		System.out.println(now3);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String formatRs = now.format(dtf);
		System.out.println(formatRs);
		// 取本月第一天
		LocalDate firstDay = now.with(TemporalAdjusters.firstDayOfMonth());
		LocalDate firstDay2 = now.withDayOfMonth(1);
		// 取本月第2天
		LocalDate secondDay = now.withDayOfMonth(2);
		LocalDate nextMonthDay = now.with(TemporalAdjusters.firstDayOfNextMonth());
		LocalDate nextYearDay = now.with(TemporalAdjusters.firstDayOfNextYear());
		// 明年的这一天
		LocalDate localDate = now.plusYears(1);
		// 当前日期加上往后推20天
		LocalDate plusDate = now.plus(20, ChronoUnit.DAYS);
		LocalDate plusYear = now.plus(10, ChronoUnit.YEARS);
		// 当前日期往前推10天
		LocalDate minusDay = now.minusDays(10);
		LocalDate minusYear = now.minus(10, ChronoUnit.YEARS);

		// 比较日期大小
		boolean b1 = now.equals(LocalDate.of(2018, 04, 27));
		boolean b2= now.equals(LocalDate.of(2018, 04, 26));
		// 判断日期前后  -> false
		boolean b3 = now.isAfter(LocalDate.of(2018, 04, 26));//false
		boolean b4 = now.isAfter(LocalDate.of(2018, 04, 25));//true
		boolean b5 = now.isBefore(LocalDate.of(2018, 04, 26));//false
		boolean b6 = now.isBefore(LocalDate.of(2018, 04, 25));//false
		boolean b7 = now.isBefore(LocalDate.of(2018, 04, 27));//true
		// 计算两个日期之间的时间间隔   格式为：x年x月x天
		Period between = Period.between(now, LocalDate.of(2018, 05, 28));
		long bwDays = ChronoUnit.DAYS.between(now, LocalDate.of(2018, 05, 28));
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
	public void current_date_plus_one_day(){
		Date date = new Date();
		LocalDate today = date2LocalDate(date);
		LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);

		System.out.println(localDate2Date(today));
		System.out.println(localDate2Date(tomorrow));
	}

	@Test
	public void current_date_plus_one_month(){
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
	 * @param date
	 */
	public static LocalDateTime date2LocalDateTime(Date date){
		Instant instant = date.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();
		LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
		return localDateTime;
	}

	/**
	 * LocalDateTime转换为Date
	 * @param localDateTime
	 */
	public static Date localDateTime2Date( LocalDateTime localDateTime){
		ZoneId zoneId = ZoneId.systemDefault();
		ZonedDateTime zdt = localDateTime.atZone(zoneId);
		Date date = Date.from(zdt.toInstant());
		return date;
	}

}
