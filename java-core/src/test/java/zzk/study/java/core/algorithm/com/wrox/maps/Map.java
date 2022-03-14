package zzk.study.java.core.algorithm.com.wrox.maps;
import zzk.study.java.core.algorithm.com.wrox.iteration.Iterable;

public interface Map extends Iterable {
    public Object get(Object key);
    public Object set(Object key, Object value);
    public Object delete(Object key);
    public boolean contains(Object key);
    public void clear();
    public int size();
    public boolean isEmpty();
    public static interface Entry {
        public Object getKey();
        public Object getValue();
    }
}