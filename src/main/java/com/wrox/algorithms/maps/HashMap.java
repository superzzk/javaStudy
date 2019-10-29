package com.wrox.algorithms.maps;
import com.wrox.algorithms.hashing.HashtableIterator;
import com.wrox.algorithms.iteration.ArrayIterator;
import com.wrox.algorithms.iteration.Iterator;

public class HashMap implements Map {
    public static final int DEFAULT_CAPACITY = 17;
    public static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private final int _initialCapacity;
    private final float _loadFactor;
    private ListMap[] _buckets;
    private int _size;
    public HashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }
    public HashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }
    public HashMap(int initialCapacity, float loadFactor) {
        assert initialCapacity > 0 : "initialCapacity can’t be < 1";
        assert loadFactor > 0 : "loadFactor can’t be <= 0";
        _initialCapacity = initialCapacity;
        _loadFactor = loadFactor;
        clear();
    }
    public Object get(Object key) {
        ListMap bucket = _buckets[bucketIndexFor(key)];
        return bucket != null ? bucket.get(key) : null;
    }
    public Object set(Object key, Object value) {
        ListMap bucket = bucketFor(key);
        int sizeBefore = bucket.size();
        Object oldValue = bucket.set(key, value);
        if (bucket.size() > sizeBefore) {
            ++_size;
            maintainLoad();
        }
        return oldValue;
    }
    public Object delete(Object key) {
        ListMap bucket = _buckets[bucketIndexFor(key)];
        if (bucket == null) {
            return null;
        }
        int sizeBefore = bucket.size();
        Object value = bucket.delete(key);
        if (bucket.size() < sizeBefore) {
            --_size;
        }
        return value;
    }
    public boolean contains(Object key) {
        ListMap bucket = _buckets[bucketIndexFor(key)];
        return bucket != null && bucket.contains(key);
    }
    public Iterator iterator() {
        return new HashtableIterator(new ArrayIterator(_buckets));
    }
    public void clear() {
        _buckets = new ListMap[_initialCapacity];
        _size = 0;
    }
    public int size() {
        return _size;
    }
    public boolean isEmpty() {
        return size() == 0;
    }
    private int bucketIndexFor(Object key) {
        assert key != null : "key can’t be null";
        return Math.abs(key.hashCode() % _buckets.length);
    }
    private ListMap bucketFor(Object key) {
        int bucketIndex = bucketIndexFor(key);
        ListMap bucket = _buckets[bucketIndex];
        if (bucket == null) {
            bucket = new ListMap();
            _buckets[bucketIndex] = bucket;
        }
        return bucket;
    }
    private void maintainLoad() {
        if (loadFactorExceeded()) {
            resize();
        }
    }
    private boolean loadFactorExceeded() {
        return size() > _buckets.length * _loadFactor;
    }
    private void resize() {
        HashMap copy = new HashMap(_buckets.length * 2, _loadFactor);
        for (int i = 0; i < _buckets.length; ++i) {
            if (_buckets[i] != null) {
                copy.addAll(_buckets[i].iterator());
            }
        }
        _buckets = copy._buckets;
    }
    private void addAll(Iterator entries) {
        assert entries != null : "entries can’t be null";
        for (entries.first(); !entries.isDone(); entries.next()) {
            Map.Entry entry = (Map.Entry) entries.current();
            set(entry.getKey(), entry.getValue());
        }
    }
}