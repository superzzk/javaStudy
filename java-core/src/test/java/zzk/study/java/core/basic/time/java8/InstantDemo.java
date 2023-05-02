package zzk.study.java.core.basic.time.java8;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class InstantDemo {
	@Test
	public void test1(){
		//Current timestamp
		Instant now = Instant.now();
		System.out.println("Current Timestamp = "+now);

		//Instant from timestamp
		Instant specificTime = Instant.ofEpochMilli(now.toEpochMilli());
		System.out.println("Specific Time = "+specificTime);

		System.out.println(now.truncatedTo(ChronoUnit.MINUTES));
		System.out.println(now.truncatedTo(ChronoUnit.SECONDS));
		System.out.println(formatDateStr(now));

		Instant later   = now.plusSeconds(3);
		System.out.println(later);
		Instant earlier = now.minusSeconds(3);
		System.out.println(earlier);

		//Duration example
		Duration thirtyDay = Duration.ofDays(30);
		System.out.println(thirtyDay);
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
