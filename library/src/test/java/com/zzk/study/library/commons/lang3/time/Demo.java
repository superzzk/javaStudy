package com.zzk.study.library.commons.lang3.time;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.FastTimeZone;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Demo {
	@Test
	public void demo1() throws InterruptedException {
		for(int i=0; i<10; i++) {
			Thread.sleep(100);
			System.out.println(DateFormatUtils.format(new Date(), "YYYY-MM-dd HH:MM:ss:SSS"));
		}
	}

	@Test
	public void testFormat() {
		final Calendar c = Calendar.getInstance(FastTimeZone.getGmtTimeZone());
		c.set(2005, Calendar.JANUARY, 1, 12, 0, 0);
		c.setTimeZone(TimeZone.getDefault());
		final StringBuilder buffer = new StringBuilder ();
		final int year = c.get(Calendar.YEAR);
		final int month = c.get(Calendar.MONTH) + 1;
		final int day = c.get(Calendar.DAY_OF_MONTH);
		final int hour = c.get(Calendar.HOUR_OF_DAY);
		buffer.append (year);
		buffer.append(month);
		buffer.append(day);
		buffer.append(hour);
		System.out.println(buffer);

		String s1 = DateFormatUtils.format(c.getTime(), "yyyyMdH");
		System.out.println(s1);
		String s2 = DateFormatUtils.format(c.getTime(), "yyyyMMdH");
		System.out.println(s2);
		s2 = DateFormatUtils.format(c.getTime(), "yyyyMMddHH");
		System.out.println(s2);
		s2 = DateFormatUtils.format(c.getTime(), "yyyyMMddHHmmss");
		System.out.println(s2);
	}

}
