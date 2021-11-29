package zzk.study.java.core.util.collection.map;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class TreeMapDemo {
    //正序
    @Test
    public void givenTreeMap_whenOrdersEntriesNaturally_thenCorrect() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(3, "val");
        map.put(2, "val");
        map.put(1, "val");
        map.put(5, "val");
        map.put(4, "val");

        assertEquals("[1, 2, 3, 4, 5]", map.keySet().toString());
    }

    //反序
    @Test
    public void givenTreeMap_whenOrdersEntriesByComparator_thenCorrect() {
        TreeMap<Integer, String> map = new TreeMap<>(Comparator.reverseOrder());
        map.put(3, "val");
        map.put(2, "val");
        map.put(1, "val");
        map.put(5, "val");
        map.put(4, "val");

        assertEquals("[5, 4, 3, 2, 1]", map.keySet().toString());
    }

    // firstKey & lastKey
    // floorKey & ceilingKey
    // lowerKey & higherKey
    @Test
    public void queryKey() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(3, "val");
        map.put(2, "val");
        map.put(1, "val");
        map.put(5, "val");
        map.put(4, "val");

        Integer highestKey = map.lastKey();
        Integer lowestKey = map.firstKey();

        assertEquals(5, highestKey.intValue());
        assertEquals(1, lowestKey.intValue());

        assertEquals(3, map.floorKey(3).intValue());
        assertEquals(3, map.ceilingKey(3).intValue());

        assertEquals(2, map.lowerKey(3).intValue());
        assertEquals(4, map.higherKey(3).intValue());
    }

    // headMap & tailMap
    // subMap
    @Test
    public void givenTreeMap_whenPerformsQueries_thenCorrect() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(3, "val");
        map.put(2, "val");
        map.put(1, "val");
        map.put(5, "val");
        map.put(4, "val");

        Set<Integer> keysLessThan3 = map.headMap(3).keySet();
        Set<Integer> keysGreaterThanEqTo3 = map.tailMap(3).keySet();

        assertEquals("[1, 2]", keysLessThan3.toString());
        assertEquals("[3, 4, 5]", keysGreaterThanEqTo3.toString());

        // subMap
        SortedMap<Integer, String> subMap1 = map.subMap(1, 3);
        SortedMap<Integer, String> subMap2 = map.subMap(3, 100);

        assertEquals("[1, 2]", subMap1.keySet().toString());
        assertEquals("[3, 4, 5]", subMap2.keySet().toString());
    }
}
