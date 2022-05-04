package com.zzk.study.leetcode.leetcode.editor.cn;//给定一个整数数组 nums，返回区间和在 [lower, upper] 之间的个数，包含 lower 和 upper。
//区间和 S(i, j) 表示在 nums 中，位置从 i 到 j 的元素之和，包含 i 和 j (i ≤ j)。 
//
// 说明: 
//最直观的算法复杂度是 O(n2) ，请在此基础上优化你的算法。 
//
// 示例: 
//
// 输入: nums = [-2,5,-1], lower = -2, upper = 2,
//输出: 3 
//解释: 3个区间分别是: [0,0], [2,2], [0,2]，它们表示的和分别为: -2, -1, 2。
// 
// Related Topics 排序 树状数组 线段树 二分查找 分治算法

import org.junit.Test;

//leetcode submit region begin(Prohibit modification and deletion)
class Q327 {
    public int countRangeSum(int[] nums, int lower, int upper) {
        int count=0;
        for(int i=0;i<nums.length;i++){
            for(int j=i;j<nums.length;j++){
                if(check(nums, i,j,lower,upper))
                    count++;
            }
        }
        return count;
    }

    private boolean check(int[] nums, int start, int end, int lower, int upper) {
        long sum = 0;
        for(int i=start;i<=end;i++){
            sum += nums[i];
        }
        return (sum >= lower && sum <= upper);
    }


    public void test(){
        int[] nums = {-2, 5, -1};
        int cnt = countRangeSum(nums, -2, 2);
        System.out.println(cnt);
    }
}
//leetcode submit region end(Prohibit modification and deletion)

