package com.zzk.study.leetcode.leetcode.editor.cn;
//给定两个整数 n 和 k，返回 1 ... n 中所有可能的 k 个数的组合。 
//
// 示例: 
//
// 输入: n = 4, k = 2
//输出:
//[
//  [2,4],
//  [3,4],
//  [2,3],
//  [1,2],
//  [1,3],
//  [1,4],
//] 
// Related Topics 回溯算法 
// 👍 419 👎 0

import java.util.ArrayList;
import java.util.List;

class Combinations {

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        List<List<Integer>> res = new ArrayList<>();

        public List<List<Integer>> combine(int n, int k) {
            backtrack(n, k, new ArrayList<>(), 1);
            return res;
        }

        private void backtrack(int max, int number, List<Integer> temp, int start) {
            if (temp.size() == number) {
                res.add(new ArrayList<>(temp));
                return;
            }
            for (int i = start; i <= max; i++) {
                temp.add(i);
                backtrack(max, number, temp, i + 1);
                temp.remove(temp.size() - 1);
            }
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        Solution solution = new Combinations().new Solution();
        List<List<Integer>> list = solution.combine(4, 2);
        System.out.println(list);
    }

    class SolutionDFS {
        List<Integer> temp = new ArrayList<Integer>();
        List<List<Integer>> ans = new ArrayList<List<Integer>>();

        public List<List<Integer>> combine(int n, int k) {
            dfs(1, n, k);
            return ans;
        }

        public void dfs(int cur, int max, int count) {
            // 剪枝：temp 长度加上区间 [cur, max] 的长度小于 count，不可能构造出长度为 count 的 temp
            if (temp.size() + (max - cur + 1) < count) {
                return;
            }
            // 记录合法的答案
            if (temp.size() == count) {
                ans.add(new ArrayList<Integer>(temp));
                return;
            }
            // 考虑选择当前位置
            temp.add(cur);
            dfs(cur + 1, max, count);
            temp.remove(temp.size() - 1);
            // 考虑不选择当前位置
            dfs(cur + 1, max, count);
        }
    }
}