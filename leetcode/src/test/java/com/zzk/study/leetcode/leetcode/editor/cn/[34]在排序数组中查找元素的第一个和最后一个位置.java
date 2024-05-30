//给你一个按照非递减顺序排列的整数数组 nums，和一个目标值 target。请你找出给定目标值在数组中的开始位置和结束位置。 
//
// 如果数组中不存在目标值 target，返回 [-1, -1]。 
//
// 你必须设计并实现时间复杂度为 O(log n) 的算法解决此问题。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [5,7,7,8,8,10], target = 8
//输出：[3,4] 
//
// 示例 2： 
//
// 
//输入：nums = [5,7,7,8,8,10], target = 6
//输出：[-1,-1] 
//
// 示例 3： 
//
// 
//输入：nums = [], target = 0
//输出：[-1,-1] 
//
// 
//
// 提示： 
//
// 
// 0 <= nums.length <= 10⁵ 
// -10⁹ <= nums[i] <= 10⁹ 
// nums 是一个非递减数组 
// -10⁹ <= target <= 10⁹ 
// 
//
// Related Topics 数组 二分查找 👍 2695 👎 0

import java.util.Arrays;

class Q34 {
//leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        int left = Integer.MAX_VALUE;
        int right = Integer.MIN_VALUE;
        public int[] searchRange(int[] nums, int target) {
            if(nums.length == 0)
                return new int[]{-1,-1};
            bsearch(nums, target, 0, nums.length);

            left = left == Integer.MAX_VALUE ? -1 : left;
            right = right == Integer.MIN_VALUE ? -1 : right;
            return new int[]{left, right};
        }

        private void bsearch(int[] nums, int target, int start, int end){
            if(start > end)
                return;
            int mid = start + (end - start)/2;
            if(mid >= nums.length)
                return;
            if(nums[mid] == target){
                left = Math.min(mid, left);
                right = Math.max(mid, right);
                bsearch(nums, target, start, mid-1);
                bsearch(nums, target, mid+1, end);
            }else if(nums[mid] < target){
                bsearch(nums, target, mid+1, end);
            } else {
                bsearch(nums, target, start, mid-1);
            }
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        Solution solution = new Q34().new Solution();
        int[] nums = {1};
        int target = 1;
        int[] res = solution.searchRange(nums, target);
        System.out.println(Arrays.toString(res));
    }
}
