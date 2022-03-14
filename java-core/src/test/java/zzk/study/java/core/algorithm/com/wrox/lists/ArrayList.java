package zzk.study.java.core.algorithm.com.wrox.lists;
import zzk.study.java.core.algorithm.com.wrox.iteration.ArrayIterator;
import zzk.study.java.core.algorithm.com.wrox.iteration.Iterator;

public class ArrayList implements List {
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private final int _initialCapacity;
    private Object[] _array;
    private int _size;
    public ArrayList() {
        this(DEFAULT_INITIAL_CAPACITY);
    }
    public ArrayList(int initialCapacity) {
        assert initialCapacity > 0 : "initialCapacity must be > 0";
        _initialCapacity = initialCapacity;
        clear();
    }
    public void clear() {
        _array = new Object[_initialCapacity];
        _size = 0;
    }
    public void insert(int index, Object value)
            throws IndexOutOfBoundsException {
        assert value != null : "value can’t be null";
        if (index < 0 || index > _size) {
            throw new IndexOutOfBoundsException();
        }
        ensureCapacity(_size + 1);
        System.arraycopy(_array, index, _array, index + 1, _size - index);
        _array[index] = value;
        ++_size;
    }
    private void ensureCapacity(int capacity) {
        assert capacity > 0 : "capacity must be > 0";
        if (_array.length < capacity) {
            Object[] copy = new Object[capacity + capacity / 2];
            System.arraycopy(_array, 0, copy, 0, _size);
            _array = copy;
        }
    }
    public void add(Object value) {
        insert(_size, value);
    }
    public Object get(int index) throws IndexOutOfBoundsException {
        checkOutOfBounds(index);
        return _array[index];
    }
    public Object set(int index, Object value)
            throws IndexOutOfBoundsException {
        assert value != null : "value can’t be null";
        checkOutOfBounds(index);
        Object oldValue = _array[index];
        _array[index] = value;
        return oldValue;
    }
    private void checkOutOfBounds(int index) {
        if (isOutOfBounds(index)) {
            throw new IndexOutOfBoundsException();
        }
    }
    private boolean isOutOfBounds(int index) {
        return index < 0 || index >= _size;
    }
    public int indexOf(Object value) {
        assert value != null : "value can’t be null";
        for (int i = 0; i < _size; ++i) {
            if (value.equals(_array[i])) {
                return i;
            }
        }
        return -1;
    }
    public boolean contains(Object value) {
        return indexOf(value) != -1;
    }
    public Object delete(int index) throws IndexOutOfBoundsException {
        checkOutOfBounds(index);
        Object value = _array[index];
        int copyFromIndex = index + 1;
        if (copyFromIndex < _size) {
            System.arraycopy(_array, copyFromIndex,
                    _array, index,
                    _size - copyFromIndex);
        }
        _array[--_size] = null;
        return value;
    }
    public boolean delete(Object value) {
        int index = indexOf(value);
        if (index != -1) {
            delete(index);
            return true;
        }
        return false;
    }
    public Iterator iterator() {
        return new ArrayIterator(_array, 0, _size);
    }
    public int size() {
        return _size;
    }
    public boolean isEmpty() {
        return size() == 0;
    }

}