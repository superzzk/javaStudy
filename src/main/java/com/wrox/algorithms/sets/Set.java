package com.wrox.algorithms.sets;
import com.wrox.algorithms.iteration.Iterable;
public interface Set extends Iterable {
    public boolean add(Object value);
    public boolean delete(Object value);
    public boolean contains(Object value);
    public void clear();
    public int size();
    public boolean isEmpty();
}