package leecode.explore.basic.algorithm;

import java.util.Arrays;
import java.util.Random;

/**
 * @author zhangzhongkun
 * @since 2019-10-14 13:25
 **/
public class Design {

    /**
     * 384
     * Shuffle an Array
     *
     * 打乱一个没有重复元素的数组。
     * 示例:
     *
     * // 以数字集合 1, 2 和 3 初始化数组。
     * int[] nums = {1,2,3};
     * Solution solution = new Solution(nums);
     *
     * // 打乱数组 [1,2,3] 并返回结果。任何 [1,2,3]的排列返回的概率应该相同。
     * solution.shuffle();
     *
     * // 重设数组到它的初始状态[1,2,3]。
     * solution.reset();
     *
     * // 随机返回数组[1,2,3]打乱后的结果。
     * solution.shuffle();
     */
    class Solution {
        int[] initial_nums;
        public Solution(int[] nums) {
            initial_nums = Arrays.copyOf(nums,nums.length);
        }

        /** Resets the array to its original configuration and return it. */
        public int[] reset() {
            return Arrays.copyOf(initial_nums,initial_nums.length);
        }

        /** Returns a random shuffling of the array. */
        public int[] shuffle() {
            int[] result = Arrays.copyOf(initial_nums,initial_nums.length);
            for(int i =0; i< result.length; i++){
                int swap = new Random().nextInt(initial_nums.length);
                swap(result,i,swap);
            }
            return result;
        }
        private void swap(int[] nums, int a ,int b){
            int temp = nums[a];
            nums[a] = nums[b];
            nums[b] = temp;
        }
    }
}
