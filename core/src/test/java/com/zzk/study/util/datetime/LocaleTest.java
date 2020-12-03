package com.zzk.study.util.datetime;

import org.checkerframework.dataflow.qual.TerminatesExecution;
import org.junit.Test;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class LocaleTest {

	/**
	 *  2种不同的Locale的创建方法
	 */
	@Test
	public void testDiffDateLocales() {
		// date为2013-09-19 14:22:30
		Date date = new Date(113, 8, 19, 14, 22, 30);

		// 创建“简体中文”的Locale
		Locale localeCN = Locale.SIMPLIFIED_CHINESE;
		// 创建“英文/美国”的Locale
		Locale localeUS = new Locale("en", "US");

		// 获取“简体中文”对应的date字符串
		String cn = DateFormat.getDateInstance(DateFormat.MEDIUM, localeCN).format(date);
		System.out.println("medium: " +cn);
		cn = DateFormat.getDateInstance(DateFormat.FULL, localeCN).format(date);
		System.out.println("full: " + cn);
		cn = DateFormat.getDateInstance(DateFormat.LONG, localeCN).format(date);
		System.out.println("long: " + cn);
		// 获取“英文/美国”对应的date字符串
		String us = DateFormat.getDateInstance(DateFormat.MEDIUM, localeUS).format(date);
		System.out.println(us);

		System.out.printf("cn=%s\nus=%s\n", cn, us);
	}

	/**
	 *  显示所有的Locales
	 */
	@Test
	public void testAllLocales() {
		Locale[] ls = Locale.getAvailableLocales();

		System.out.print("All Locales: ");
		for (Locale locale:ls) {
			System.out.printf(locale+", ");
		}
		System.out.println();
	}
}
