package com.wrox.algorithms.maps;
public class DefaultEntry implements Map.Entry {
    private final Object _key;
    private Object _value;
    public DefaultEntry(Object key, Object value) {
        assert key != null : "key can’t be null";
        _key = key;
        setValue(value);
    }
    public Object getKey() {
        return _key;
    }
    public Object setValue(Object value) {
        Object oldValue = _value;
        _value = value;
        return oldValue;
    }
    public Object getValue() {
        return _value;
    }
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        DefaultEntry other = (DefaultEntry) object;
        return _key.equals(other._key) && _value.equals(other._value);
    }
}