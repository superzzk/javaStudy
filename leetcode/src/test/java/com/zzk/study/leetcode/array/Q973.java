package com.zzk.study.leetcode.array;

import org.junit.Test;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 973. 最接近原点的 K 个点
 * 我们有一个由平面上的点组成的列表 points。需要从中找出 K 个距离原点 (0, 0) 最近的点。
 *
 * （这里，平面上两点之间的距离是欧几里德距离。）
 *
 * 你可以按任何顺序返回答案。除了点坐标的顺序之外，答案确保是唯一的。
 *
 * 示例 1：
 *
 * 输入：points = [[1,3],[-2,2]], K = 1
 * 输出：[[-2,2]]
 * 解释：
 * (1, 3) 和原点之间的距离为 sqrt(10)，
 * (-2, 2) 和原点之间的距离为 sqrt(8)，
 * 由于 sqrt(8) < sqrt(10)，(-2, 2) 离原点更近。
 * 我们只需要距离原点最近的 K = 1 个点，所以答案就是 [[-2,2]]。
 *
 * 示例 2：
 *
 * 输入：points = [[3,3],[5,-1],[-2,4]], K = 2
 * 输出：[[3,3],[-2,4]]
 * （答案 [[-2,4],[3,3]] 也会被接受。）
 *
 * 提示：
 *
 *     1 <= K <= points.length <= 10000
 *     -10000 < points[i][0] < 10000
 *     -10000 < points[i][1] < 10000
 *
 **/
public class Q973 {
    /**
     * [[3,3],[5,-1],[-2,4]]
     * 2
     */
    @Test
    public void test1(){
        int[][] a = {{3,3},{5,-1},{-2,4}};
        int[][] rt = kClosest(a, 2);
        for (int[] pair:rt)
            System.out.println(Arrays.toString(pair));
    }
    public int[][] kClosest(int[][] points, int K) {
        Queue<Item> queue = new PriorityQueue<>(K,(a,b)-> b.distance-a.distance);
        int[][] result = new int[K][2];

        for (int[] pair:points) {
            int x = pair[0];
            int y = pair[1];
            if(queue.size()<K){
                queue.offer(new Item(x, y));
            }else{
                if(distance(x,y)<queue.peek().distance){
                    queue.poll();
                    queue.offer(new Item(x, y));
                }
            }
        }
        int i=0;
        for(Item item : queue){
            result[i][0] = item.x;
            result[i][1] = item.y;
            i++;
        }
        return result;
    }

    private int distance(int x, int y){
        return Math.abs(x*x) +  Math.abs(y*y);
    }

    class Item{
        public int x;
        public int y;
        public int distance;
        Item(int x,int y){this.x=x;this.y=y;this.distance=distance(x,y);}
    }
}
