package com.zzk.study.library.commons;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


public class StringUtilsDemo {
    @Test
    public void isBlank() {
        assertThat(StringUtils.isBlank(" ")).isTrue();
    }

    @Test
    public void isEmpty() {
        assertThat(StringUtils.isEmpty("")).isTrue();
    }

    @Test
    public void isAllLowerCase() {
        assertThat(StringUtils.isAllLowerCase("abd")).isTrue();
    }

    @Test
    public void isAllUpperCase() {
        assertThat(StringUtils.isAllUpperCase("ABC")).isTrue();
    }

    @Test
    public void isMixedCase() {
        assertThat(StringUtils.isMixedCase("abC")).isTrue();
    }

    @Test
    public void isAlpha() {
        assertThat(StringUtils.isAlpha("abc")).isTrue();
    }

    @Test
    public void isAlphanumeric() {
        assertThat(StringUtils.isAlphanumeric("abc123")).isTrue();
    }

    @Test
    public void containsAny(){
        String string = "baeldung.com";
        boolean contained1 = StringUtils.containsAny(string, 'a', 'b', 'c');
        boolean contained2 = StringUtils.containsAny(string, 'x', 'y', 'z');
        boolean contained3 = StringUtils.containsAny(string, "abc");
        boolean contained4 = StringUtils.containsAny(string, "xyz");

        assertTrue(contained1);
        assertFalse(contained2);
        assertTrue(contained3);
        assertFalse(contained4);
    }

    @Test
    public void containsIgnoreCase(){
        String string = "baeldung.com";
        boolean contained = StringUtils.containsIgnoreCase(string, "BAELDUNG");

        assertTrue(contained);
    }
    @Test
    public void countMatches(){
        String string = "welcome to www.baeldung.com";
        int charNum = StringUtils.countMatches(string, 'w');
        int stringNum = StringUtils.countMatches(string, "com");

        assertEquals(4, charNum);
        assertEquals(2, stringNum);
    }
    @Test
    public void append_prepend(){
        String string = "baeldung.com";
        String stringWithSuffix = StringUtils.appendIfMissing(string, ".com");
        String stringWithPrefix = StringUtils.prependIfMissing(string, "www.");

        assertEquals("baeldung.com", stringWithSuffix);
        assertEquals("www.baeldung.com", stringWithPrefix);
    }

    @Test
    public void swapCase(){
        String originalString = "baeldung.COM";
        String swappedString = StringUtils.swapCase(originalString);

        assertEquals("BAELDUNG.com", swappedString);
    }
    @Test
    public void capitalize_uncapitalize(){
        String originalString = "baeldung";
        String capitalizedString = StringUtils.capitalize(originalString);

        assertEquals("Baeldung", capitalizedString);

         originalString = "Baeldung";
        String uncapitalizedString = StringUtils.uncapitalize(originalString);

        assertEquals("baeldung", uncapitalizedString);
    }

    @Test
    public void reverse(){
        String originalString = "baeldung";
        String reversedString = StringUtils.reverse(originalString);

        assertEquals("gnudleab", reversedString);

        originalString = "www.baeldung.com";
        reversedString = StringUtils.reverseDelimited(originalString, '.');

        assertEquals("com.baeldung.www", reversedString);
    }

    @Test
    public void rotate(){
        String originalString = "baeldung";
        String rotatedString = StringUtils.rotate(originalString, 4);

        assertEquals("dungbael", rotatedString);
    }

    @Test
    public void difference(){
        String tutorials = "Baeldung Tutorials";
        String courses = "Baeldung Courses";
        String diff1 = StringUtils.difference(tutorials, courses);
        String diff2 = StringUtils.difference(courses, tutorials);

        assertEquals("Courses", diff1);
        assertEquals("Tutorials", diff2);
    }
}
