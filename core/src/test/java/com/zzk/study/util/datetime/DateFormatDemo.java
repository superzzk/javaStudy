package com.zzk.study.util.datetime;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateFormatDemo {

	/**
	 * Below is a list of the most common pattern letters you can use. For a full list, see the official JavaDoc for the SimpleDateFormat class.
	 *
	 * y   = year   (yy or yyyy)
	 * M   = month  (MM)
	 * d   = day in month (dd)
	 * h   = hour (0-12)  (hh)
	 * H   = hour (0-23)  (HH)
	 * m   = minute in hour (mm)
	 * s   = seconds (ss)
	 * S   = milliseconds (SSS)
	 * z   = time zone  text        (e.g. Pacific Standard Time...)
	 * Z   = time zone, time offset (e.g. -0800)
	 * Here are a few pattern examples, with examples of how each pattern would format or expect to parse a date:
	 *
	 * yyyy-MM-dd           (2009-12-31)
	 *
	 * dd-MM-YYYY           (31-12-2009)
	 *
	 * yyyy-MM-dd HH:mm:ss  (2009-12-31 23:59:59)
	 *
	 * HH:mm:ss.SSS         (23:59.59.999)
	 *
	 * yyyy-MM-dd HH:mm:ss.SSS   (2009-12-31 23:59:59.999)
	 *
	 * yyyy-MM-dd HH:mm:ss.SSS Z   (2009-12-31 23:59:59.999 +0100)
	 * */
	@Test
	public void demo1() throws ParseException {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		String dateString = format.format( new Date()   );
		System.out.println(dateString);
		Date date       = format.parse ( "2009-12-31" );
		System.out.println(date);
	}


	@Test
	public void demo2(){
		LocalDateTime now = LocalDateTime.now();

		System.out.println(DateTimeFormatter.BASIC_ISO_DATE.format(now));
		System.out.println(DateTimeFormatter.ISO_DATE_TIME.format(now));
		System.out.println(DateTimeFormatter.ISO_INSTANT.format(Instant.now()));
		System.out.println(DateTimeFormatter.ISO_LOCAL_TIME.format(now));
		System.out.println(DateTimeFormatter.ISO_ORDINAL_DATE.format(now));
	}

}
