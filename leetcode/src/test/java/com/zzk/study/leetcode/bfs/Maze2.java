package com.zzk.study.leetcode.bfs;

import org.junit.Test;

import java.util.Stack;

/**
 * https://www.cnblogs.com/wanghang-learning/p/9430672.html
 * @author zhangzhongkun
 * @since 2019-10-22 17:00
 **/
public class Maze2 {
    private int[][] map = {                           //迷宫地图,1代表墙壁，0代表通路
            {1,1,1,1,1,1,1,1,1,1},
            {1,0,0,1,0,0,0,1,0,1},
            {1,0,0,1,0,0,0,1,0,1},
            {1,0,0,0,0,1,1,0,0,1},
            {1,0,1,1,1,0,0,0,0,1},
            {1,0,0,0,1,0,0,0,0,1},
            {1,0,1,0,0,0,1,0,0,1},
            {1,0,1,1,1,0,1,1,0,1},
            {1,1,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1}
    };

    //内部类，封装一个点
    public class Dot{
        private int x;            //行标
        private int y;            //列标

        public Dot(int x , int y) {this.x = x;this.y = y;}
        public int getX(){return x;}
        public int getY(){return y;}
    }

    //内部类，封装走过的每一个点,自带方向
    public class Block extends Dot{
        private int dir;          //方向,1向上，2向右，3向下，4向左
        public Block(int x , int y) {
            super(x , y);
            dir = 1;
        }

        public int getDir(){return dir;}
        public void changeDir(){dir++;}
    }

    /*
        广度优先遍历用到的数据结构，它需要一个指向父节点的索引
    */
    public class WideBlock extends Dot{
        private WideBlock parent;

        public WideBlock(int x , int y , WideBlock p){
            super(x , y);
            parent = p;
        }

        public WideBlock getParent(){return parent;}
    }

    /**
     * 1 回溯法
     * 思路：从每一个位置出发，下一步都有四种选择（上右下左），
     * 先选择一个方向，如果该方向能够走下去，那么就往这个方向走，当前位置切换为下一个位置。
     * 如果不能走，那么换个方向走，如果所有方向都走不了，那么退出当前位置，
     * 到上一步的位置去，当前位置切换为上一步的位置。
     * 一直这样执行下去，如果当前位置是终点，那么结束。
     * 如果走过了所有的路径都没能到达终点，那么无解。
     */
    @Test
    public void test_findPath1(){
        int[][] map = {                           //迷宫地图,1代表墙壁，0代表通路
                {0,1,1},
                {0,0,0},
                {1,1,0},
        };
        Stack<Block> stack  = findPath1(map);
        printStack(stack);
    }

    public Stack<Block> findPath1(int[][] map){
        Stack<Block> stack = new Stack<>();
        int mapX = map.length - 1;                //地图xy边界
        int mapY = map[0].length - 1;

        Block b = new Block(0,0);
        stack.push(b);                                //起点进栈
        while(!stack.empty()){                        //栈空代表所有路径已走完，没有找到通路
            Block t = stack.peek();

            int x = t.getX();                         //获取栈顶元素的x
            int y = t.getY();                         //获取栈顶元素的y
            int dir = t.getDir();                     //获取栈顶元素的下一步方向

            map[x][y] = 1;                            //把地图上对应的位置标记为1表示是当前路径上的位置，防止往回走

            if(t.getX() == mapX && t.getY() == mapY) //已达终点
                return stack;

            switch(dir){
                case 1://向上
                    if(x - 1 >= 0 && map[x - 1][y] == 0)   //判断向上走一步是否出界&&判断向上走一步的那个位置是否可达
                        stack.push(new Block(x - 1 , y));   //记录该位置
                    t.changeDir();                          //改变方向，当前方向已走过
                    continue;                               //进入下一轮循环
                case 2://向右
                    if(y + 1 <= mapY && map[x][y+1] == 0)
                        stack.push(new Block(x , y + 1));
                    t.changeDir();
                    continue;
                case 3://向下
                    if(x + 1 <= mapX && map[x+1][y] == 0)
                        stack.push(new Block(x + 1 , y));
                    t.changeDir();
                    continue;
                case 4:
                    if(y - 1 >= 0 && map[x][y - 1] == 0)
                        stack.push(new Block(x , y - 1));
                    t.changeDir();
                    continue;
            }
            t = stack.pop();                //dir > 4 当前Block节点已经没有方向可走,出栈
            map[t.getX()][t.getY()] = 0;    //出栈元素对应的位置已经不再当前路径上，表示可达
        }
        return stack;
    }

    //打印栈
    public void printStack(Stack<Block> stack){
        int count = 1;
        while(!stack.empty()){
            Block b = stack.pop();
            System.out.print("(" + b.getX() + "," + b.getY() + ") ");
            if(count % 10 == 0)
                System.out.println("");
            count++;
        }
    }

    @Test
    public void test_findPath2(){
        int[][] map = {                           //迷宫地图,1代表墙壁，0代表通路
                {0,1,1},
                {0,0,0},
                {1,1,0},
        };
        Stack<Block> s= findPath2(map,0,0);
        printStack(s);
    }

    public Stack<Block> findPath2(int[][] map, int startX, int startY){
        int mapX = map.length - 1;                //地图xy边界
        int mapY = map[0].length - 1;
        Stack<Block> result = new Stack<>();
        if(startX >= 0 && startX <= mapX && startY >= 0 && startY <= mapY && map[startX][startY] == 0){
            find(map, startX , startY, result);
        }
        return result;
    }
    private void find(int[][] map, int x , int y, Stack<Block> result){
        int mapX = map.length - 1;                //地图xy边界
        int mapY = map[0].length - 1;
        Stack<Block> stack = new Stack<>();
        map[x][y] = 1;
        if(x == mapX && y == mapY) {
            stack.push(new Block(x , y));
            while(!stack.empty()){
                result.push(stack.pop());
            }
            //return ;
            //在此处返回会使后续递归再次寻找路线会经过这里，如果不返回，整个函数执行完毕，所有路径都会被访问到
        }
        stack.push(new Block(x , y));
        if( x - 1 >= 0 && map[x - 1][y] == 0 )  //可以往上走，那么往上走
            find(map,x - 1 , y, result);
        if(x + 1 <= mapX && map[x + 1][y] == 0) //可以往下走，那么往下走
            find(map,x + 1 , y, result);
        if(y - 1 >= 0 && map[x][y - 1] ==0)     //往左
            find(map, x , y - 1,result);
        if(y + 1 <= mapY && map[x][y + 1] == 0)
            find(map, x , y + 1, result);
        if(!stack.empty())
            stack.pop();

    }
}
