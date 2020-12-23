package zzk.study.java.core.string;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zhangzhongkun
 * @since 2019-08-01 15:20
 **/
public class StringUitlsTest {
    /**
     * shuffle
     */
    @Test
    public void whenShufflingList_thenListIsShuffled() {
        String temp = "abcd1；abcd2";
        String[] result = StringUtils.split(temp, ";；");
        Assert.assertEquals(result[0],"abcd1");
        Assert.assertEquals(result[1],"abcd2");
    }

    @Test
    public void whenShufflingMapEntries_thenValuesAreShuffled() {
        Map<Integer, String> studentsById = new HashMap<>();
        studentsById.put(1, "Foo");
        studentsById.put(2, "Bar");
        studentsById.put(3, "Baz");
        studentsById.put(4, "Qux");

        System.out.println("Students before shuffling:");
        System.out.println(studentsById.values());

        List<Map.Entry<Integer, String>> shuffledStudentEntries = new ArrayList<>(studentsById.entrySet());
        Collections.shuffle(shuffledStudentEntries);

        List<String> shuffledStudents = shuffledStudentEntries.stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        System.out.println("Students after shuffling");
        System.out.println(shuffledStudents);
    }

    @Test
    public void whenShufflingSet_thenElementsAreShuffled() {
        Set<String> students = new HashSet<>(Arrays.asList("Foo", "Bar", "Baz", "Qux"));

        System.out.println("Set before shuffling:");
        System.out.println(students);

        List<String> studentList = new ArrayList<>(students);

        Collections.shuffle(studentList);
        System.out.println("Shuffled set elements:");
        System.out.println(studentList);
    }

    @Test
    public void whenShufflingWithSameRandomness_thenElementsAreShuffledDeterministically() {
        List<String> students_1 = Arrays.asList("Foo", "Bar", "Baz", "Qux");
        List<String> students_2 = Arrays.asList("Foo", "Bar", "Baz", "Qux");

        Collections.shuffle(students_1, new Random(5));
        Collections.shuffle(students_2, new Random(5));

        Assert.assertEquals(students_1, students_2);
    }

}
