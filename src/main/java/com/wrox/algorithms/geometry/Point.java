package com.wrox.algorithms.geometry;

public class Point {
    private final double _x;
    private final double _y;
    public Point(double x, double y) {
        _x = x;
        _y = y;
    }
    public double getX() {
        return _x;
    }
    public double getY() {
        return _y;
    }
    public double distance(Point other) {
        assert other != null : "other can’t be null";
        double rise = getY() - other.getY();
        double travel = getX() - other.getX();
        return Math.sqrt(rise * rise + travel * travel);
    }
    public int hashCode() {
        return (int) (_x * _y);
    }
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        Point other = (Point) obj;
        return getX() == other.getX() && getY() == other.getY();
    }
}