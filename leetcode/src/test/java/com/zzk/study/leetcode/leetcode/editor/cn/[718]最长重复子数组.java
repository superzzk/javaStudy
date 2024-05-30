//给两个整数数组 nums1 和 nums2 ，返回 两个数组中 公共的 、长度最长的子数组的长度 。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums1 = [1,2,3,2,1], nums2 = [3,2,1,4,7]
//输出：3
//解释：长度最长的公共子数组是 [3,2,1] 。
// 
//
// 示例 2： 
//
// 
//输入：nums1 = [0,0,0,0,0], nums2 = [0,0,0,0,0]
//输出：5
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums1.length, nums2.length <= 1000 
// 0 <= nums1[i], nums2[i] <= 100 
// 
//
// Related Topics 数组 二分查找 动态规划 滑动窗口 哈希函数 滚动哈希 👍 1079 👎 0

import java.util.Arrays;

class Q718 {
    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public int findLength(int[] nums1, int[] nums2) {
            int res = 0;
            int[][] dp = new int[nums1.length][nums2.length];
            for (int i = 0; i < nums1.length; i++) {
                for (int j = 0; j < nums2.length; j++) {
                    if (i == 0 || j == 0)
                        dp[i][j] = nums1[i] == nums2[j] ? 1 : 0;
                    else
                        dp[i][j] = nums1[i] == nums2[j] ? dp[i-1][j-1] + 1 : 0;

                    res = Math.max(res, dp[i][j]);
                }
            }
            return res;
        }
    }

    //leetcode submit region end(Prohibit modification and deletion)
    // 暴力
    class Solution1 {
        public int findLength(int[] nums1, int[] nums2) {
            int res = 0;

            for (int nums1Index = 0; nums1Index < nums1.length; nums1Index++) {
                int start = 0;
                int nums2Pos = -1;
                while (true) {
                    int nums1Pos = nums1Index;
                    nums2Pos = findPos(nums2, nums1[nums1Index], start++);
                    if (nums2Pos == -1)
                        break;
                    int len = 1;
                    while (nums2Pos + 1 < nums2.length && nums1Pos + 1 < nums1.length && nums2[nums2Pos + 1] == nums1[nums1Pos + 1]) {
                        nums2Pos++;
                        nums1Pos++;
                        len++;
                    }
                    res = Math.max(res, len);
                }
            }

            return res;
        }

        private int findPos(int[] nums, int target, int start) {
            while (start < nums.length && nums[start] != target)
                start++;
            return start == nums.length ? -1 : start;
        }
    }

    // 动态规划
    class SolutionDynamic {
        public int findLength(int[] nums1, int[] nums2) {
            int res = 0;
            int[][] dp = new int[nums1.length][nums2.length];
            for (int i = 0; i < nums1.length; i++) {
                for (int j = 0; j < nums2.length; j++) {
                    if (i == 0 || j == 0)
                        dp[i][j] = nums1[i] == nums2[j] ? 1 : 0;
                    else
                        dp[i][j] = nums1[i] == nums2[j] ? dp[i-1][j-1] + 1 : 0;

                    res = Math.max(res, dp[i][j]);
                }
            }
            return res;
        }
    }
}