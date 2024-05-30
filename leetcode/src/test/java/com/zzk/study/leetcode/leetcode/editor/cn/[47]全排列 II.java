//给定一个可包含重复数字的序列 nums ，按任意顺序 返回所有不重复的全排列。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,1,2]
//输出：
//[[1,1,2],
// [1,2,1],
// [2,1,1]]
// 
//
// 示例 2： 
//
// 
//输入：nums = [1,2,3]
//输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 8 
// -10 <= nums[i] <= 10 
// 
//
// Related Topics 数组 回溯 👍 1566 👎 0


import java.util.*;
class Q47 {
    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        List<List<Integer>> res = new ArrayList<>();
        boolean[] vis;

        public List<List<Integer>> permuteUnique(int[] nums) {
            vis = new boolean[nums.length];
            Arrays.sort(nums);
            backtrack(nums, new ArrayList<>());
            return res;
        }

        private void backtrack(int[] nums, List<Integer> intermediate) {
            if (intermediate.size() == nums.length) {
                res.add(new ArrayList<>(intermediate));
                return;
            }
            for (int i = 0; i < nums.length; i++) {

                if (vis[i] || i > 0 && nums[i] == nums[i - 1] && !vis[i - 1])
                    continue;

                vis[i] = true;
                intermediate.add(nums[i]);

                backtrack(nums, intermediate);

                vis[i] = false;
                intermediate.remove(intermediate.size() - 1);
            }
        }
    }
//leetcode submit region end(Prohibit modification and deletion)
}