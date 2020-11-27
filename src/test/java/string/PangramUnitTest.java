package string;

import org.junit.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PangramUnitTest {

    @Test
    public void givenValidString_isPangram_shouldReturnSuccess() {
        String input = "Two driven jocks help fax my big quiz";
        assertTrue(Pangram.isPangram(input));
        assertTrue(Pangram.isPangramWithStreams(input));
    }

    @Test
    public void givenNullString_isPangram_shouldReturnFailure() {
        String input = null;
        assertFalse(Pangram.isPangram(input));
        assertFalse(Pangram.isPangramWithStreams(input));
        assertFalse(Pangram.isPerfectPangram(input));
    }

    @Test
    public void givenPerfectPangramString_isPerfectPangram_shouldReturnSuccess() {
        String input = "abcdefghijklmNoPqrStuVwxyz";
        assertTrue(Pangram.isPerfectPangram(input));
    }

    @Test
    public void givenNonPangramString_isPangram_shouldReturnFailure() {
        String input = "invalid pangram";
        assertFalse(Pangram.isPangram(input));
        assertFalse(Pangram.isPangramWithStreams(input));
    }

    @Test
    public void givenPangram_isPerfectPangram_shouldReturnFailure() {
        String input = "Two driven jocks help fax my big quiz";
        assertFalse(Pangram.isPerfectPangram(input));
    }

    public static class Pangram {
        private static final int ALPHABET_COUNT = 26;

        public static boolean isPangram(String str) {
            if (str == null)
                return false;
            Boolean[] alphabetMarker = new Boolean[ALPHABET_COUNT];
            Arrays.fill(alphabetMarker, false);
            int alphabetIndex = 0;
            String strUpper = str.toUpperCase();
            for (int i = 0; i < str.length(); i++) {
                if ('A' <= strUpper.charAt(i) && strUpper.charAt(i) <= 'Z') {
                    alphabetIndex = strUpper.charAt(i) - 'A';
                    alphabetMarker[alphabetIndex] = true;
                }
            }
            for (boolean index : alphabetMarker) {
                if (!index)
                    return false;
            }
            return true;
        }

        public static boolean isPangramWithStreams(String str) {
            if (str == null)
                return false;

            // filtered character stream
            String strUpper = str.toUpperCase();
            Stream<Character> filteredCharStream = strUpper.chars()
                    .filter(item -> ((item >= 'A' && item <= 'Z')))
                    .mapToObj(c -> (char) c);
            Map<Character, Boolean> alphabetMap = filteredCharStream.collect(Collectors.toMap(item -> item, k -> Boolean.TRUE, (p1, p2) -> p1));

            return (alphabetMap.size() == ALPHABET_COUNT);
        }

        public static boolean isPerfectPangram(String str) {
            if (str == null)
                return false;

            // filtered character stream
            String strUpper = str.toUpperCase();
            Stream<Character> filteredCharStream = strUpper.chars()
                    .filter(item -> ((item >= 'A' && item <= 'Z')))
                    .mapToObj(c -> (char) c);
            Map<Character, Long> alphabetFrequencyMap = filteredCharStream.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            return (alphabetFrequencyMap.size() == ALPHABET_COUNT && alphabetFrequencyMap.values()
                    .stream()
                    .allMatch(item -> item == 1));
        }
    }


}
