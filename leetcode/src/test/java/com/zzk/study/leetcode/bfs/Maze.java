package com.zzk.study.leetcode.bfs;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 求一个人从迷宫的起点到终点的任意一条最短路径。
 *
 *     迷宫可以用二维数组进行表示，1表示可以行走，0表示不能行走。如下所示
 *
 *          1 0 0 1 1 0
 *
 *          1 1 1 1 0 1
 *
 *          0 1 0 1 1 1
 *
 *          0 0 1 1 0 1
 *
 * ，问题也就是求从左上角到右下角的最短路径。
 **/
public class Maze {
    public static String path="";
    public static Deque<String> queue = new LinkedList<String>();
    public static int m, n; //m表示横坐标，y表示纵坐标

    public static void main(String[] args) {
        int[][] map = {
                {1,0,0,1,1,0},
                {1,1,1,1,0,1},
                {0,1,0,1,1,1},
                {0,0,1,1,0,1}
        };
        m = map.length;
        n = map[0].length;
        bfs(0, 0, map);
        System.out.println(path);
    }

    public static void bfs(int x, int y, int[][] map) {
        if (x < 0 || y < 0 || x >= m || y >= n || map[x][y] == 0) {
            return;
        }

        queue.offerLast("[" + x + "," + y + "]"); //将当前位置放入队列中
        map[x][y] = 0; //并将其设置为已经走过

        if (x == m - 1 && y == n - 1) { //到达终点
            updatePath();
            map[x][y] = 1;
            queue.removeLast();
            return;
        }

        bfs(x, y + 1, map); //上
        bfs(x + 1, y, map); //右
        bfs(x, y - 1, map); //下
        bfs(x - 1, y, map); //左
        map[x][y] = 1;
        queue.removeLast();
    }

    public static void updatePath() {
        StringBuilder sb = new StringBuilder();
        Iterator<String> iterator = queue.iterator();
        while (iterator.hasNext())
            sb.append(iterator.next() + ",");
        if (sb.length() > 0)
            sb.deleteCharAt(sb.length() - 1);
        path = sb.toString();
    }
}
