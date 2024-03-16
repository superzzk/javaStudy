package zzk.study.java.core.util.regex;

import org.junit.Assert;
import org.junit.Test;

import javax.print.DocFlavor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonPattern {
	// user name
	private static Pattern usrNamePtrn = Pattern.compile("^[a-z0-9_-]{6,14}$");
	// email
	private static Pattern emailNamePtrn = Pattern.compile(
			"^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	// date
	private static Pattern dateFrmtPtrn =
			Pattern.compile("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)");
	// url
	private static Pattern urlPatter = Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");

	@Test
	public void test_name_pattern(){
		Assert.assertTrue(usrNamePtrn.matcher("java2novice").matches());
		Assert.assertFalse(usrNamePtrn.matcher("java2novice-too-long").matches());
		Assert.assertFalse(usrNamePtrn.matcher("cric").matches());
		Assert.assertFalse(usrNamePtrn.matcher("JAVA2NOVICE").matches());
		Assert.assertFalse(usrNamePtrn.matcher("java.2.novice").matches());
		Assert.assertTrue(usrNamePtrn.matcher("java_2-novice").matches());
	}

	/**
	 * matches会匹配整个字符串，如果只寻找字符串中是否存在某个regex，可使用find
	 */
	@Test
	public void find(){
		String content = "${aaa}";
		Pattern placeHolderPattern = Pattern.compile("\\$\\{[^}]+}");
		assertTrue(placeHolderPattern.matcher(content).find());
	}

	@Test
	public void test_email_pattern(){
		Assert.assertTrue(emailNamePtrn.matcher("java2novice@gmail.com").matches());
		Assert.assertFalse(emailNamePtrn.matcher("cric*7*&@yahoo.com").matches());
		Assert.assertFalse(emailNamePtrn.matcher("JAVA2NOVICE.gmail.com").matches());
	}

	@Test
	public void test_url_pattern(){
		Assert.assertTrue(urlPatter.matcher("http://localhost:99").matches());
	}

	@Test
	public void test_date_pattern(){
		System.out.println("Is '03/04/2012' a valid date format? "
				+dateFrmtPtrn.matcher("03/04/2012").matches());     //true
		System.out.println("Is '12/23/2012' a valid date format? "
				+dateFrmtPtrn.matcher("12/23/2012").matches());     //false
		System.out.println("Is '12/12/12' a valid date format? "
				+dateFrmtPtrn.matcher("12/12/12").matches());       //false
		System.out.println("Is '3/4/2012' a valid date format? "
				+dateFrmtPtrn.matcher("3/4/2012").matches());       //true
	}

	@Test
	public void commonPattern(){
		String blankLine = "^$";
		String cityState = ".*, [A-Z][A-Z]";        //City, State abbreviation.
		//Zip Code. Five digits, followed by an optional hyphen and four additional digits.
		String zipCode = "[0-9]{5}(-[0-9]{4})?";
		System.out.println(Pattern.matches(zipCode,"00000"));//true
		System.out.println(Pattern.matches(zipCode,"00000-9999"));//true
		//Social security number, such as:
		//###-##-####
		String socialSecurityNumber = "[0-9]{3}-[0-9]{2}-[0-9]{4}";
		//	Dollar amounts, specified with a leading $ symbol.
		String dollar = "\\$[0-9]*.[0-9][0-9]";
		//Date, in numeric format, such as 2003-08-06.
		String date = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
		//Date, in format such as "Jan 3, 2003"
		String date2 = "[A-Z][a-z][a-z] [0-9][0-9]*, [0-9]{4}";
	}

	/**
	 * Lookahead and Lookbehind Zero-Length Assertions
	 * */
	@Test
	public void wordMatch() {
		String[] words = {"hello", "Baeldung"};
		String inputString = "hello there, Baeldung";
		String wholeInput = "helloBaeldung";
		final boolean result = containsWordsPatternMatch(inputString, words);
		assertThat(result).isTrue();
	}

	public static boolean containsWordsPatternMatch(String inputString, String[] words) {

		StringBuilder regexp = new StringBuilder();
		for (String word : words) {
			regexp.append("(?=.*").append(word).append(")");
		}
		System.out.println(regexp.toString());
		Pattern pattern = Pattern.compile(regexp.toString());

		return pattern.matcher(inputString).find();
	}

}
