package com.wrox.algorithms.lists;

import com.wrox.algorithms.iteration.Iterator;

public class EmptyList implements List {

    public static EmptyList INSTANCE = new EmptyList();

    private EmptyList(){

    }
    @Override
    public void insert(int index, Object value) throws IndexOutOfBoundsException {

    }

    @Override
    public void add(Object value) {

    }

    @Override
    public Object delete(int index) throws IndexOutOfBoundsException {
        return null;
    }

    @Override
    public boolean delete(Object value) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public Object set(int index, Object value) throws IndexOutOfBoundsException {
        return null;
    }

    @Override
    public Object get(int index) throws IndexOutOfBoundsException {
        return null;
    }

    @Override
    public int indexOf(Object value) {
        return 0;
    }

    @Override
    public boolean contains(Object value) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Iterator iterator() {
        return null;
    }
}
