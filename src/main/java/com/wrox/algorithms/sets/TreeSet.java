package com.wrox.algorithms.sets;
import com.wrox.algorithms.iteration.Iterator;
import com.wrox.algorithms.iteration.IteratorOutOfBoundsException;
import com.wrox.algorithms.sorting.Comparator;
import com.wrox.algorithms.sorting.NaturalComparator;

public class TreeSet implements Set {
    private final Comparator _comparator;
    private Node _root;
    private int _size;
    public TreeSet() {
        this(NaturalComparator.INSTANCE);
    }
    public TreeSet(Comparator comparator) {
        assert comparator != null : "comparator can’t be null";
        _comparator = comparator;
    }
    public boolean contains(Object value) {
        return search(value) != null;
    }
    public boolean add(Object value) {
        Node parent = null;
        Node node = _root;
        int cmp = 0;
        while (node != null) {
            parent = node;
            cmp = _comparator.compare(value, node.getValue());
            if (cmp == 0) {
                return false;
            }
            node = cmp < 0 ? node.getSmaller() : node.getLarger();
        }
        Node inserted = new Node(parent, value);
        if (parent == null) {
            _root = inserted;
        } else if (cmp < 0) {
            parent.setSmaller(inserted);
        } else {
            parent.setLarger(inserted);
        }
        ++_size;
        return true;
    }
    public boolean delete(Object value) {
        Node node = search(value);
        if (node == null) {
            return false;
        }
        Node deleted = node.getSmaller() != null && node.getLarger() != null ?
                node.successor() : node;
        assert deleted != null : "deleted can’t be null";
        Node replacement = deleted.getSmaller() != null ? deleted.getSmaller() :
                deleted.getLarger();
        if (replacement != null) {
            replacement.setParent(deleted.getParent());
        }
        if (deleted == _root) {
            _root = replacement;
        } else if (deleted.isSmaller()) {
            deleted.getParent().setSmaller(replacement);
        } else {
            deleted.getParent().setLarger(replacement);
        }
        if (deleted != node) {
            Object deletedValue = node.getValue();
            node.setValue(deleted.getValue());
            deleted.setValue(deletedValue);
        }
        --_size;
        return true;
    }
    public Iterator iterator() {
        return new ValueIterator();
    }
    public void clear() {
        _root = null;
        _size = 0;
    }
    public int size() {
        return _size;
    }
    public boolean isEmpty() {
        return _root == null;
    }
    private Node search(Object value) {
        assert value != null : "value can’t be null";
        Node node = _root;
        while (node != null) {
            int cmp = _comparator.compare(value, node.getValue());
            if (cmp == 0) {
                break;
            }
            node = cmp < 0 ? node.getSmaller() : node.getLarger();
        }
        return node;
    }
    private static final class Node {
        private Object _value;
        private Node _parent;
        private Node _smaller;
        private Node _larger;
        public Node(Node parent, Object value) {
            setParent(parent);
            setValue(value);
        }
        public Object getValue() {
            return _value;
        }
        public void setValue(Object value) {
            assert value != null : "value can’t be null";
            _value = value;
        }
        public Node getParent() {
            return _parent;
        }
        public void setParent(Node parent) {
            _parent = parent;
        }
        public Node getSmaller() {
            return _smaller;
        }
        public void setSmaller(Node node) {
            assert node != getLarger() : "smaller can’t be the same as larger";
            _smaller = node;
        }
        public Node getLarger() {
            return _larger;
        }
        public void setLarger(Node node) {
            assert node != getSmaller() : "larger can’t be the same as smaller";
            _larger = node;
        }
        public boolean isSmaller() {

            return getParent() != null && this == getParent().getSmaller();
        }
        public boolean isLarger() {
            return getParent() != null && this == getParent().getLarger();
        }
        public Node minimum() {
            Node node = this;
            while (node.getSmaller() != null) {
                node = node.getSmaller();
            }
            return node;
        }
        public Node maximum() {
            Node node = this;
            while (node.getLarger() != null) {
                node = node.getLarger();
            }
            return node;
        }
        public Node successor() {
            if (getLarger() != null) {
                return getLarger().minimum();
            }
            Node node = this;
            while (node.isLarger()) {
                node = node.getParent();
            }
            return node.getParent();
        }
        public Node predecessor() {
            if (getSmaller() != null) {
                return getSmaller().maximum();
            }
            Node node = this;
            while (node.isSmaller()) {
                node = node.getParent();
            }
            return node.getParent();
        }
    }
    private final class ValueIterator implements Iterator {
        private Node _current;
        public void first() {
            _current = _root != null ? _root.minimum() : null;
        }
        public void last() {
            _current = _root != null ? _root.maximum() : null;
        }
        public boolean isDone() {
            return _current == null;
        }
        public void next() {
            if (!isDone()) {
                _current = _current.successor();
            }
        }
        public void previous() {
            if (!isDone()) {
                _current = _current.predecessor();
            }
        }
        public Object current() throws IteratorOutOfBoundsException {
            if (isDone()) {
                throw new IteratorOutOfBoundsException();
            }
            return _current.getValue();
        }
    }
}