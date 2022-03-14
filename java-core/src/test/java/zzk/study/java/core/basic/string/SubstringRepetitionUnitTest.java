package zzk.study.java.core.basic.string;

import org.junit.Test;

import static zzk.study.java.core.basic.string.SubstringRepetitionUnitTest.SubstringRepetition.containsOnlySubstrings;
import static zzk.study.java.core.basic.string.SubstringRepetitionUnitTest.SubstringRepetition.containsOnlySubstringsEfficient;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * The Problem
 * Before we continue with the implementation, let's set up some conditions. First, we'll assume that our String has at least two characters.
 *
 * Second, there's at least one repetition of a substring.
 *
 * This is best illustrated with some examples by checking out a few repeated substrings:
 *
 * "aa"
 * "ababab"
 * "barrybarrybarry"
 * And a few non-repeated ones:
 *
 * "aba"
 * "cbacbac"
 * "carlosxcarlosy"
 * We'll now show a few solutions to the problem.
 * */
public class SubstringRepetitionUnitTest {

    private String validString = "aa";
    private String validStringTwo = "ababab";
    private String validStringThree = "baeldungbaeldung";

    private String invalidString = "aca";
    private String invalidStringTwo = "ababa";
    private String invalidStringThree = "baeldungnonrepeatedbaeldung";

    @Test
    public void givenValidStrings_whenCheckIfContainsOnlySubstrings_thenReturnsTrue() {
        assertTrue(containsOnlySubstrings(validString));
        assertTrue(containsOnlySubstrings(validStringTwo));
        assertTrue(containsOnlySubstrings(validStringThree));
    }

    @Test
    public void givenInvalidStrings_whenCheckIfContainsOnlySubstrings_thenReturnsFalse() {
        assertFalse(containsOnlySubstrings(invalidString));
        assertFalse(containsOnlySubstrings(invalidStringTwo));
        assertFalse(containsOnlySubstrings(invalidStringThree));
    }

    @Test
    public void givenValidStrings_whenCheckEfficientlyIfContainsOnlySubstrings_thenReturnsTrue() {
        assertTrue(containsOnlySubstringsEfficient(validString));
        assertTrue(containsOnlySubstringsEfficient(validStringTwo));
        assertTrue(containsOnlySubstringsEfficient(validStringThree));
    }

    @Test
    public void givenInvalidStrings_whenCheckEfficientlyIfContainsOnlySubstrings_thenReturnsFalse() {
        assertFalse(containsOnlySubstringsEfficient(invalidString));
        assertFalse(containsOnlySubstringsEfficient(invalidStringTwo));
        assertFalse(containsOnlySubstringsEfficient(invalidStringThree));
    }

    public static class SubstringRepetition {

        /**
         * The process is rather simple: we'll check the String‘s length and eliminate the single character Strings at the very beginning.
         *
         * Then, since the length of a substring can't be larger than a half of the string's length, we'll iterate through the half of the String and create the substring in every iteration by appending the next character to the previous substring.
         *
         * We'll next remove those substrings from the original String and check if the length of the “stripped” one is zero. That would mean that it's made only of its substrings
         * */
        public static boolean containsOnlySubstrings(String string) {

            if (string.length() < 2) {
                return false;
            }

            StringBuilder substr = new StringBuilder();
            for (int i = 0; i < string.length() / 2; i++) {
                substr.append(string.charAt(i));

                String clearedFromSubstrings = string.replaceAll(substr.toString(), "");

                if (clearedFromSubstrings.length() == 0) {
                    return true;
                }
            }

            return false;
        }

        /**
         * Namely, we should make use of the fact that a String is made of the repeated substrings if and only if it's a nontrivial rotation of itself.
         *
         * The rotation here means that we remove some characters from the beginning of the String and put them at the end. For example, “eldungba” is the rotation of “baeldung”. If we rotate a String and get the original one, then we can apply this rotation over and over again and get the String consisting of the repeated substrings.
         *
         * Next, we need to check if this is the case with our example. To accomplish this, we'll make use of the theorem which says that if String A and String B have the same length, then we can say that A is a rotation of B if and only if A is a substring of BB. If we go with the example from the previous paragraph, we can confirm this theorem: baeldungbaeldung.
         *
         * Since we know that our String A will always be a substring of AA, we then only need to check if the String A is a substring of AA excluding the first character
         * */
        public static boolean containsOnlySubstringsEfficient(String string) {

            return ((string + string).indexOf(string, 1) != string.length());
        }
    }

}
