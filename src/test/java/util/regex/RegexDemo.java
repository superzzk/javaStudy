package util.regex;

import javax.print.DocFlavor;
import java.util.regex.Pattern;

public class RegexDemo {
	private static Pattern usrNamePtrn = Pattern.compile("^[a-z0-9_-]{6,14}$");
	private static Pattern emailNamePtrn = Pattern.compile(
			"^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	private static Pattern dateFrmtPtrn =
			Pattern.compile("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)");


	public static void main(String args[]){
		//commonPattern();

		System.out.println("Is 'java2novice' a valid user name? "
				+usrNamePtrn.matcher("java2novice").matches());     //true
		System.out.println("Is 'cric' a valid user name? "
				+usrNamePtrn.matcher("cric").matches());            //false
		System.out.println("Is 'JAVA2NOVICE' a valid user name? "
				+usrNamePtrn.matcher("JAVA2NOVICE").matches());     //false
		System.out.println("Is 'java.2.novice' a valid user name? "
				+usrNamePtrn.matcher("java.2.novice").matches());   //false
		System.out.println("Is 'java_2-novice' a valid user name? "
				+usrNamePtrn.matcher("java_2-novice").matches());   //true

		System.out.println("Is 'java2novice@gmail.com' a valid email address? "
				+emailNamePtrn.matcher("java2novice@gmail.com").matches()); //true
		System.out.println("Is 'cric*7*&@yahoo.com' a valid email address? "
				+emailNamePtrn.matcher("cric*7*&@yahoo.com").matches());    //false
		System.out.println("Is 'JAVA2NOVICE.gmail.com' a valid email address? "
				+emailNamePtrn.matcher("JAVA2NOVICE.gmail.com").matches()); //false

		System.out.println("Is '03/04/2012' a valid date format? "
				+dateFrmtPtrn.matcher("03/04/2012").matches());     //true
		System.out.println("Is '12/23/2012' a valid date format? "
				+dateFrmtPtrn.matcher("12/23/2012").matches());     //false
		System.out.println("Is '12/12/12' a valid date format? "
				+dateFrmtPtrn.matcher("12/12/12").matches());       //false
		System.out.println("Is '3/4/2012' a valid date format? "
				+dateFrmtPtrn.matcher("3/4/2012").matches());       //true
	}

	public static void test1(){
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


	public static void test2(){
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

	public static void commonPattern(){
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


}