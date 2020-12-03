package com.zzk.study.util.datetime;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateDemo {

	@Test
	public void testParseStr(){
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");

		String input = "2050-12-30 00:00:00";
		System.out.print(input + " Parses as ");
		try {
			Date t = ft.parse(input);
			System.out.println(t);
		} catch (ParseException e) {
			System.out.println("Unparseable using " + ft);
		}
	}

	@Test
	public void testPrintf(){
		Date date=new Date();
		//b的使用，月份简称
		String str=String.format(Locale.US,"英文月份简称：%tb",date);
		System.out.println(str);
		System.out.printf("本地月份简称：%tb%n",date);
		//B的使用，月份全称
		str=String.format(Locale.US,"英文月份全称：%tB",date);
		System.out.println(str);
		System.out.printf("本地月份全称：%tB%n",date);
		//a的使用，星期简称
		str=String.format(Locale.US,"英文星期的简称：%ta",date);
		System.out.println(str);
		//A的使用，星期全称
		System.out.printf("本地星期的简称：%tA%n",date);
		//C的使用，年前两位
		System.out.printf("年的前两位数字（不足两位前面补0）：%tC%n",date);
		//y的使用，年后两位
		System.out.printf("年的后两位数字（不足两位前面补0）：%ty%n",date);
		//j的使用，一年的天数
		System.out.printf("一年中的天数（即年的第几天）：%tj%n",date);
		//m的使用，月份
		System.out.printf("两位数字的月份（不足两位前面补0）：%tm%n",date);
		//d的使用，日（二位，不够补零）
		System.out.printf("两位数字的日（不足两位前面补0）：%td%n",date);
		//e的使用，日（一位不补零）
		System.out.printf("月份的日（前面不补0）：%te",date);
	}

	@Test
	public void timeDifferenceBySimpleDateFormat() throws ParseException {
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		/*天数差*/
		Date fromDate1 = simpleFormat.parse("2018-03-01 12:00");
		Date toDate1 = simpleFormat.parse("2018-03-12 12:00");
		long from1 = fromDate1.getTime();
		long to1 = toDate1.getTime();
		int days = (int) ((to1 - from1) / (1000 * 60 * 60 * 24));
		System.out.println("两个时间之间的天数差为：" + days);

		/*小时差*/
		Date fromDate2 = simpleFormat.parse("2018-03-01 12:00");
		Date toDate2 = simpleFormat.parse("2018-03-12 12:00");
		long from2 = fromDate2.getTime();
		long to2 = toDate2.getTime();
		int hours = (int) ((to2 - from2) / (1000 * 60 * 60));
		System.out.println("两个时间之间的小时差为：" + hours);

		/*分钟差*/
		Date fromDate3 = simpleFormat.parse("2018-03-01 12:00");
		Date toDate3 = simpleFormat.parse("2018-03-12 12:00");
		long from3 = fromDate3.getTime();
		long to3 = toDate3.getTime();
		int minutes = (int) ((to3 - from3) / (1000 * 60));
		System.out.println("两个时间之间的分钟差为：" + minutes);
	}

}
