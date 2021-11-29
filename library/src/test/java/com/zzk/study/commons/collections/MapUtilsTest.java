package com.zzk.study.commons.collections;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MapUtilsTest {
    @Test
    public void givenMap_StringUtils_ThenResultingStringIsCorrect() {
        String mapAsString = StringUtils.join(getMap());
        MapUtils.debugPrint(System.out, "Map as String", getMap());
        Assert.assertEquals("{1=one, 2=two, 3=three, 4=four}", mapAsString);
    }

    @Test
    public void givenString_StringUtils_ThenResultingMapIsCorrect() {
        Map<String, String> m = convertWithStream("1=one, 2=two, 3=three, 4=four");
        MapUtils.debugPrint(System.out, "Map as String", m);
    }

    private Map<Integer, String> getMap() {
        Map<Integer, String> wordsByKey = new HashMap<>();
        wordsByKey.put(1, "one");
        wordsByKey.put(2, "two");
        wordsByKey.put(3, "three");
        wordsByKey.put(4, "four");
        return wordsByKey;
    }

    private Map<String, String> convertWithStream(String mapAsString) {
        Map<String, String> map = Arrays.stream(mapAsString.split(","))
                .map(entry -> entry.split("="))
                .collect(Collectors.toMap(entry -> entry[0], entry -> entry[1]));
        return map;
    }

}
