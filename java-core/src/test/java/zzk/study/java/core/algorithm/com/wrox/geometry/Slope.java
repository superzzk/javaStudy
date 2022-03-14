package zzk.study.java.core.algorithm.com.wrox.geometry;
public class Slope {
    private final double _rise;
    private final double _travel;
    public Slope(double rise, double travel) {
        _rise = rise;
        _travel = travel;
    }
    public boolean isVertical() {
        return _travel == 0;
    }
    public int hashCode() {
        return (int) (_rise * _travel);
    }
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || object.getClass() != getClass()) {
            return false;
        }
        Slope other = (Slope) object;
        if (isVertical() && other.isVertical()) {
            return true;
        }
        if (isVertical() || other.isVertical()) {

            return false;
        }
        return (asDouble()) == (other.asDouble());
    }
    public double asDouble() {
        if (isVertical()) {
            throw new IllegalStateException("Vertical slope cannot be represented as double");
        }
        return _rise / _travel;
    }
}