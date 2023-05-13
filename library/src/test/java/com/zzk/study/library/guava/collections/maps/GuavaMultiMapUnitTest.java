package com.zzk.study.library.guava.collections.maps;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class GuavaMultiMapUnitTest {
    @Test
    public void givenMultiMap_whenAddTwoValuesForSameKey_shouldHaveTwoEntriesInMap() {
        //given
        String key = "a-key";
        Multimap<String, String> map = ArrayListMultimap.create();

        //when
        map.put(key, "firstValue");
        map.put(key, "secondValue");

        //then
        assertEquals(2, map.size());
    }
}