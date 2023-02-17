//在一个 8 x 8 的棋盘上，有一个白色的车（Rook），用字符 'R' 表示。棋盘上还可能存在空方块，白色的象（Bishop）以及黑色的卒（pawn），分
//别用字符 '.'，'B' 和 'p' 表示。不难看出，大写字符表示的是白棋，小写字符表示的是黑棋。 
//
// 车按国际象棋中的规则移动。东，西，南，北四个基本方向任选其一，然后一直向选定的方向移动，直到满足下列四个条件之一： 
//
// 
// 棋手选择主动停下来。 
// 棋子因到达棋盘的边缘而停下。 
// 棋子移动到某一方格来捕获位于该方格上敌方（黑色）的卒，停在该方格内。 
// 车不能进入/越过已经放有其他友方棋子（白色的象）的方格，停在友方棋子前。 
// 
//
// 你现在可以控制车移动一次，请你统计有多少敌方的卒处于你的捕获范围内（即，可以被一步捕获的棋子数）。 
//
// 
//
// 示例 1： 
//
// 
//
// 输入：[[".",".",".",".",".",".",".","."],[".",".",".","p",".",".",".","."],[".",
//".",".","R",".",".",".","p"],[".",".",".",".",".",".",".","."],[".",".",".",".",
//".",".",".","."],[".",".",".","p",".",".",".","."],[".",".",".",".",".",".",".",
//"."],[".",".",".",".",".",".",".","."]]
//输出：3
//解释：
//在本例中，车能够捕获所有的卒。
// 
//
// 示例 2： 
//
// 
//
// 输入：[[".",".",".",".",".",".",".","."],[".","p","p","p","p","p",".","."],[".",
//"p","p","B","p","p",".","."],[".","p","B","R","B","p",".","."],[".","p","p","B",
//"p","p",".","."],[".","p","p","p","p","p",".","."],[".",".",".",".",".",".",".",
//"."],[".",".",".",".",".",".",".","."]]
//输出：0
//解释：
//象阻止了车捕获任何卒。
// 
//
// 示例 3： 
//
// 
//
// 输入：[[".",".",".",".",".",".",".","."],[".",".",".","p",".",".",".","."],[".",
//".",".","p",".",".",".","."],["p","p",".","R",".","p","B","."],[".",".",".",".",
//".",".",".","."],[".",".",".","B",".",".",".","."],[".",".",".","p",".",".",".",
//"."],[".",".",".",".",".",".",".","."]]
//输出：3
//解释： 
//车可以捕获位置 b5，d6 和 f5 的卒。
// 
//
// 
//
// 提示： 
//
// 
// board.length == board[i].length == 8 
// board[i][j] 可以是 'R'，'.'，'B' 或 'p' 
// 只有一个格子上存在 board[i][j] == 'R' 
// 
//
// Related Topics 数组 矩阵 模拟 👍 104 👎 0

import java.util.Arrays;

class NumRookCaptures {
    public static void main(String[] args) {
        char[][] input = {{'.','.','.','.','.','.','p','.'},{'p','.','.','.','.','.','R','.'},{'.','.','.','.','.','.','.','.'},{'.','.','.','.','.','.','.','.'},{'.','.','.','.','.','.','.','.'},{'.','.','.','.','.','.','.','.'},{'.','.','.','.','.','.','.','.'},{'.','.','.','.','.','.','p','.'}};
        print(input);
        NumRookCaptures.Solution solution = new NumRookCaptures().new Solution();
        final int res = solution.numRookCaptures(input);
        System.out.println(res); // 应该是3
    }

    private static void print(char[][] input) {
        for (char[] row : input) {
            for (char c : row) {
                System.out.print(c+" ");
            }
            System.out.println();
        }
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        char[][] board;
        int cnt = 0;
        public int numRookCaptures(char[][] board) {
            this.board = board;
            final int[] rook = findRook(board);
            System.out.println(Arrays.toString(rook));
            int rookRow = rook[0];
            int rookCol = rook[1];

            for (int i = rookRow - 1; i >= 0; i--) {
                if (process(i, rookCol)) break;
            }
            for (int i = rookRow+1 ; i < 8; i++) {
                if (process(i, rookCol)) break;
            }
            for (int i = rookCol - 1; i >= 0; i--) {
                if (process(rookRow, i)) break;
            }
            for (int i = rookCol + 1; i < 8; i++) {
                if (process(rookRow, i)) break;
            }
            return cnt;
        }

        private boolean process(int row, int col) {
            if (board[row][col] == 'B') return true;
            if (board[row][col] == 'p') {
                cnt++;
                return true;
            }
            return false;
        }

        private int[] findRook(char[][] board) {
            for (int r = 0; r < 8; r++) {
                for (int c = 0; c < 8; c++) {
                    if (board[r][c] == 'R')
                        return new int[]{r, c};
                }
            }
            return null;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)
}