package com.zzk.study.library.commons.lang3.time;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateParser;
import org.apache.commons.lang3.time.FastDateFormat;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

public class DateParserDemo {

	@Test
	public void parserDemo() throws ParseException {
		String str;

		final DateParser h = FastDateFormat.getInstance("yyyy-MM-dd hh:mm:ss");
		Date dt = h.parse("1900-10-10 33:22:44");
		System.out.println(dt);
		str  = DateFormatUtils.format(dt, "yyyyMMdd HHmmss:SSS");
		System.out.println(str);

		DateParser fdf = FastDateFormat.getInstance("yyyy MM dd HH mm ss SSS");
		dt = fdf.parse("1900 10 10 33 22 55 444");
		str  = DateFormatUtils.format(dt, "yyyyMMdd HHmmss:SSS");
		System.out.println(str);

	}


}
