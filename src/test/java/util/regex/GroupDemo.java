package util.regex;

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

	/**
	 * <b>*?</b>
	 * ? after a quantifier makes it a reluctant quantifier.
	 * It tries to find the smallest match. This makes the regular expression stop at the first match
	 * */
	public static void ttt(){

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
	public static void test(){

	}


	/**
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
}
