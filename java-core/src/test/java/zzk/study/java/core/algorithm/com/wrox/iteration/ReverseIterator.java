package zzk.study.java.core.algorithm.com.wrox.iteration;
public class ReverseIterator implements Iterator {
    private final Iterator _iterator;
    public ReverseIterator(Iterator iterator) {
        assert iterator != null : "iterator can’t be null";
        _iterator = iterator;
    }
    public boolean isDone() {
        return _iterator.isDone();
    }
    public Object current() throws IteratorOutOfBoundsException {
        return _iterator.current();
    }
    public void first() {
        _iterator.last();
    }
    public void last() {
        _iterator.first();
    }
    public void next() {
        _iterator.previous();
    }
    public void previous() {
        _iterator.next();
    }
}