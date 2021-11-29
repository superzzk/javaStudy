package com.zzk.study.leetcode.explore.basic.algorithm;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author zhangzhongkun
 * @since 2019-10-11 17:50
 **/
public class Dynamic {

    /**
     * 买卖股票的最佳时机
     *
     * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
     * 如果你最多只允许完成一笔交易（即买入和卖出一支股票），设计一个算法来计算你所能获取的最大利润。
     *
     * 注意你不能在买入股票前卖出股票。
     *
     * 示例 1:
     * 输入: [7,1,5,3,6,4]
     * 输出: 5
     * 解释: 在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
     *      注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格。
     *
     * 示例 2:
     * 输入: [7,6,4,3,1]
     * 输出: 0
     * 解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。
     */
    public int maxProfit(int[] prices) {

        return 0;
    }



    /**
     * 300
     * 给定一个无序的整数数组，找到其中最长上升子序列的长度。
     *
     * 示例:
     *
     * 输入: [10,9,2,5,3,7,101,18]
     * 输出: 4
     * 解释: 最长的上升子序列是 [2,3,7,101]，它的长度是 4。
     *
     * 说明:
     *
     *     可能会有多种最长上升子序列的组合，你只需要输出对应的长度即可。
     *     你算法的时间复杂度应该为 O(n2) 。
     *
     * 进阶: 你能将算法的时间复杂度降低到 O(n log n) 吗?
     *
     */
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int [] dp = new int[1000000];
        int res = 0;
        for(int i = 0;i < n;i++) {
            dp[i] = 1;
            for(int j = 0;j < i;j++)
                if(nums[j] < nums[i])
                    dp[i] = Math.max(dp[i],dp[j] + 1);
            res = Math.max(res,dp[i]);
        }
        return res;
    }
    @Test
    public void test_lengthOfLIS(){
        int[] data = {10, 9, 2, 5, 3, 7, 101, 18};
        int result = lengthOfLIS(data);
        System.out.println(result);
    }


    /**
     * 最大子序和
     *
     * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
     *
     * 示例:
     *
     * 输入: [-2,1,-3,4,-1,2,1,-5,4],
     * 输出: 6
     * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
     *
     * 进阶:
     *
     * 如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的分治法求解。
     */
    public int maxSubArray(int[] nums) {
        int[] sums = Arrays.copyOf(nums, nums.length);
        int max=nums[0];
        sums[0] = nums[0];
        for(int i=1; i<nums.length; i++){

            sums[i] = nums[i];
            if(nums[i] + sums[i-1] > nums[i]){
                sums[i] = nums[i] + sums[i-1];
            }

            max = sums[i] > max ? sums[i] : max;
        }
        return max;
    }

    @Test
    public void test_maxSubArray(){
        int[] data = {-2,1,-3,4,-1,2,1,-5,4};
        int result = maxSubArray(data);
        Assert.assertEquals(result, 6);
    }
}
