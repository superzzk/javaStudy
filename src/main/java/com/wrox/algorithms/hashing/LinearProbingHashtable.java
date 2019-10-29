package com.wrox.algorithms.hashing;
public class LinearProbingHashtable implements Hashtable {
    private Object[] _values;
    private int _size;
    public LinearProbingHashtable(int initialCapacity) {
        assert initialCapacity > 0 : "initialCapacity can’t be < 1";
        _values = new Object[initialCapacity];
    }
    public void add(Object value) {
        ensureCapacityForOneMore();
        int index = indexFor(value);
        if (_values[index] == null) {
            _values[index] = value;
            ++_size;
        }
    }
    public boolean contains(Object value) {
        return indexOf(value) != -1;
    }
    public int size() {
        return _size;
    }
    private int indexFor(Object value) {
        int start = startingIndexFor(value);
        int index = indexFor(value, start, _values.length);
        if (index == -1) {
            index = indexFor(value, 0, start);
            assert index == -1 : "no free slots";
        }
        return index;
    }
    private int indexFor(Object value, int start, int end) {
        assert value != null : "value can’t be null";
        for (int i = start; i < end; ++i) {
            if (_values[i] == null || value.equals(_values[i])) {
                return i;
            }
        }
        return -1;
    }
    private int indexOf(Object value) {
        int start = startingIndexFor(value);
        int index = indexOf(value, start, _values.length);
        if (index == -1) {
            index = indexOf(value, 0, start);
        }
        return index;
    }
    private int indexOf(Object value, int start, int end) {
        assert value != null : "value can’t be null";
        for (int i = start; i < end; ++i) {
            if (value.equals(_values[i])) {
                return i;
            }
        }
        return -1;
    }
    private int startingIndexFor(Object value) {
        assert value != null : "value can’t be null";
        return Math.abs(value.hashCode() % _values.length);
    }
    private void ensureCapacityForOneMore() {
        if (size() == _values.length) {
            resize();
        }
    }
    private void resize() {
        LinearProbingHashtable copy =
                new LinearProbingHashtable(_values.length * 2);
        for (int i = 0; i < _values.length; ++i) {
            if (_values[i] != null) {
                copy.add(_values[i]);
            }
        }
        _values = copy._values;
    }
}