package zzk.study.java.core.util.regex;

import org.checkerframework.common.value.qual.StaticallyExecutable;
import org.junit.Test;

import java.io.Console;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GroupDemo {
	public static final String EXAMPLE_TEST = "This is my small example "
			+ "string which I'm going to " + "use for pattern matching.";

	public static void main(String[] args) {
		String s1 = "test test , test";
		// Removes whitespace between a word character and . or ,
		String pattern = "(\\w)(\\s+)([\\.,])";
		System.out.println(s1.replaceAll(pattern, "$1$3"));



		//  extracts the text between a title tag.   没看懂
		pattern = "(?i)(<title.*?>)(.+?)()";
		String s2 = "<title>what is it</title>";
		String updated = s2.replaceAll(pattern, "$2");
		System.out.println(updated);
	}

	@Test
	public void demo_greedy_reluctant_possessive(){
		String temp = "xfooxxxxxxfoo";

		System.out.println("greedy:");
		Matcher matcher = Pattern.compile(".*foo").matcher(temp);
		printMatcher(matcher);

		System.out.println("reluctant:");
		matcher = Pattern.compile(".*?foo").matcher(temp);
		printMatcher(matcher);

		System.out.println("possessive:");
		/**
		 *  In possessive case, the entire input string is consumed by .*+,
		 *  leaving nothing left over to satisfy the "foo" at the end of the expression.
		 *  Use a possessive quantifier for situations where you want to seize all of something without ever backing off;
		 *  it will outperform the equivalent greedy quantifier in cases where the match is not immediately found.
		 * */
		matcher = Pattern.compile(".*+foo").matcher(temp);
		printMatcher(matcher);

	}

	@Test
	public void demo_back_reference() {
		Matcher matcher = Pattern.compile("(\\d\\d)\\1").matcher("1212");
		printMatcher(matcher);

		matcher = Pattern.compile("(\\d\\d)\\1").matcher("1234");
		printMatcher(matcher);
	}


	/**
	 * Negative look ahead
	 * Negative look ahead provides the possibility to exclude a pattern.
	 * With this you can say that a string should not be followed by another string.
	 *
	 * Negative look ahead are defined via (?!pattern). For example, the following will match "a" if "a" is not followed by "b".
	 *
	 * a(?!b)
	 * */
	@Test
	public void demo_lookahead(){
		/*
		 * lookahead after
		 * Explanation: \d+ matches the digits 100, then the lookahead (?= dollars) asserts that
		 * at that position in the string, what immediately follows is the characters " dollars"
		 * */
		Matcher matcher = Pattern.compile("\\d+(?= dollars)").matcher("100 dollars");
		printMatcher(matcher);

		/*
		 * lookahead before
		 * Explanation: The lookahead (?=\d+ dollars) asserts that at the current position in the string,
		 * what follows is digits then the characters " dollars". If the assertion succeeds, the engine matches the digits with \d+.
		 *
		 * Note that this pattern achieves the same result as \d+(?= dollars) from above,
		 * but it is less efficient because \d+ is matched twice.
		 * A better use of looking ahead before matching characters is to validate multiple conditions in a password.
		 * */
		matcher = Pattern.compile("(?=\\d+ dollars)\\d+").matcher("100 dollars");
		printMatcher(matcher);

		/*
		 * Negative Lookahead After
		 * Explanation: \d+ matches 100, then the negative lookahead (?!\d| dollars) asserts that
		 * at that position in the string, what immediately follows is neither a digit nor the characters " dollars"
		 * */
		matcher = Pattern.compile("\\d+(?!\\d| dollars)").matcher("100 pesos");
		printMatcher(matcher);

		/*
		 * Explanation: The negative lookahead (?!\d+ dollars) asserts that at the current position in the string,
		 * what follows is not digits then the characters " dollars". If the assertion succeeds, the engine matches the digits with \d+.
		 *
		 *  Note that this pattern achieves the same result as \d+(?!\d| dollars) from above,
		 * but it is less efficient because \d+ is matched twice.
		 * A better use of looking ahead before matching characters is to validate multiple conditions in a password.
		 * */
		matcher = Pattern.compile("(?!\\d+ dollars)\\d+").matcher("100 pesos");
		printMatcher(matcher);
	}

	@Test
	public void demo_lookbehind() {
		/*
		 * Lookbehind Before the match: (?<=USD)\d{3}
		 * Sample Match: 100 in USD100
		 * Explanation: The lookbehind (?<=USD) asserts that at the current position in the string,
		 * what precedes is the characters "USD". If the assertion succeeds, the engine matches three digits with \d{3}.
		 * */
		Matcher matcher = Pattern.compile("(?<=USD)\\d{3}").matcher("USD100");
		printMatcher(matcher);

		/*
		 * Lookbehind After the match: \d{3}(?<=USD\d{3})
		 * Sample Match: 100 in USD100
		 * Explanation: \d{3} matches 100, then the lookbehind (?<=USD\d{3}) asserts that at that position in the string,
		 * what immediately precedes is the characters "USD" then three digits.
		 *
		 *  Note that this pattern achieves the same result as (?<=USD)\d{3} from above,
		 *  but it is less efficient because \d{3} is matched twice.
		 * */
		matcher = Pattern.compile("\\d{3}(?<=USD\\d{3})").matcher("USD100");
		printMatcher(matcher);


		/*
		 * Negative Lookbehind Before the Match: (?<!USD)\d{3}
		 * Sample Match: 100 in JPY100
		 * Explanation: The negative lookbehind (?<!USD) asserts that at the current position in the string,
		 * what precedes is not the characters "USD". If the assertion succeeds, the engine matches three digits with \d{3}.
		 * */


		/*
		 * Negative Lookbehind After the Match: \d{3}(?<!USD\d{3})
		 * Explanation: \d{3} matches 100, then the negative lookbehind (?<!USD\d{3}) asserts that
		 * at that position in the string, what immediately precedes is not the characters "USD" then three digits.
		 *
		 * Note that this pattern achieves the same result as (?<!USD)\d{3} from above,
		 * but it is less efficient because \d{3} is matched twice.
		 * */
	}


	/*
	 * Specifying modes inside the regular expression
	 * You can add the mode modifiers to the start of the regex. To specify multiple modes, simply put them together as in (?ismx).
	 *
	 * (?i) makes the regex case insensitive.
	 *
	 * (?s) for "single line mode" makes the dot match all characters, including line breaks.
	 *
	 * (?m) for "multi-line mode" makes the caret and dollar match at the start and end of each line in the subject string
	 * */
	public static void test2(){

	}


	/**
	 * Finding duplicated words
	 * The following regular expression matches duplicated words.
	 *
	 * \b(\w+)\s+\1\b
	 * \b is a word boundary and \1 references to the captured match of the first group, i.e., the first word.
	 *
	 * The (?!-in)\b(\w+) \1\b finds duplicate words if they do not start with "-in".
	 *
	 * TIP:Add (?s) to search across multiple lines
	 * */
	public static void test3(){

	}

	/**
	 * https://www.rexegg.com/regex-lookarounds.html
	 * Our password must meet four conditions:
	 *
	 *  1. The password must have between six and ten word characters \w
	 *  2. It must include at least one lowercase character [a-z]
	 *  3. It must include at least three uppercase characters [A-Z]
	 *  4. It must include at least one digit \d
	 * */
	@Test
	public void lookahead_example_simple_password_validation() {
		/*
		 * condition 1
		 *  A string that is made of six-to-ten word characters can be written like this: \A\w{6,10}\z
		 *  The \A anchor asserts that the current position is the beginning of the string.
		 *  After matching the six to ten word characters, the \z anchor asserts that the current position is the end of the string.
		 *
		 *  Within a lookahead, this pattern becomes (?=\A\w{6,10}\z).
		 *  This lookahead asserts: at the current position in the string, what follows is the beginning of the string,
		 *  six to ten word characters, and the very end of the string.
		 *
		 *  We want to make this assertion at the very beginning of the string.
		 *  Therefore, to continue building our pattern, we want to anchor the lookahead with an \A.
		 *  There is no need to duplicate the \A, so we can take it out of the lookahead. Our pattern becomes:
		 *  \A(?=\w{6,10}\z)
		 *  So far, we have an expression that validates that a string is entirely composed of six to ten word characters.
		 *  Note that we haven't matched any of these characters yet: we have only looked ahead.
		 *  The current position after the lookahead is still the beginning of the string.
		 *  To check the other conditions, we just add lookaheads.
		 * */


		/*
		 * Condition 2
		 * For our second condition, we need to check that the password contains one lowercase letter.
		 * To find one lowercase letter, the simplest idea is to use .*[a-z].
		 * That works, but the dot-star first shoots down to the end of the string,
		 * so we will always need to backtrack. Just for the sport, can we think of something more efficient?
		 * You might think of making the star quantifier reluctant by adding a ?, giving us .*?[a-z],
		 * but that too requires backtracking as a lazy quantifier requires backtracking at each step.
		 *
		 * For this type of situation, I recommend you use something like [^a-z]*[a-z]
		 * (or even better, depending on your engine, the atomic (?>[^a-z]*)[a-z]
		 * or possessive version [^a-z]*+[a-z]—but we'll discuss that in the footnotes).
		 * The negated character class [^a-z] is the counterclass of the lowercase letter [a-z] we are looking for:
		 * it matches one character that is not a lowercase letter, and the * quantifier makes us match zero or more such characters.
		 * The pattern [^a-z]*[a-z] is a good example of the principle of contrast recommended by the regex style guide.
		 *
		 *  Let's use this pattern inside a lookahead: (?=[^a-z]*[a-z])
		 *  The lookahead asserts: at this position in the string (i.e., the beginning of the string),
		 *  we can match zero or more characters that are not lowercase letters, then we can match one lowercase letter: [a-z]
		 *  Our pattern becomes:
		 *  \A(?=\w{6,10}\z)(?=[^a-z]*[a-z])
		 *
		 *  At this stage, we have asserted that we are at the beginning of the string, and we have looked ahead twice.
		 *  We still haven't matched any characters. Note that on a logical level it doesn't matter which condition we check first.
		 *  If we swapped the order of the lookaheads, the result would be the same.
		 *
		 * */

		/*
		 * Condition 3
		 * For our third condition, we need to check that the password contains at least three uppercase letters.
		 * The logic is similar to condition 2: we look for an optional number of non-uppercase letters,
		 * then one uppercase letter… But we need to repeat that three times, for which we'll use the quantifier {3}.
		 * We'll use this lookahead: (?=(?:[^A-Z]*[A-Z]){3})
		 *
		 *  The lookahead asserts: at this position in the string (i.e., the beginning of the string),
		 *  we can do the following three times: match zero or more characters that are not uppercase letters
		 *  (the job of the negated character class [^A-Z] with the quantifier *), then match one uppercase letter: [A-Z]
		 *  Our pattern becomes:
		 *  \A(?=\w{6,10}\z)(?=[^a-z]*[a-z])(?=(?:[^A-Z]*[A-Z]){3})
		 * */


		/*
		 * Condition 4
		 *  To check that the string contains at least one digit, we use this lookahead: (?=\D*\d).
		 *  Opposing \d to its counterclass \D makes good use of the regex principle of contrast.
		 *
		 *  The lookahead asserts: at this position in the string (i.e., the beginning of the string),
		 *  we can match zero or more characters that are not digits (the job of the "not-a-digit" character class \D and the * quantifier),
		 *  then we can match one digit: \d
		 *  Our pattern becomes:
		 *  \A(?=\w{6,10}\z)(?=[^a-z]*[a-z])(?=(?:[^A-Z]*[A-Z]){3})(?=\D*\d)
		 *
		 *  At this stage, we have asserted that we are at the beginning of the string,
		 *  and we have looked ahead four times to check our four conditions. We still haven't matched any characters,
		 *  but we have validated our string: we know that it is a valid password.
		 *
		 *  If all we wanted was to validate the password, we could stop right there.
		 *  But if for any reason we also need to match and return the entire string —
		 *  perhaps because we ran the regex on the output of a function and
		 *  the password's characters haven't yet been assigned to a variable—we can easily do so now.
		 *
		 *  Matching the Validated String
		 *  After checking that the string conforms to all four conditions,
		 *  we are still standing at the beginning of the string.
		 *  The five assertions we have made (the anchor \A and the four lookaheads) have not changed our position.
		 *  At this stage, we can use a simple .* to gobble up the string: we know that whatever characters are matched by the dot-star,
		 *  the string is a valid password. The pattern becomes:
		 *  \A(?=\w{6,10}\z)(?=[^a-z]*[a-z])(?=(?:[^A-Z]*[A-Z]){3})(?=\D*\d).*
		 * */

		/*
		 * Fine-Tuning: Removing One Condition
		 * If you examine our lookaheads, you may notice that the pattern \w{6,10}\z inside the first one
		 * examines all the characters in the string. Therefore,
		 * we could have used this pattern to match the whole string instead of the dot-star .*
		 *
		 *  This allows us to remove one lookahead and to simplify the pattern to this:
		 *
		 *  \A(?=[^a-z]*[a-z])(?=(?:[^A-Z]*[A-Z]){3})(?=\D*\d)\w{6,10}\z
		 *  The pattern \w{6,10}\z now serves the double purpose of matching the whole string
		 *  and of ensuring that the string is entirely composed of six to ten word characters.
		 *
		 *  Generalizing this result, if you must check for n conditions, your pattern only needs to include n-1 lookaheads at the most.
		 *  Often, you are even able to combine several conditions into a single lookahead.
		 *
		 *  You may object that we were able to use \w{6,10}\z because it happened to match the whole string.
		 *  Indeed that was the case. But we could also have converted any of the other three lookaheads to match the entire string.
		 *  For instance, taking the lookahead (?=\D*\d) which checks for the presence of one digit,
		 *  we can add a simple .*\z to get us to the end of the string.
		 *
		 *  The pattern would have become:
		 * \A(?=\w{6,10}\z)(?=[^a-z]*[a-z])(?=(?:[^A-Z]*[A-Z]){3})\D*\d.*\z
		 *  By the way, you may wonder why I bother using the \z after the .*:
		 *  shouldn't it get me to the end of the string? In general, not so: unless we're in DOTALL mode, the dot doesn't match line breaks.
		 *  Therefore, the .* only gets you to the end of the first line.
		 *  After this, the string may have line breaks and many more line.
		 *  A \z anchor ensures that after the .* we have reached not only the end of the line, but also the end of the string.
		 *
		 *  In this particular pattern, the first lookaround (?=\w{6,10}\z) already ensures that
		 *  there cannot be any line breaks in the string, so the final \z is not strictly necessary.
		 * */
	}



	private void printMatcher(Matcher matcher){
		boolean found = false;
		while (matcher.find()) {
			System.out.println(
			String.format("I found the text" +
							" \"%s\" starting at " +
							"index %d and ending at index %d.",
					matcher.group(),
					matcher.start(),
					matcher.end())
			);
			found = true;
		}
		if(!found){
			System.out.println("No match found.%n");
		}
	}
}
