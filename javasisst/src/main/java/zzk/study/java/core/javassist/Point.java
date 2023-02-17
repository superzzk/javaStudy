package zzk.study.java.core.javassist;

public class Point {
    public int x = 0;
    public int y = 0;

    public Point(){}

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
