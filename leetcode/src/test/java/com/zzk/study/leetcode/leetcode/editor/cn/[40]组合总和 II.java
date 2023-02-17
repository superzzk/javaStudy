package com.zzk.study.leetcode.leetcode.editor.cn;//给定一个候选人编号的集合 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
//
// candidates 中的每个数字在每个组合中只能使用 一次 。 
//
// 注意：解集不能包含重复的组合。 
//
// 
//
// 示例 1: 
//
// 
//输入: candidates = [10,1,2,7,6,1,5], target = 8,
//输出:
//[
//[1,1,6],
//[1,2,5],
//[1,7],
//[2,6]
//] 
//
// 示例 2: 
//
// 
//输入: candidates = [2,5,2,1,2], target = 5,
//输出:
//[
//[1,2,2],
//[5]
//] 
//
// 
//
// 提示: 
//
// 
// 1 <= candidates.length <= 100 
// 1 <= candidates[i] <= 50 
// 1 <= target <= 30 
// 
//
// Related Topics 数组 回溯 👍 1174 👎 0


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class CombinationSum2 {
    public static void main(String[] args) {
        CombinationSum2.Solution solution = new CombinationSum2().new Solution();
        List<List<Integer>> res = solution.combinationSum2(new int[]{10,1,2,7,6,1,5}, 8);
        System.out.println(res);

        solution.res.clear();
        res = solution.combinationSum2(new int[]{2,5,2,1,2}, 5);
        System.out.println(res);
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        List<List<Integer>> res = new ArrayList<>();
        public List<List<Integer>> combinationSum2(int[] candidates, int target) {
            Arrays.sort(candidates);
            recur(candidates, target, new ArrayList<>(),0);
            return res;
        }

        private void recur(int[] candidates, int target, List<Integer> temp, int index) {
            if(target<0) return;
            if (target == 0) {
                res.add(new ArrayList<>(temp));
                return;
            }

            for(int i = index; i<candidates.length; i++){
                if(i > index && candidates[i]==candidates[i-1]) continue;
                temp.add(candidates[i]);
                recur(candidates, target - candidates[i], temp, i+1);
                temp.remove(temp.size() - 1);
            }
        }
    }
//leetcode submit region end(Prohibit modification and deletion)
}