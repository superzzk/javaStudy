package zzk.study.java.core.util.regex;

import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegexBasic {
	@Test
	public void basic() {
		assertEquals(3, runTest(".", "foo"));
		assertEquals(1, runTest("foo.", "foofoo"));
		assertEquals(1, runTest("[abc]", "b"));
		assertEquals(3, runTest("[abc]", "cab"));
		assertEquals(3, runTest("[bcr]at", "bat cat rat"));
		assertEquals(1, runTest("[^abc]", "g"));
		assertEquals(3, runTest("[^bcr]at", "sat mat eat"));
		assertEquals(2, runTest("[A-Z]", "Two Uppercase alphabets 34 overall"));
		assertEquals(26, runTest("[a-z]", "Two Uppercase alphabets 34 overall"));
		assertEquals(28, runTest("[a-zA-Z]", "Two Uppercase alphabets 34 overall"));
		assertEquals(1, runTest( "3[0-5]", "Two Uppercase alphabets 34 overall"));
		//union 1-3 or 7-9
		assertEquals(6, runTest( "[1-3[7-9]]", "123456789"));
		// intersection 1-6 && 3-9  = 3-6
		assertEquals(4, runTest( "[1-6&&[3-9]]", "123456789"));
		// subtraction
		assertEquals(5, runTest( "[0-9&&[^2468]]", "123456789"));
	}

	@Test
	public void predefinedCharacter() {
		assertEquals(3, runTest( "\\d", "123"));
		assertEquals(2, runTest( "\\D", "a6c"));
		assertEquals(1, runTest( "\\s", "a c"));
		assertEquals(2, runTest( "\\S", "a c"));
		// Matching a word character, equivalent to [a-zA-Z_0-9]
		assertEquals(2, runTest( "\\w", "hi!"));
		assertEquals(1, runTest( "\\W", "hi!"));
	}

	@Test
	public void quantifiers() {
		//match a text zero or one time
		assertEquals(3, runTest( "\\a?", "hi"));
		assertEquals(3, runTest( "\\a{0,1}", "hi"));

		assertEquals(3, runTest( "\\a*", "hi"));
		assertEquals(3, runTest( "\\a{0,}", "hi"));

		assertEquals(0, runTest( "\\a+", "hi"));
		assertEquals(0, runTest( "\\a{1,}", "hi"));

		assertEquals(2, runTest( "a{3}", "aaaaaa"));
		//When we use a range in the brace, the match will be greedy
		assertEquals(1, runTest( "a{2,3}", "aaaa"));
		assertEquals(2, runTest( "a{2,3}?", "aaaa"));
	}

	@Test
	public void captureGroups() {
		assertEquals(1, runTest( "\\d\\d", "12"));
		assertEquals(1, runTest( "(\\d\\d)", "12"));
		assertEquals(2, runTest( "(\\d\\d)", "1212"));
		assertEquals(1, runTest( "(\\d\\d)\\1", "1212"));
		assertEquals(1, runTest( "(\\d\\d)\\1\\1\\1", "12121212"));
	}

	@Test
	public void boundaryMatch() {
		assertEquals(1, runTest( "^dog", "dogs are friendly"));
		assertEquals(0, runTest( "^dog", "are dogs are friendly"));
		assertEquals(1, runTest( "dog$", "Man's best friend is a dog"));
		//word boundary
		assertEquals(1, runTest( "\\bdog\\b", "a dog is friendly"));
		assertEquals(1, runTest( "\\bdog\\B", "snoop dogg is a rapper"));

	}

	@Test
	public void patternFlag_CANON_EQ() {
		/*
		* Consider the accented Unicode character é. Its composite code point is u00E9.
		* However, Unicode also has a separate code point for its component characters e, u0065, and the acute accent, u0301.
		* In this case, composite character u00E9 is indistinguishable from the two character sequence u0065 u0301.
		 * */
		//By default, matching doesn't take canonical equivalence into account
		assertEquals(0, runTest( "\u00E9", "\u0065\u0301"));
		assertEquals(1, runTest("\u00E9", "\u0065\u0301", Pattern.CANON_EQ));
	}

	@Test
	public void patternFlag_CASE_INSENSITIVE() {
		assertEquals(0, runTest( "dog", "This is a Dog"));
		assertEquals(1, runTest( "dog", "This is a Dog",Pattern.CASE_INSENSITIVE));
		// equivalent
		assertEquals(1, runTest( "(?i)dog", "This is a Dog"));

	}
	@Test
	public void test1(){
		System.out.println(Pattern.matches(".s", "as"));//true (2nd char is s)
		System.out.println(Pattern.matches(".s", "mk"));//false (2nd char is not s)
		System.out.println(Pattern.matches(".s", "mst"));//false (has more than 2 char)
		System.out.println(Pattern.matches(".s", "amms"));//false (has more than 2 char)
		System.out.println(Pattern.matches("..s", "mas"));//true (3rd char is s)


		System.out.println(Pattern.matches("[amn]", "abcd"));//false (not a or m or n)
		System.out.println(Pattern.matches("[amn]", "a"));//true (among a or m or n)
		System.out.println(Pattern.matches("[amn]", "ammmna"));//false (m and a comes more than once)


		System.out.println("? quantifier ....");
		System.out.println(Pattern.matches("[amn]?", "a"));//true (a or m or n comes one time)
		System.out.println(Pattern.matches("[amn]?", "aaa"));//false (a comes more than one time)
		System.out.println(Pattern.matches("[amn]?", "aammmnn"));//false (a m and n comes more than one time)
		System.out.println(Pattern.matches("[amn]?", "aazzta"));//false (a comes more than one time)
		System.out.println(Pattern.matches("[amn]?", "am"));//false (a or m or n must come one time)

		System.out.println("+ quantifier ....");
		System.out.println(Pattern.matches("[amn]+", "a"));//true (a or m or n once or more times)
		System.out.println(Pattern.matches("[amn]+", "aaa"));//true (a comes more than one time)
		System.out.println(Pattern.matches("[amn]+", "aammmnn"));//true (a or m or n comes more than once)
		System.out.println(Pattern.matches("[amn]+", "aazzta"));//false (z and t are not matching pattern)

		System.out.println("* quantifier ....");
		System.out.println(Pattern.matches("[amn]*", "ammmna"));//true (a or m or n may come zero or more times)


		System.out.println("metacharacters d....");     //d means digit

		System.out.println(Pattern.matches("\\d", "abc"));//false (non-digit)
		System.out.println(Pattern.matches("\\d", "1"));//true (digit and comes once)
		System.out.println(Pattern.matches("\\d", "4443"));//false (digit but comes more than once)
		System.out.println(Pattern.matches("\\d", "323abc"));//false (digit and char)

		System.out.println("metacharacters D....");     //D means non-digit

		System.out.println(Pattern.matches("\\D", "abc"));//false (non-digit but comes more than once)
		System.out.println(Pattern.matches("\\D", "1"));//false (digit)
		System.out.println(Pattern.matches("\\D", "4443"));//false (digit)
		System.out.println(Pattern.matches("\\D", "323abc"));//false (digit and char)
		System.out.println(Pattern.matches("\\D", "m"));//true (non-digit and comes once)

		System.out.println("metacharacters D with quantifier....");
		System.out.println(Pattern.matches("\\D*", "mak"));//true (non-digit and may come 0 or more times)

	}

	@Test
	public  void test2(){
		/*Create a regular expression that accepts alphanumeric characters only.
		Its length must be six characters long only.*/
		System.out.println(Pattern.matches("[a-zA-Z0-9]{6}", "arun32"));//true
		System.out.println(Pattern.matches("[a-zA-Z0-9]{6}", "kkvarun32"));//false (more than 6 char)
		System.out.println(Pattern.matches("[a-zA-Z0-9]{6}", "JA2Uk2"));//true
		System.out.println(Pattern.matches("[a-zA-Z0-9]{6}", "arun$2"));//false ($ is not matched)


		/*Create a regular expression that accepts 10 digit numeric characters
        starting with 7, 8 or 9 only.*/
		System.out.println("by character classes and quantifiers ...");
		System.out.println(Pattern.matches("[789]{1}[0-9]{9}", "9953038949"));//true
		System.out.println(Pattern.matches("[789][0-9]{9}", "9953038949"));//true

		System.out.println(Pattern.matches("[789][0-9]{9}", "99530389490"));//false (11 characters)
		System.out.println(Pattern.matches("[789][0-9]{9}", "6953038949"));//false (starts from 6)
		System.out.println(Pattern.matches("[789][0-9]{9}", "8853038949"));//true

		System.out.println("by metacharacters ...");
		System.out.println(Pattern.matches("[789]{1}\\d{9}", "8853038949"));//true
		System.out.println(Pattern.matches("[789]{1}\\d{9}", "3853038949"));//false (starts from 3)
	}


	public static int runTest(String regex, String text) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		int matches = 0;
		while (matcher.find()) {
			matches++;
		}
		return matches;
	}

	public static int runTest(String regex, String text, int flags) {
		Pattern pattern = Pattern.compile(regex, flags);
		Matcher matcher = pattern.matcher(text);
		int matches = 0;
		while (matcher.find()){
			matches++;
		}
		return matches;
	}

}
