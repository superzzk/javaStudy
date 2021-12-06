package zzk.study.java.core.util.collection;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhangzhongkun
 * @since 2019-07-30 20:26
 **/
public class MapToString {
    private Map<Integer, String> getMap() {
        Map<Integer, String> wordsByKey = new HashMap<>();
        wordsByKey.put(1, "one");
        wordsByKey.put(2, "two");
        wordsByKey.put(3, "three");
        wordsByKey.put(4, "four");
        return wordsByKey;
    }

    @Test
    public void givenMap_WhenUsingIteration_ThenResultingStringIsCorrect() {
        Map<Integer, String> map = getMap();
        StringBuilder mapAsString = new StringBuilder("{");
        for (Integer key : map.keySet()) {
            mapAsString.append(key).append("=").append(map.get(key)).append(", ");
        }
        mapAsString.delete(mapAsString.length()-2, mapAsString.length()).append("}");

        Assert.assertEquals("{1=one, 2=two, 3=three, 4=four}", mapAsString.toString());
    }

    private String convertWithIteration(Map<Integer, ?> map) {
        StringBuilder mapAsString = new StringBuilder("{");
        for (Integer key : map.keySet()) {
            mapAsString.append(key).append("=").append(map.get(key)).append(", ");
        }
        mapAsString.delete(mapAsString.length()-2, mapAsString.length()).append("}");
        return mapAsString.toString();
    }

    @Test
    public void givenMap_WhenUsingStream_ThenResultingStringIsCorrect() {
        String mapAsString = convertWithStream(getMap());
        Assert.assertEquals("{1=one, 2=two, 3=three, 4=four}", mapAsString);
    }

    public String convertWithStream(Map<Integer, ?> map) {
        String mapAsString = map.keySet().stream()
                .map(key -> key + "=" + map.get(key))
                .collect(Collectors.joining(", ", "{", "}"));

        return mapAsString;
    }



}
