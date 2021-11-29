package com.zzk.study.leetcode.leetcode.editor.cn;
//给定一个整数数组 nums 和一个正整数 k，找出是否有可能把这个数组分成 k 个非空子集，其总和都相等。 
//
// 示例 1： 
//
// 输入： nums = [4, 3, 2, 3, 5, 2, 1], k = 4
//输出： True
//说明： 有可能将其分成 4 个子集（5），（1,4），（2,3），（2,3）等于总和。 
//
// 
//
// 提示： 
//
// 
// 1 <= k <= len(nums) <= 16 
// 0 < nums[i] < 10000 
// 
// Related Topics 递归 动态规划 
// 👍 262 👎 0

import java.util.Arrays;
import java.util.stream.IntStream;

class PartitionToKEqualSumSubsets {
    public static void main(String[] args) {
        Solution solution = new PartitionToKEqualSumSubsets().new Solution();
        boolean b = solution.canPartitionKSubsets(new int[]{4, 3, 2, 3, 5, 2, 1}, 4);
        System.out.println(b);

        System.out.println(solution.canPartitionKSubsets(new int[]{10, 10, 10, 7, 7, 7, 7, 7, 7, 6, 6, 6}, 3));
        System.out.println(solution.canPartitionKSubsets(
                new int[]{3522,181,521,515,304,123,2512,312,922,407,146,1932,4037,2646,3871,269},5));

    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {

        public boolean canPartitionKSubsets(int[] nums, int k) {
            int sum = IntStream.of(nums).sum();
            if (sum % k != 0) return false;
            int target = sum / k;
            int[] group = new int[k];

            return can(nums, group, target, 0);
        }

        private boolean can(int[] nums, int[] group, int target, int start) {
            if(check(group,target))
                return true;

            for(int i=start; i< nums.length; i++){
                for (int j = 0; j < group.length; j++) {
                    if(group[j]+nums[i]<=target){
                        group[j] += nums[i];
                        if(can(nums, group, target, i + 1))
                            return true;
                        group[j] -= nums[i];
                    }
                }
            }

            return false;
        }

        private boolean check(int[] group, int target) {
            if (IntStream.of(group).anyMatch(i -> i != target)) {
                return false;
            }
            return true;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}