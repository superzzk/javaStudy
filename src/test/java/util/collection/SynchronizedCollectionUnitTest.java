package util.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;

public class SynchronizedCollectionUnitTest {

    @Test
    public void givenSynchronizedCollection_whenTwoThreadsAddElements_thenCorrectCollectionSize() throws InterruptedException {
        Collection<Integer> syncCollection = Collections.synchronizedCollection(new ArrayList<>());

        Runnable listOperations = () -> {
            syncCollection.addAll(Arrays.asList(1, 2, 3, 4, 5, 6));
        };
        Thread thread1 = new Thread(listOperations);
        Thread thread2 = new Thread(listOperations);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        Assert.assertEquals(syncCollection.size(), 12 );
    }

    @Test
    public void givenSynchronizedList_whenTwoThreadsAddElements_thenCorrectListSize() throws InterruptedException {
        List<Integer> syncList = Collections.synchronizedList(new ArrayList<>());

        Runnable listOperations = () -> {
            syncList.addAll(Arrays.asList(1, 2, 3, 4, 5, 6));
        };
        Thread thread1 = new Thread(listOperations);
        Thread thread2 = new Thread(listOperations);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        Assert.assertEquals(syncList.size(), 12);
    }

    @Test
    public void givenStringList_whenTwoThreadsIterateOnSynchronizedList_thenCorrectResult() throws InterruptedException {
        List<String> syncCollection = Collections.synchronizedList(Arrays.asList("a", "b", "c"));
        List<String> uppercasedCollection = new ArrayList<>();

        Runnable listOperations = () -> {
            synchronized (syncCollection) {
                syncCollection.forEach((e) -> uppercasedCollection.add(e.toUpperCase()));
            }
        };

        Thread thread1 = new Thread(listOperations);
        Thread thread2 = new Thread(listOperations);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        Assert.assertEquals(uppercasedCollection.get(0), "A");
    }

    @Test
    public void givenSynchronizedMap_whenTwoThreadsAddElements_thenCorrectMapSize() throws InterruptedException {
        Map<Integer, String> map = new HashMap<>();//如果不是同步的，最后的map.size可能更大
        Map<Integer, String> syncMap = Collections.synchronizedMap(new HashMap<>());

        Runnable mapOperations = () -> {
            map.put(1, "one");
            map.put(2, "two");
            map.put(3, "three");
            syncMap.put(1, "one");
            syncMap.put(2, "two");
            syncMap.put(3, "three");
        };
        Thread thread1 = new Thread(mapOperations);
        Thread thread2 = new Thread(mapOperations);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        Assert.assertEquals(syncMap.size(), 3);
    }

    @Test
    public void givenSynchronizedSorteMap_whenTwoThreadsAddElements_thenCorrectSortedMapSize() throws InterruptedException {
        Map<Integer, String> syncSortedMap = Collections.synchronizedSortedMap(new TreeMap<>());

        Runnable sortedMapOperations = () -> {
            syncSortedMap.put(1, "One");
            syncSortedMap.put(2, "Two");
            syncSortedMap.put(3, "Three");
        };
        Thread thread1 = new Thread(sortedMapOperations);
        Thread thread2 = new Thread(sortedMapOperations);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        Assert.assertEquals(syncSortedMap.size(), 3);
    }

    @Test
    public void givenSynchronizedSortedSet_whenTwoThreadsAddElements_thenCorrectSortedSetSize() throws InterruptedException {
        SortedSet<Integer> syncSortedSet = Collections.synchronizedSortedSet(new TreeSet<>());

        Runnable sortedSetOperations = () -> syncSortedSet.addAll(Arrays.asList(1, 2, 3, 4, 5, 6));
        sortedSetOperations.run();
        sortedSetOperations.run();
        Thread thread1 = new Thread(sortedSetOperations);
        Thread thread2 = new Thread(sortedSetOperations);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        Assert.assertEquals(syncSortedSet.size(), 6);
    }
}