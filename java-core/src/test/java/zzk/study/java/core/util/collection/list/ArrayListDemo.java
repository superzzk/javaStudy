package zzk.study.java.core.util.collection.list;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayListDemo {

    @Test
    public void testEquals() {
        ArrayList<String> firstList = new ArrayList<>(Arrays.asList("Apple", "pears", "Guava", "Mango"));
        List<String> secondList = new ArrayList<String>(firstList);
        assertTrue(firstList.equals(secondList));
        assertEquals(firstList,secondList);
        secondList.add("Papaya");
        assertFalse(firstList.equals(secondList));
    }

    @Test
    public void testRemoveAll() {
        ArrayList<String> firstList = new ArrayList<String>();
        firstList.add("Apple");
        firstList.add("Pears");
        firstList.add("Guava");
        firstList.add("Peach");
        ArrayList<String> secondList = new ArrayList<String>();
        secondList.add("Apple");
        secondList.add("Pears");
        secondList.add("Papaya");
        secondList.add("Peach");
        secondList.removeAll(firstList);

        assertEquals(secondList, Arrays.asList("Papaya"));
    }

    @Test
    public void testRetainAll(){

        ArrayList<String> firstList = new ArrayList<String>(Arrays.asList("M", "W", "J", "K", "T"));
        List<String> secondList=new ArrayList<String>(Arrays.asList("M", "W", "E", "K", "T"));
        secondList.retainAll(firstList);
        assertEquals(secondList, Arrays.asList("M","W","K","T"));

    }

    @Test
    public void toArray() {
        // list to array
        List<Integer> l = new ArrayList<>();
        l.add(1);
        l.add(2);
        l.add(3);
        Integer[] array = l.toArray(new Integer[0]);
        System.out.println("list: " + l);
        System.out.println("array: " + Arrays.toString(array));
    }
}
