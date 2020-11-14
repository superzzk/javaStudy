package leetcode.leetcode.editor.cn;
//给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。 
//
// candidates 中的数字可以无限制重复被选取。 
//
// 说明： 
//
// 
// 所有数字（包括 target）都是正整数。 
// 解集不能包含重复的组合。 
// 
//
// 示例 1： 
//
// 输入：candidates = [2,3,6,7], target = 7,
//所求解集为：
//[
//  [7],
//  [2,2,3]
//]
// 
//
// 示例 2： 
//
// 输入：candidates = [2,3,5], target = 8,
//所求解集为：
//[
//  [2,2,2,2],
//  [2,3,3],
//  [3,5]
//] 
//
// 
//
// 提示： 
//
// 
// 1 <= candidates.length <= 30 
// 1 <= candidates[i] <= 200 
// candidate 中的每个元素都是独一无二的。 
// 1 <= target <= 500 
// 
// Related Topics 数组 回溯算法 
// 👍 1014 👎 0

import java.util.*;

class CombinationSum {
    public static void main(String[] args) {
        Solution solution = new CombinationSum().new Solution();
        List<List<Integer>> res = solution.combinationSum(new int[]{2, 3, 6, 7}, 7);
        System.out.println(res);

        solution.res.clear();
        res = solution.combinationSum(new int[]{2, 3, 5}, 8);
        System.out.println(res);
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public Set<List<Integer>> res = new TreeSet<List<Integer>>((x, y)->{
            if(x.size()!=y.size())
                return x.size() - y.size();
            for (int i = 0; i < x.size(); i++) {
                if (!x.get(i).equals(y.get(i))) {
                    return x.get(i) - y.get(i);
                }
            }
            return 0;
        });
        public List<List<Integer>> combinationSum(int[] candidates, int target) {
            combine(candidates, target, new ArrayList<>(), 0);
            return new ArrayList<>(res);
        }

        private void combine(int[] candidates, int target, List<Integer> temp, int start) {
            if(target<0)
                return;
            if (target == 0) {
                List<Integer> t = new ArrayList<>(temp);
                Collections.sort(t);
                res.add(t);
            }
            for (int i = start; i < candidates.length; i++) {
                temp.add(candidates[i]);
                combine(candidates, target - candidates[i], temp, start);
                temp.remove(temp.size() - 1);
            }
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}