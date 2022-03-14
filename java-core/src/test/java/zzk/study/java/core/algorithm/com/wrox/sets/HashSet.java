package zzk.study.java.core.algorithm.com.wrox.sets;
import zzk.study.java.core.algorithm.com.wrox.hashing.HashtableIterator;
import zzk.study.java.core.algorithm.com.wrox.iteration.ArrayIterator;
import zzk.study.java.core.algorithm.com.wrox.iteration.Iterator;

public class HashSet implements Set {
    public static final int DEFAULT_CAPACITY = 17;
    public static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private final int _initialCapacity;
    private final float _loadFactor;
    private ListSet[] _buckets;
    private int _size;
    public HashSet() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }
    public HashSet(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }
    public HashSet(int initialCapacity, float loadFactor) {
        assert initialCapacity > 0 : "initialCapacity can’t be < 1";
        assert loadFactor > 0 : "loadFactor can’t be <= 0";
        _initialCapacity = initialCapacity;
        _loadFactor = loadFactor;
        clear();
    }
    public boolean contains(Object value) {
        ListSet bucket = _buckets[bucketIndexFor(value)];
        return bucket != null && bucket.contains(value);
    }
    public boolean add(Object value) {
        ListSet bucket = bucketFor(value);
        if (bucket.add(value)) {
            ++_size;
            maintainLoad();
            return true;
        }
        return false;
    }
    public boolean delete(Object value) {
        int bucketIndex = bucketIndexFor(value);
        ListSet bucket = _buckets[bucketIndex];
        if (bucket != null && bucket.delete(value)) {
            --_size;
            if (bucket.isEmpty()) {
                _buckets[bucketIndex] = null;
            }
            return true;
        }
        return false;
    }
    public Iterator iterator() {
        return new HashtableIterator(new ArrayIterator(_buckets));
    }

    public void clear() {
        _buckets = new ListSet[_initialCapacity];
        _size = 0;
    }
    public int size() {
        return _size;
    }
    public boolean isEmpty() {
        return size() == 0;
    }
    private ListSet bucketFor(Object value) {
        int bucketIndex = bucketIndexFor(value);
        ListSet bucket = _buckets[bucketIndex];
        if (bucket == null) {
            bucket = new ListSet();
            _buckets[bucketIndex] = bucket;
        }
        return bucket;
    }
    private int bucketIndexFor(Object value) {
        assert value != null : "value can’t be null";
        return Math.abs(value.hashCode() % _buckets.length);
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
        HashSet copy = new HashSet(_buckets.length * 2, _loadFactor);
        for (int i = 0; i < _buckets.length; ++i) {
            if (_buckets[i] != null) {
                copy.addAll(_buckets[i].iterator());
            }
        }
        _buckets = copy._buckets;
    }
    private void addAll(Iterator values) {
        assert values != null : "values can’t be null";
        for (values.first(); !values.isDone(); values.next()) {
            add(values.current());
        }
    }
}