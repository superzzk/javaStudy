//给你一个整数数组 nums 。如果任一值在数组中出现 至少两次 ，返回 true ；如果数组中每个元素互不相同，返回 false 。
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,2,3,1]
//输出：true 
//
// 示例 2： 
//
// 
//输入：nums = [1,2,3,4]
//输出：false 
//
// 示例 3： 
//
// 
//输入：nums = [1,1,1,3,3,4,3,2,4,2]
//输出：true 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 10⁵ 
// -10⁹ <= nums[i] <= 10⁹ 
// 
//
// Related Topics 数组 哈希表 排序 👍 978 👎 0


import java.util.Arrays;

class Q217{
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public boolean containsDuplicate(int[] nums) {
        if(nums.length <= 1)
            return false;
        binarySort(nums, 0, nums.length-1);
        System.out.println(Arrays.toString(nums));
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) {
                return true;
            }
        }
        return false;
    }

    private void binarySort(int[] nums, int start, int end) {
        if (start < end) {
            int pos = partition(nums, start, end);
            binarySort(nums, start, pos-1);
            binarySort(nums, pos+1, end);
        }
    }

    private int partition(int[] nums, int start, int end) {
        int pivot = end--;
        while (start < end) {
            while(nums[start] < nums[pivot]) start++;
            while(nums[end] > nums[pivot]) end--;
            if (start < end) {
                swap(nums, start, end);
            }
        }
        swap(nums, start, pivot);
        return start;
    }

    private void swap(int[] nums, int a, int b) {
        if(a==b) return;
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
}