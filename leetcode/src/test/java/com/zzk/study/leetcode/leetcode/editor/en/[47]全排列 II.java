package com.zzk.study.leetcode.leetcode.editor.en;//Given a collection of numbers, nums, that might contain duplicates, return
//all possible unique permutations in any order. 关键词：唯一
//
// Example 1: 
//
//Input: nums = [1,1,2]
//Output:
//[[1,1,2],
// [1,2,1],
// [2,1,1]]
// 
// Example 2:
//
//Input: nums = [1,2,3]
//Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
//
// Constraints: 
//
// 1 <= nums.length <= 8
// -10 <= nums[i] <= 10 
// 
// Related Topics Array Backtracking 👍 6496 👎 113


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Q47 {
    boolean[] used;

    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        List<Integer> perm = new ArrayList<Integer>();
        used = new boolean[nums.length];
        Arrays.sort(nums);
        backtrack(nums, ans, 0, perm);
        return ans;
    }

    public void backtrack(int[] nums, List<List<Integer>> ans, int idx, List<Integer> perm) {
        if (idx == nums.length) {
            ans.add(new ArrayList<Integer>(perm));
            return;
        }
        for (int i = 0; i < nums.length; ++i) {
            if (used[i] || (i > 0 && nums[i] == nums[i - 1] && !used[i - 1])) {
                continue;
            }
            perm.add(nums[i]);
            used[i] = true;
            backtrack(nums, ans, idx + 1, perm);
            used[i] = false;
            perm.remove(idx);
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
