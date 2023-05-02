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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Q46{
    public static void main(String[] args) {
        Q46.Solution solution =new Q46().new Solution();
        final List<List<Integer>> res = solution.permute(new int[]{1, 2, 3});
        System.out.println(res);
    }
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        backtrace(nums,new HashSet<>(),res, new ArrayList<>());
        return res;
    }

    private void backtrace(int[] nums, Set<Integer> used, List<List<Integer>> result, List<Integer> intermediate) {
        if(intermediate.size() == nums.length) {
            result.add(new ArrayList<>(intermediate));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if(used.contains(nums[i])) continue;
            intermediate.add(nums[i]);
            used.add(nums[i]);
            backtrace(nums, used, result, intermediate);
            intermediate.remove(intermediate.size()-1);
            used.remove(nums[i]);
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
}