package util.datetime;

import org.junit.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class InstantDemo {
	@Test
	public void test1(){
		Instant now     = Instant.now();

		Instant later   = now.plusSeconds(3);
		System.out.println(later);
		Instant earlier = now.minusSeconds(3);
		System.out.println(earlier);


	}

	@Test
	public void test_truncate(){
		Instant instant = Instant.parse("2014-12-03T10:15:30.00Z");
		System.out.println(instant);
		System.out.println(instant.truncatedTo(ChronoUnit.HOURS));
		System.out.println(instant.truncatedTo(ChronoUnit.DAYS));
	}

}
