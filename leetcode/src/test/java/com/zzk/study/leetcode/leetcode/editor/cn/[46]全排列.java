//给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,2,3]
//输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
// 
//
// 示例 2： 
//
// 
//输入：nums = [0,1]
//输出：[[0,1],[1,0]]
// 
//
// 示例 3： 
//
// 
//输入：nums = [1]
//输出：[[1]]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 6 
// -10 <= nums[i] <= 10 
// nums 中的所有整数 互不相同 
// 
//
// Related Topics 数组 回溯 👍 2408 👎 0

import java.util.*;

class Q46 {

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> intermediate = new ArrayList<>();

        public List<List<Integer>> permute(int[] nums) {
            dfs(nums, 0);
            return res;
        }

        private void dfs(int[] nums, int index) {
            if (index == nums.length)
                res.add(new ArrayList<>(intermediate));

            for (int i = index; i < nums.length; i++) {
                swap(nums, index, i);
                intermediate.add(nums[index]);
                dfs(nums, index + 1);
                intermediate.remove(intermediate.size() - 1);
                swap(nums, i, index);
            }
        }

        private void swap(int[] nums, int i, int j) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }

    }

    //leetcode submit region end(Prohibit modification and deletion)
    class SolutionBacktrack {
        public List<List<Integer>> permute(int[] nums) {
            List<List<Integer>> res = new ArrayList<>();
            backtrack(nums, new ArrayList<>(), res);
            return res;
        }

        private List<List<Integer>> backtrack(int[] nums, List<Integer> intermediate, List<List<Integer>> res) {
            if (intermediate.size() == nums.length) {
                res.add(new ArrayList<>(intermediate));
                return res;
            }
            for (int i = 0; i < nums.length; i++) {
                if (intermediate.contains(nums[i])) continue;
                intermediate.add(nums[i]);
                backtrack(nums, intermediate, res);
                intermediate.remove(intermediate.size() - 1);
            }
            return res;
        }
    }

    public static void main(String[] args) {
        Q46.Solution solution = new Q46().new Solution();
        final List<List<Integer>> res = solution.permute(new int[]{1, 2, 3});
        System.out.println(res);
    }
}