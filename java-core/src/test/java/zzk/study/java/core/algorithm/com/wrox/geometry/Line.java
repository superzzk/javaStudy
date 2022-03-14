package zzk.study.java.core.algorithm.com.wrox.geometry;
public class Line {
    private final Point _p;
    private final Point _q;
    private final Slope _slope;
    public Line(Point p, Point q) {
        assert p != null : "point defining a line cannot be null";
        assert q != null : "point defining a line cannot be null";
        _p = p;
        _q = q;
        _slope = new Slope(_p.getY() - _q.getY(), _p.getX() - _q.getX());
    }
    public boolean isParallelTo(Line line) {
        return _slope.equals(line._slope);
    }
    public boolean contains(Point a) {
        if (!isWithin(a.getX(), _p.getX(), _q.getX())) {
            return false;
        }
        if (!isWithin(a.getY(), _p.getY(), _q.getY())) {
            return false;
        }
        if (_slope.isVertical()) {
            return true;
        }
        return a.getY() == solveY(a.getX());
    }
    private double solveY(double x) {
        return _slope.asDouble() * x + base();
    }
    private double base() {
        return _p.getY() - _slope.asDouble() * _p.getX();
    }
    private static boolean isWithin(double test, double bound1, double bound2) {
        return test >= Math.min(bound1, bound2)
                && test <= Math.max(bound1, bound2);
    }
    public Point intersectionPoint(Line line) {
        if (isParallelTo(line)) {
            return null;
        }
        double x = getIntersectionXCoordinate(line);
        double y = getIntersectionYCoordinate(line, x);
        Point p = new Point(x, y);
        if (line.contains(p) && this.contains(p)) {
            return p;
        }
        return null;
    }
    private double getIntersectionXCoordinate(Line line) {
        if (_slope.isVertical()) {
            return _p.getX();
        }
        if (line._slope.isVertical()) {

            return line._p.getX();
        }
        double m = _slope.asDouble();
        double b = base();
        double n = line._slope.asDouble();
        double c = line.base();
        return (c - b) / (m - n);
    }
    private double getIntersectionYCoordinate(Line line, double x) {
        if (_slope.isVertical()) {
            return line.solveY(x);
        }
        return solveY(x);
    }
}