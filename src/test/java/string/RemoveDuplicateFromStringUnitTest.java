package string;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class RemoveDuplicateFromStringUnitTest {

    private final static String STR1 = "racecar";
    private final static String STR2 = "J2ee programming";
    private final static String STR_EMPTY = "";

    private RemoveDuplicateFromString removeDuplicateFromString;

    @Before
    public void executedBeforeEach() {
        removeDuplicateFromString = new RemoveDuplicateFromString();
    }


    @Test
    public void whenUsingCharArray_DuplicatesShouldBeRemovedWithoutKeepingStringOrder() {
        String str1 = removeDuplicateFromString.removeDuplicatesUsingCharArray(STR1);
        Assert.assertEquals("ecar", str1);
        String str2 = removeDuplicateFromString.removeDuplicatesUsingCharArray(STR2);
        Assert.assertEquals("J2e poraming", str2);
        String strEmpty = removeDuplicateFromString.removeDuplicatesUsingCharArray(STR_EMPTY);
        Assert.assertEquals("", strEmpty);
    }

    @Test
    public void whenUsingLinkedHashSet_DuplicatesShouldBeRemovedAndItKeepStringOrder() {
        String str1 = removeDuplicateFromString.removeDuplicatesUsinglinkedHashSet(STR1);
        String str2 = removeDuplicateFromString.removeDuplicatesUsinglinkedHashSet(STR2);

        String strEmpty = removeDuplicateFromString.removeDuplicatesUsinglinkedHashSet(STR_EMPTY);
        Assert.assertEquals("", strEmpty);
        Assert.assertEquals("race", str1);
        Assert.assertEquals("J2e progamin", str2);
    }

    @Test
    public void whenUsingSorting_DuplicatesShouldBeRemovedWithoutKeepingStringOrder() {
        String str1 = removeDuplicateFromString.removeDuplicatesUsingSorting(STR1);
        String str2 = removeDuplicateFromString.removeDuplicatesUsingSorting(STR2);

        String strEmpty = removeDuplicateFromString.removeDuplicatesUsingSorting(STR_EMPTY);
        Assert.assertEquals("", strEmpty);
        Assert.assertEquals("acer", str1);
        Assert.assertEquals(" 2Jaegimnopr", str2);
    }

    @Test
    public void whenUsingHashSet_DuplicatesShouldBeRemovedWithoutKeepingStringOrder() {
        String str1 = removeDuplicateFromString.removeDuplicatesUsingHashSet(STR1);
        String str2 = removeDuplicateFromString.removeDuplicatesUsingHashSet(STR2);
        String strEmpty = removeDuplicateFromString.removeDuplicatesUsingHashSet(STR_EMPTY);
        Assert.assertEquals("", strEmpty);
        Assert.assertEquals("arce", str1);
        Assert.assertEquals(" pa2regiJmno", str2);
    }

    @Test
    public void whenUsingIndexOf_DuplicatesShouldBeRemovedWithoutKeepingStringOrder() {
        String str1 = removeDuplicateFromString.removeDuplicatesUsingIndexOf(STR1);
        String str2 = removeDuplicateFromString.removeDuplicatesUsingIndexOf(STR2);
        String strEmpty = removeDuplicateFromString.removeDuplicatesUsingIndexOf(STR_EMPTY);
        Assert.assertEquals("", strEmpty);
        Assert.assertEquals("ecar", str1);
        Assert.assertEquals("J2e poraming", str2);
    }

    @Test
    public void whenUsingJava8_DuplicatesShouldBeRemovedAndItKeepStringOrder() {
        String str1 = removeDuplicateFromString.removeDuplicatesUsingDistinct(STR1);
        String str2 = removeDuplicateFromString.removeDuplicatesUsingDistinct(STR2);
        String strEmpty = removeDuplicateFromString.removeDuplicatesUsingDistinct(STR_EMPTY);
        Assert.assertEquals("", strEmpty);
        Assert.assertEquals("race", str1);
        Assert.assertEquals("J2e progamin", str2);
    }

    public class RemoveDuplicateFromString {

        String removeDuplicatesUsingCharArray(String str) {

            char[] chars = str.toCharArray();
            StringBuilder sb = new StringBuilder();
            int repeatedCtr;
            for (int i = 0; i < chars.length; i++) {
                repeatedCtr = 0;
                for (int j = i + 1; j < chars.length; j++) {
                    if (chars[i] == chars[j]) {
                        repeatedCtr++;
                    }
                }
                if (repeatedCtr == 0) {
                    sb.append(chars[i]);
                }
            }
            return sb.toString();
        }

        String removeDuplicatesUsinglinkedHashSet(String str) {

            StringBuilder sb = new StringBuilder();
            Set<Character> linkedHashSet = new LinkedHashSet<>();

            for (int i = 0; i < str.length(); i++) {
                linkedHashSet.add(str.charAt(i));
            }

            for (Character c : linkedHashSet) {
                sb.append(c);
            }

            return sb.toString();
        }

        String removeDuplicatesUsingSorting(String str) {
            StringBuilder sb = new StringBuilder();
            if(!str.isEmpty()) {
                char[] chars = str.toCharArray();
                Arrays.sort(chars);

                sb.append(chars[0]);
                for (int i = 1; i < chars.length; i++) {
                    if (chars[i] != chars[i - 1]) {
                        sb.append(chars[i]);
                    }
                }
            }

            return sb.toString();
        }

        String removeDuplicatesUsingHashSet(String str) {

            StringBuilder sb = new StringBuilder();
            Set<Character> hashSet = new HashSet<>();

            for (int i = 0; i < str.length(); i++) {
                hashSet.add(str.charAt(i));
            }

            for (Character c : hashSet) {
                sb.append(c);
            }

            return sb.toString();
        }

        String removeDuplicatesUsingIndexOf(String str) {

            StringBuilder sb = new StringBuilder();
            int idx;
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                idx = str.indexOf(c, i + 1);
                if (idx == -1) {
                    sb.append(c);
                }
            }
            return sb.toString();
        }


        String removeDuplicatesUsingDistinct(String str) {
            StringBuilder sb = new StringBuilder();
            str.chars().distinct().forEach(c -> sb.append((char) c));
            return sb.toString();
        }

    }
}
