package com.zzk.study.leetcode.leetcode.editor.en;//Given an array nums of distinct integers, return all the possible
//permutations. You can return the answer in any order. 
//
// 
// Example 1: 
// Input: nums = [1,2,3]
// Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
//
// Example 2: 
// Input: nums = [0,1]
// Output: [[0,1],[1,0]]
//
// Example 3: 
// Input: nums = [1]
// Output: [[1]]
//
// Constraints: 
//
// 1 <= nums.length <= 6
// -10 <= nums[i] <= 10 
// All the integers of nums are unique. 
// 
// Related Topics Array Backtracking 👍 13024 👎 220


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Q46 {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();

        List<Integer> numbers = new ArrayList<Integer>();
        for (int num : nums) {
            numbers.add(num);
        }

        int n = nums.length;
        backtrack(n, numbers, res, 0);
        return res;
    }

    public void backtrack(int len, List<Integer> numbers, List<List<Integer>> res, int depth) {
        // 所有数都填完了
        if (depth == len) {
            res.add(new ArrayList<Integer>(numbers));
        }
        for (int i = depth; i < len; i++) {
            // 动态维护数组
            Collections.swap(numbers, depth, i);
            // 继续递归填下一个数
            backtrack(len, numbers, res, depth + 1);
            // 撤销操作
            Collections.swap(numbers, depth, i);
        }
    }

    // 解法二， 更通用的深度遍历模式，几个关键词：depth, used(状态变量), path
    private void dfs(int len, List<Integer> numbers, List<Integer> path, List<List<Integer>> res, int depth, boolean[] used) {
        if(depth == len){
            res.add(new ArrayList<>(path));
            return;
        }
        for(int i=0; i<len; i++){
            if(used[i]) continue;
            path.add(numbers.get(i));
            used[i] = true;
            dfs(len, numbers, path, res, depth + 1, used);
            path.remove(path.size() - 1);
            used[i] = false;
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
