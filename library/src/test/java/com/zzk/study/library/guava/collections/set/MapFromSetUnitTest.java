package com.zzk.study.library.guava.collections.set;

import com.google.common.base.Function;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertTrue;

public class MapFromSetUnitTest {

    @Test
    public void givenStringSet_whenMapsToElementLength_thenCorrect() {
        Function<Integer, String> function = new Function<Integer, String>() {
            @Override
            public String apply(Integer from) {
                return Integer.toBinaryString(from);
            }
        };
        Set<Integer> set = new TreeSet<>(Arrays.asList(32, 64, 128));
        Map<Integer, String> map = new MapFromSet<Integer, String>(set, function);
        assertTrue(map.get(32).equals("100000")
                && map.get(64).equals("1000000")
                && map.get(128).equals("10000000"));
    }

    @Test
    public void givenIntSet_whenMapsToElementBinaryValue_thenCorrect() {
        Function<String, Integer> function = new Function<String, Integer>() {
            @Override
            public Integer apply(String from) {
                return from.length();
            }
        };
        Set<String> set = new TreeSet<>(Arrays.asList("four", "three", "twelve"));
        Map<String, Integer> map = new MapFromSet<String, Integer>(set, function);
        assertTrue(map.get("four") == 4 && map.get("three") == 5
                && map.get("twelve") == 6);
    }

    @Test
    public void givenSet_whenNewSetElementAddedAndMappedLive_thenCorrect() {
        Function<String, Integer> function = new Function<String, Integer>() {
            @Override
            public Integer apply(String from) {
                return from.length();
            }
        };
        Set<String> set = new TreeSet<>(Arrays.asList("four", "three", "twelve"));
        Map<String, Integer> map = new MapFromSet<String, Integer>(set, function);
        set.add("one");
        assertTrue(map.get("one") == 3 && map.size() == 4);
    }

    public static class MapFromSet<K, V> extends AbstractMap<K, V> {

        private class SingleEntry implements Entry<K, V> {
            private K key;
            public SingleEntry(K key) {
                this.key = key;
            }
            @Override
            public K getKey() {
                return this.key;
            }
            @Override
            public V getValue() {
                V value = MapFromSet.this.cache.get(this.key);
                if (value == null) {
                    value = MapFromSet.this.function.apply(this.key);
                    MapFromSet.this.cache.put(this.key, value);
                }
                return value;
            }
            @Override
            public V setValue(V value) {
                throw new UnsupportedOperationException();
            }
        }

        private class MyEntrySet extends AbstractSet<Entry<K, V>> {
            public class EntryIterator implements Iterator<Entry<K, V>> {
                private Iterator<K> inner;
                public EntryIterator() {
                    this.inner = MyEntrySet.this.keys.iterator();
                }
                @Override
                public boolean hasNext() {
                    return this.inner.hasNext();
                }
                @Override
                public Map.Entry<K, V> next() {
                    K key = this.inner.next();
                    return new SingleEntry(key);
                }
                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            }
            private Set<K> keys;
            public MyEntrySet(Set<K> keys) {
                this.keys = keys;
            }

            @Override
            public Iterator<Map.Entry<K, V>> iterator() {
                return new EntryIterator();
            }

            @Override
            public int size() {
                return this.keys.size();
            }
        }

        private WeakHashMap<K, V> cache;
        private Set<Entry<K, V>> entries;
        private Function<? super K, ? extends V> function;

        public MapFromSet(Set<K> keys, Function<? super K, ? extends V> function) {
            this.function = function;
            this.cache = new WeakHashMap<K, V>();
            this.entries = new MyEntrySet(keys);
        }

        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            return this.entries;
        }

    }
}