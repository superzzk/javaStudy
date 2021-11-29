package com.zzk.study.leetcode.array;

/**
 * 80
 * Given a sorted array nums, remove the duplicates in-place such that duplicates appeared at most twice
 * and return the new length.
 * <p>
 * Do not allocate extra space for another array, you must do this by modifying the input array in-place
 * with O(1) extra memory.
 * <p>
 * Example 1:
 * <p>
 * Given nums = [1,1,1,2,2,3],
 * <p>
 * Your function should return length = 5, with the first five elements of nums
 * being 1, 1, 2, 2 and 3 respectively.
 * <p>
 * It doesn't matter what you leave beyond the returned length.
 * <p>
 * Example 2:
 * <p>
 * Given nums = [0,0,1,1,1,1,2,3,3],
 * <p>
 * Your function should return length = 7, with the first seven elements of nums being
 * modified to 0, 0, 1, 1, 2, 3 and 3 respectively.
 * <p>
 * It doesn't matter what values are set beyond the returned length.
 * <p>
 * Clarification:
 * <p>
 * Confused why the returned value is an integer but your answer is an array?
 * <p>
 * Note that the input array is passed in by reference, which means modification to the input array will be known to the caller as well.
 * <p>
 * Internally you can think of this:
 * <p>
 * // nums is passed in by reference. (i.e., without making a copy)
 * int len = removeDuplicates(nums);
 * <p>
 * // any modification to nums in your function would be known by the caller.
 * // using the length returned by your function, it prints the first len elements.
 * for (int i = 0; i < len; i++) {
 * print(nums[i]);
 * }
 **/
@SuppressWarnings("Duplicates")
public class Q80_RemoveDuplicatesFromSortedArrayII {
    Q80_RemoveDuplicatesFromSortedArrayII.Solution s = new Q80_RemoveDuplicatesFromSortedArrayII.Solution();

    public static void main(String[] args) {
        int[] nums = {0, 0, 1, 1, 1, 2, 3, 3};
        Q80_RemoveDuplicatesFromSortedArrayII ooo = new Q80_RemoveDuplicatesFromSortedArrayII();
        int n = ooo.s.removeDuplicates(nums);

        for (int i = 0; i < n; i++) {
            System.out.print(nums[i]);
        }
    }

    class Solution {
        public int removeDuplicates(int[] nums) {
            if(nums.length==0)
                return 0;
            int index = 0;
            int appear = 0;
            int len = nums.length;
            int last = nums[0];
            for (int i = 0; i < len; i++) {
                if (nums[i] > last) {
                    appear = appear < 2 ? appear : 2;
                    for (int j = 0; j < appear; j++) {
                        nums[index++] = last;
                    }
                    last = nums[i];
                    appear=1;
                }else {
                    appear++;
                }
            }
            appear = appear < 2 ? appear : 2;
            for (int j = 0; j < appear; j++) {
                nums[index++] = last;
            }
            return index;
        }

        public int removeDuplicates2(int[] nums) {
            int count = 1;
            boolean hasAppeared = false;
            for(int i = 1; i < nums.length;i++) {
                if(nums[i-1] < nums[i]) {
                    nums[count++] = nums[i];
                    hasAppeared = false;
                }
                else if(nums[i- 1] == nums[i] && !hasAppeared) {
                    nums[count++] = nums[i];
                    hasAppeared = true;
                }
            }
            return count;
        }

        public int removeDuplicates3(int[] nums) {
            int index = 0, i = 0, j = 1, count = 0;

            while (j < nums.length) {
                nums[index] = nums[i];

                if (nums[i] < nums[j] || (nums[i]==nums[j] && count==0)) {
                    count = nums[i]==nums[j] ? count+1 : 0;
                    index++;
                }
                i++;
                j++;
            }

            while (i < nums.length) {
                nums[index++] = nums[i++];
            }

            return index;
        }
    }
}
