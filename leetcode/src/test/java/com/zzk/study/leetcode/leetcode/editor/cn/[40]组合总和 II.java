package com.zzk.study.leetcode.leetcode.editor.cn;
//给定一个数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。 
//
// candidates 中的每个数字在每个组合中只能使用一次。 
//
// 说明： 
//
// 
// 所有数字（包括目标数）都是正整数。 
// 解集不能包含重复的组合。 
// 
//
// 示例 1: 
//
// 输入: candidates = [10,1,2,7,6,1,5], target = 8,
//所求解集为:
//[
//  [1, 7],
//  [1, 2, 5],
//  [2, 6],
//  [1, 1, 6]
//]
// 
//
// 示例 2: 
//
// 输入: candidates = [2,5,2,1,2], target = 5,
//所求解集为:
//[
//  [1,2,2],
//  [5]
//] 
// Related Topics 数组 回溯算法 
// 👍 427 👎 0

import java.util.*;

class CombinationSumIi {
    public static void main(String[] args) {
        Solution solution = new CombinationSumIi().new Solution();
        List<List<Integer>> res = solution.combinationSum2(new int[]{10, 1, 2, 7, 6, 1, 5}, 8);
        System.out.println(res);

        solution.res.clear();
        res = solution.combinationSum2(new int[]{2,5,2,1,2}, 5);
        System.out.println(res);
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public Set<List<Integer>> res = new TreeSet<List<Integer>>((x, y)->{
            if(x.size()!=y.size())
                return x.size() - y.size();
            for(int i=0;i<x.size();i++){
                if(!x.get(i).equals(y.get(i)))
                    return x.get(i) - y.get(i);
            }
            return 0;
        });

        public List<List<Integer>> combinationSum2(int[] candidates, int target) {
            combine(candidates, target, 0, new ArrayList<>());
            return new ArrayList<>(res);
        }

        private void combine(int[] candidates, int target, int start, List<Integer> temp) {
            if(target<0)
                return;
            if (target == 0) {
                ArrayList<Integer> t = new ArrayList<>(temp);
                Collections.sort(t);
                res.add(t);
                return;
            }

            for (int i = start; i < candidates.length; i++){
                temp.add(candidates[i]);
                combine(candidates, target - candidates[i], i + 1, temp);
                temp.remove(temp.size() - 1);
            }
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}