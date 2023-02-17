//给定一个非负整数 numRows，生成「杨辉三角」的前 numRows 行。 
//
// 在「杨辉三角」中，每个数是它左上方和右上方的数的和。 
//
// 
//
// 
//
// 示例 1: 
//
// 
//输入: numRows = 5
//输出: [[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]
// 
//
// 示例 2: 
//
// 
//输入: numRows = 1
//输出: [[1]]
// 
//
// 
//
// 提示: 
//
// 
// 1 <= numRows <= 30 
// 
//
// Related Topics 数组 动态规划 👍 877 👎 0


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class GenerateYangHuiTriangle {
    public static void main(String[] args) {
        GenerateYangHuiTriangle.Solution solution = new GenerateYangHuiTriangle().new Solution();
        final List<List<Integer>> res = solution.generate(5);
        System.out.println(res);
    }
    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public List<List<Integer>> generate(int numRows) {
            int buf[][] = new int[numRows][numRows];
            buf[0][0] = 1;

            for (int row = 1; row < numRows; row++) {
                for (int col = 0; col < row+1; col++) {
                    if(col==0 || col == row) {
                        buf[row][col] = 1;
                        continue;
                    }
                    int a = buf[row - 1][col - 1];
                    int b = buf[row - 1][col];
                    buf[row][col] = a + b;
                }
            }
            for (int[] row : buf) {
                for (int n : row) {
                    System.out.print(n);
                }
                System.out.println();
            }
            List<List<Integer>> res = new ArrayList<>();
            for (int row = 0; row < buf.length; row++) {
                List<Integer> l = new ArrayList<>();
                for (int i = 0; i < row+1; i++) {
                    l.add(buf[row][i]);
                }
                res.add(l);
            }
            return res;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)
}