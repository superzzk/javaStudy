package algorithm.fibonacci;

/**
 * @program: javaStudy
 * @description:
 * @author: zhangzhongkun
 * @create: 2019-04-12 13:16
 **/
public class Recursion {
    public static void main(String[] args) {
        int n = 7;
        System.out.println(
                demo1(n)
        );
        System.out.println(
                demo2(n)
        );
        System.out.println(
                demo3(n)
        );

    }

    /**
     * @description: 普通递归算法
     * @create:
     **/
    private static int demo1(int n) {
        if (n <= 0)
            return 0;
        if (n == 1)
            return 1;
        return demo1(n - 1) + demo1(n - 2);
    }

    /**
     * @description: 自顶向下的备忘录法
     * @create:
     **/
    public static int demo2(int n) {
        if (n <= 0) return n;
        int[] Memo = new int[n + 1];
        for (int i = 0; i <= n; i++) Memo[i] = -1;
        return fib(n, Memo);
    }

    public static int fib(int n, int[] Memo) {
        if (Memo[n] != -1)
            return Memo[n]; //如果已经求出了fib（n）的值直接返回，否则将求出的值保存在Memo备忘录中。
        if (n <= 2) Memo[n] = 1;
        else Memo[n] = fib(n - 1, Memo) + fib(n - 2, Memo);
        return Memo[n];
    }

    /**
     * @description: 自底向上的动态规划
     * @create:
     **/
    public static int demo3(int n) {
        if (n <= 0) return n;
        int[] Memo = new int[n + 1];
        Memo[0] = 0;
        Memo[1] = 1;
        for (int i = 2; i <= n; i++) {
            Memo[i] = Memo[i - 1] + Memo[i - 2];
        }
        return Memo[n];
    }
    /** @description: 自底向上的动态规划，压缩存储空间
      * @create:  
      **/
    public static int demo4(int n) {
        if (n <= 1) return n;
        int Memo_i_2 = 0;
        int Memo_i_1 = 1;
        int Memo_i = 1;
        for (int i = 2; i <= n; i++) {
            Memo_i = Memo_i_2 + Memo_i_1;
            Memo_i_2 = Memo_i_1;
            Memo_i_1 = Memo_i;
        }
        return Memo_i;
    }


}
