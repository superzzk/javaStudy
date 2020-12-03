package com.zzk.study.util.datetime;

import org.junit.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class InstantDemo {
	@Test
	public void test1(){
		Instant now     = Instant.now();
		System.out.println(now);
		System.out.println(now.truncatedTo(ChronoUnit.MINUTES));
		System.out.println(now.truncatedTo(ChronoUnit.SECONDS));
		System.out.println(formatDateStr(now));

		Instant later   = now.plusSeconds(3);
		System.out.println(later);
		Instant earlier = now.minusSeconds(3);
		System.out.println(earlier);


	}

	private String formatDateStr(Instant instant){
		String str = instant.truncatedTo(ChronoUnit.SECONDS).toString();
		str = str.replace("-", "").replace(":", "");
		str = str.substring(0, str.length() - 1);
		return str;
	}

	@Test
	public void test_truncate(){
		Instant instant = Instant.parse("2014-12-03T10:15:30.00Z");
		System.out.println(instant);
		System.out.println(instant.truncatedTo(ChronoUnit.HOURS));
		System.out.println(instant.truncatedTo(ChronoUnit.DAYS));
	}

}
