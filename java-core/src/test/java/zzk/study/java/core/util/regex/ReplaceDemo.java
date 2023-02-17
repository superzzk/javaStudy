package zzk.study.java.core.util.regex;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * https://www.baeldung.com/java-regex-token-replacement
 * Let's imagine we want to build an algorithm to process all the title words in a string.
 * These words start with one uppercase character and then either end or continue with only lowercase characters.
 */
public class ReplaceDemo {
    private static final String INPUT = "First 3 Capital Words! then 10 TLAs, I Found";
    /*
    * From the definition of a title word, this contains the matches:
    First
    Capital
    Words
    I
    Found
    * */
    private static final Pattern TITLE_CASE_PATTERN = Pattern.compile("(?<=^|[^A-Za-z])([A-Z][a-z]*)(?=[^A-Za-z]|$)");


    @Test
    public void testMatch() {
        Matcher matcher = TITLE_CASE_PATTERN.matcher(INPUT);
        List<String> matches = new ArrayList<>();
        while (matcher.find()) {
            matches.add(matcher.group(1));
        }

        assertThat(matches).containsExactly("First", "Capital", "Words", "I", "Found");
    }

    @Test
    public void matchDetail() {
        Matcher matcher = TITLE_CASE_PATTERN.matcher(INPUT);
        while (matcher.find()) {
            System.out.println("Match: " + matcher.group(0));
            System.out.println("Start: " + matcher.start());
            System.out.println("End: " + matcher.end());
        }
    }

    @Test
    public void replace() {
        int lastIndex = 0;
        StringBuilder output = new StringBuilder();
        Matcher matcher = TITLE_CASE_PATTERN.matcher(INPUT);
        while (matcher.find()) {
            output.append(INPUT, lastIndex, matcher.start())
                    //这里0和1都成功，不知道为什么?
                    .append(matcher.group(1).toLowerCase());

            lastIndex = matcher.end();
        }
        if (lastIndex < INPUT.length()) {
            output.append(INPUT, lastIndex, INPUT.length());
        }
        Assertions.assertEquals("first 3 capital words! then 10 TLAs, i found", output.toString());
    }
}
