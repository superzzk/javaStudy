package util.regex;

public class GroupDemo {
	public static final String EXAMPLE_TEST = "This is my small example "
			+ "string which I'm going to " + "use for pattern matching.";

	public static void main(String[] args) {
		String s1 = "test test , test";
		// Removes whitespace between a word character and . or ,
		String pattern = "(\\w)(\\s+)([\\.,])";
		System.out.println(s1.replaceAll(pattern, "$1$3"));
		;


		// Extract the text between the two title elements
		pattern = "(?i)(<title.*?>)(.+?)()";
		String updated = EXAMPLE_TEST.replaceAll(pattern, "$2");
	}
}
