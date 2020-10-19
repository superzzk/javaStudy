package util.datetime;

import org.junit.Test;

import java.time.LocalTime;

public class LocalTimeDemo {

	@Test
	public void demo1(){
		LocalTime time1 = LocalTime.now();
		System.out.println(time1);
	}
}
