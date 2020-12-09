package zzk.study.java.core.util.collection;

import org.junit.Test;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class LinkedHashMapDemo {

    @Test
    public void givenLinkedHashMap_whenGetsOrderedKeyset_thenCorrect() {
        LinkedHashMap<Integer, String> map = new LinkedHashMap<>();
        map.put(1, null);
        map.put(2, null);
        map.put(3, null);
        map.put(4, null);
        map.put(5, null);

        Set<Integer> keys = map.keySet();
        Integer[] arr = keys.toArray(new Integer[0]);

        for (int i = 0; i < arr.length; i++) {
            assertEquals(new Integer(i + 1), arr[i]);
        }
    }

    @Test
    public void givenLinkedHashMap_whenAccessOrderWorks_thenCorrect() {
        LinkedHashMap<Integer, String> map
                = new LinkedHashMap<>(16, .75f, true);
        map.put(1, null);
        map.put(2, null);
        map.put(3, null);
        map.put(4, null);
        map.put(5, null);

        Set<Integer> keys = map.keySet();
        assertEquals("[1, 2, 3, 4, 5]", keys.toString());

        map.get(4);
        assertEquals("[1, 2, 3, 5, 4]", keys.toString());

        map.get(1);
        assertEquals("[2, 3, 5, 4, 1]", keys.toString());

        map.get(3);
        assertEquals("[2, 5, 4, 1, 3]", keys.toString());
    }

    /**
     * 自动删除最久未使用的元素
     * */
    public class MyLinkedHashMap<K, V> extends LinkedHashMap<K, V> {

        private static final int MAX_ENTRIES = 5;

        public MyLinkedHashMap(
                int initialCapacity, float loadFactor, boolean accessOrder) {
            super(initialCapacity, loadFactor, accessOrder);
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry eldest) {
            return size() > MAX_ENTRIES;
        }

    }

    @Test
    public void givenLinkedHashMap_whenRemovesEldestEntry_thenCorrect() {
        LinkedHashMap<Integer, String> map
                = new MyLinkedHashMap<>(16, .75f, true);
        map.put(1, null);
        map.put(2, null);
        map.put(3, null);
        map.put(4, null);
        map.put(5, null);
        Set<Integer> keys = map.keySet();
        assertEquals("[1, 2, 3, 4, 5]", keys.toString());

        map.put(6, null);
        assertEquals("[2, 3, 4, 5, 6]", keys.toString());

        map.put(7, null);
        assertEquals("[3, 4, 5, 6, 7]", keys.toString());

        map.put(8, null);
        assertEquals("[4, 5, 6, 7, 8]", keys.toString());
    }
}
