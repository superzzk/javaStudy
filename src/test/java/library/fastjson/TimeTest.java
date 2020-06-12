package library.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastTimeZone;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeTest {

	@Test
	public void dateFormatTest(){
		TestData d = new TestData("test",new Date());
		String s1 = JSON.toJSONString(d, SerializerFeature.UseISO8601DateFormat);
		System.out.println(s1);

		String s2 = JSON.toJSONString(d, SerializerFeature.WriteDateUseDateFormat);
		System.out.println(s2);
	}



	@Test
	public void demo1() throws InterruptedException {
		TestData d = new TestData("test",new Date());
		String s = JSON.toJSONString(d);
		System.out.println(s);
		for(int i=0; i<10; i++) {
			Thread.sleep(100);
			System.out.println(DateFormatUtils.format(new Date(), "YYYY-MM-dd HH:MM:ss:SSS"));
		}
	}
	@Test
	public void demo2() throws InterruptedException {
		Date now = new Date();
		TestJson jsonObj = new TestJson("value",DateFormatUtils.format(now, "YYYY-MM-dd HH:MM:ss:SSS"));

		TestData d = new TestData("test",new Date());
		String jsonStr = JSON.toJSONString(d, SerializerFeature.UseISO8601DateFormat);
		System.out.println(jsonStr);
		TestData data = JSON.parseObject(jsonStr , TestData.class);

		System.out.println(data.toString());
	}


	public static class TestData {
		String value;
		Date time;

		public TestData(String value, Date time) {
			this.value = value;
			this.time = time;
		}
	}

	public static class TestJson {
		String value;
		String time;

		public TestJson(String value, String time) {
			this.value = value;
			this.time = time;
		}
	}
}
