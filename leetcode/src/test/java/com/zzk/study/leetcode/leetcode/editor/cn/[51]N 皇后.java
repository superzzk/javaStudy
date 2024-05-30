package com.zzk.study.leetcode.leetcode.editor.cn;//按照国际象棋的规则，皇后可以攻击与之处在同一行或同一列或同一斜线上的棋子。
//
// n 皇后问题 研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。 
//
// 给你一个整数 n ，返回所有不同的 n 皇后问题 的解决方案。 
//
// 
// 
// 每一种解法包含一个不同的 n 皇后问题 的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。 
// 
// 
//
// 
//
// 示例 1： 
// 
// 
//输入：n = 4
//输出：[[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
//解释：如上图所示，4 皇后问题存在两个不同的解法。
// 
//
// 示例 2： 
//
// 
//输入：n = 1
//输出：[["Q"]]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= n <= 9 
// 
//
// Related Topics 数组 回溯 👍 2076 👎 0


import java.util.*;

class Q51 {
    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        boolean[] usedRows;
        boolean[] usedCols;
        Set<Integer> sum = new HashSet<>();
        Set<Integer> difference = new HashSet<>();

        public List<List<String>> solveNQueens(int n) {
            List<List<String>> res = new ArrayList<>();
            usedRows = new boolean[n];
            usedCols = new boolean[n];

            char[][] board = new char[n][n];
            for (char[] row : board) {
                Arrays.fill(row, '.');
            }
            dfs(board, 0, res);
            return res;
        }

        public void dfs(char[][] board, int row, List<List<String>> res) {
            if (row == board.length) {
                res.add(construct(board));
                return;
            }
            for (int col = 0; col < board.length; col++) {
                if (valid(board, row, col)) {
                    board[row][col] = 'Q';
                    usedRows[row] = true;
                    usedCols[col] = true;
                    sum.add(row + col);
                    difference.add(row - col);
                    dfs(board, row + 1, res);
                    board[row][col] = '.';
                    usedRows[row] = false;
                    usedCols[col] = false;
                    sum.remove(row + col);
                    difference.remove(row - col);
                }
            }
        }

        public boolean valid(char[][] board, int row, int col) {
            if (usedRows[row])
                return false;
            if (usedCols[col])
                return false;
            if(sum.contains(row + col) || difference.contains(row - col))
                return false;
            return true;
        }

        public List<String> construct(char[][] board) {
            List<String> res = new ArrayList<>();
            for (char[] row : board) {
                res.add(new String(row));
            }
            return res;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)
}