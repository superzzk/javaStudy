package zzk.study.java.core.algorithm.com.wrox.iteration;

public class ArrayIterator implements Iterator {
    private final Object[] _array;
    private final int _first;
    private final int _last;
    private int _current = -1;
    public ArrayIterator(Object[] array, int start, int length) {
        assert array != null : "array can’t be null";
        assert start >= 0 : "start can’t be < 0";
        assert start < array.length : "start can’t be > array.length";
        assert length >= 0 : "length can’t be < 0";
        _array = array;
        _first = start;
        _last = start + length - 1;
        assert _last < array.length : "start + length can’t be > array.length";
    }
    public ArrayIterator(Object[] array) {
        assert array != null : "array can’t be null";
        _array = array;
        _first = 0;
        _last = array.length - 1;
    }
    public void first() {
        _current = _first;
    }

    public void last() {
        _current = _last;
    }
    public void next() {
        ++_current;
    }
    public void previous() {
        --_current;
    }
    public boolean isDone() {
        return _current < _first || _current > _last;
    }
    public Object current() throws IteratorOutOfBoundsException {
        if (isDone()) {
            throw new IteratorOutOfBoundsException();
        }
        return _array[_current];
    }
}