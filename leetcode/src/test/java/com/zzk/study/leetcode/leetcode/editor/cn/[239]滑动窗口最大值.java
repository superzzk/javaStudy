package com.zzk.study.leetcode.leetcode.editor.cn;
//给定一个数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。 
//
//
// 返回滑动窗口中的最大值。 
//
// 
//
// 进阶： 
//
// 你能在线性时间复杂度内解决此题吗？ 
//
// 
//
// 示例: 
//
// 输入: nums = [1,3,-1,-3,5,3,6,7], 和 k = 3
//输出: [3,3,5,5,6,7] 
//解释: 
//
//  滑动窗口的位置                最大值
//---------------               -----
//[1  3  -1] -3  5  3  6  7       3
// 1 [3  -1  -3] 5  3  6  7       3
// 1  3 [-1  -3  5] 3  6  7       5
// 1  3  -1 [-3  5  3] 6  7       5
// 1  3  -1  -3 [5  3  6] 7       6
// 1  3  -1  -3  5 [3  6  7]      7 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 10^5 
// -10^4 <= nums[i] <= 10^4 
// 1 <= k <= nums.length 
// 
// Related Topics 堆 Sliding Window 
// 👍 595 👎 0

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class SlidingWindowMaximum{
    public static void main(String[] args) {
        Solution solution = new SlidingWindowMaximum().new Solution();
        int[] nums = {1, 3, -1, -3, 5 ,3, 6, 7};
        int[] res = solution.maxSlidingWindow(nums, 3);
        System.out.println(Arrays.toString(res));
    }

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    class Window{
        int index;
        int max;
    }
    public int[] maxSlidingWindow(int[] nums, int k) {
//        if(nums.length==0 && k>nums.length)
//            return new int[0];
        List<Integer> res = new ArrayList<>();
        int max = max(nums, 0, k - 1);
        res.add(max);
        int i=1;
        while (i + k < nums.length +1) {
            int temp = nums[i-1];
            if(temp==max) {
                max = max(nums, i, i + k - 1);
            }else{
                max = Math.max(nums[i + k - 1], max);
            }
            res.add(max);
            i++;
        }

        return res.stream().mapToInt(Integer::intValue).toArray();
    }

    private int max(int[] nums, int start, int end) {
        int max = nums[start];
        for (int i = start+1; i <= end; i++) {
            max = Math.max(max, nums[i]);
        }
        return max;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

}