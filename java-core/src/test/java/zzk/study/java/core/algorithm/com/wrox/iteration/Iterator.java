package zzk.study.java.core.algorithm.com.wrox.iteration;

public interface Iterator {
    public void first();
    public void last();
    public boolean isDone();
    public void next();
    public void previous();
    public Object current() throws IteratorOutOfBoundsException;
}