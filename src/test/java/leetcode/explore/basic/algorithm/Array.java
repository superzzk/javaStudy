package leetcode.explore.basic.algorithm;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author zhangzhongkun
 * @since 2019-10-11 10:16
 **/
public class Array {
    /**
     * 只出现一次的数字
     *
     * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
     *
     * 说明：
     *
     * 你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
     *
     * 示例 1:
     *
     * 输入: [2,2,1]
     * 输出: 1
     *
     * 示例 2:
     *
     * 输入: [4,1,2,1,2]
     * 输出: 4
     */
    public int singleNumber(int[] nums) {
        Set<Integer> once = new HashSet<>();
        for(int i : nums){
            if(once.contains(i)) {
                once.remove(i);
                continue;
            }
            once.add(i);
        }
        return (int)once.toArray()[0];
    }

    /**
     * 旋转数组
     *
     * 给定一个数组，将数组中的元素向右移动 k 个位置，其中 k 是非负数。
     *
     * 示例 1:
     *
     * 输入: [1,2,3,4,5,6,7] 和 k = 3
     * 输出: [5,6,7,1,2,3,4]
     * 解释:
     * 向右旋转 1 步: [7,1,2,3,4,5,6]
     * 向右旋转 2 步: [6,7,1,2,3,4,5]
     * 向右旋转 3 步: [5,6,7,1,2,3,4]
     *
     * 示例 2:
     *
     * 输入: [-1,-100,3,99] 和 k = 2
     * 输出: [3,99,-1,-100]
     * 解释:
     * 向右旋转 1 步: [99,-1,-100,3]
     * 向右旋转 2 步: [3,99,-1,-100]
     *
     * 说明:
     *
     *     尽可能想出更多的解决方案，至少有三种不同的方法可以解决这个问题。
     *     要求使用空间复杂度为 O(1) 的 原地 算法。
     */
    public void rotate(int[] nums, int k) {
        k = k % nums.length;

        int[] newNusm = Arrays.copyOf(nums, nums.length);
        for(int i=0; i<nums.length; i++){
            int index = (i+k)%nums.length;
            newNusm[index] = nums[i];
        }

        for(int i=0; i<nums.length; i++){
            nums[i] = newNusm[i];
        }
    }


    public void rotate2(int[] nums, int k) {
        k = k % nums.length;
        int count = 0;
        for (int start = 0; count < nums.length; start++) {
            int current = start;
            int prev = nums[start];
            do {
                int next = (current + k) % nums.length;
                int temp = nums[next];
                nums[next] = prev;
                prev = temp;
                current = next;
                count++;
            } while (start != current);
        }
    }

    public void rotate3(int[] nums, int k) {
        int temp, previous;
        for (int i = 0; i < k; i++) {
            previous = nums[nums.length - 1];
            for (int j = 0; j < nums.length; j++) {
                temp = nums[j];
                nums[j] = previous;
                previous = temp;
            }
        }
    }



    /**
     * 旋转图像
     *
     * 给定一个 n × n 的二维矩阵表示一个图像。
     * 将图像顺时针旋转 90 度。
     * 说明：
     *
     * 你必须在原地旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要使用另一个矩阵来旋转图像。
     *
     * 示例 1:
     *
     * 给定 matrix =
     * [
     *   [1,2,3],
     *   [4,5,6],
     *   [7,8,9]
     * ],
     *
     * 原地旋转输入矩阵，使其变为:
     * [
     *   [7,4,1],
     *   [8,5,2],
     *   [9,6,3]
     * ]
     *
     * 示例 2:
     *
     * 给定 matrix =
     * [
     *   [ 5, 1, 9,11],
     *   [ 2, 4, 8,10],
     *   [13, 3, 6, 7],
     *   [15,14,12,16]
     * ],
     *
     * 原地旋转输入矩阵，使其变为:
     * [
     *   [15,13, 2, 5],
     *   [14, 3, 4, 1],
     *   [12, 6, 8, 9],
     *   [16, 7,10,11]
     * ]
     */
    public void rotate(int[][] matrix) {






    }

    @Test
    public void test_reverse_array(){
        int[] array = {1,2,3,4,5};
        reverse_array(array);
        System.out.println(Arrays.toString(array));
    }

    private void reverse_array(int[] input){
        if(input.length<2)
            return;
        int left =0;
        int right = input.length-1;

        do{
            swap(input, left++, right--);
        }while(left!=right && left>right);
    }
    private void swap(int[] nums, int a,int b){
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] =temp;
    }

    /**
     * 两个数组的交集 II
     *
     * 给定两个数组，编写一个函数来计算它们的交集。
     * 示例 1:
     * 输入: nums1 = [1,2,2,1], nums2 = [2,2]
     * 输出: [2,2]
     *
     * 示例 2:
     * 输入: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
     * 输出: [4,9]
     *
     * 说明：
     *     输出结果中每个元素出现的次数，应与元素在两个数组中出现的次数一致。
     *     我们可以不考虑输出结果的顺序。
     *
     * 进阶:
     *     如果给定的数组已经排好序呢？你将如何优化你的算法？
     *     如果 nums1 的大小比 nums2 小很多，哪种方法更优？
     *     如果 nums2 的元素存储在磁盘上，磁盘内存是有限的，并且你不能一次加载所有的元素到内存中，你该怎么办？
     */
    public int[] intersect(int[] nums1, int[] nums2) {
        int[] temp;
        if(nums1.length>nums2.length){
            temp = nums1;
            nums1 = nums2;
            nums2 = temp;
        }
        int[] r = new int[nums1.length];
        int k =0;
        int index = 0;
        for(int i=0; i<nums1.length; i++){
            boolean find = false;
            for(int j=0; j<nums2.length - k; j++){
                if(nums2[j]==nums1[i]){
                    find = true;
                    swap(nums2, j, nums2.length - 1 -k);
                    k++;
                    break;
                }
            }
            if(find)
                r[index++] = nums1[i];
        }

        return Arrays.copyOf(r, index);
    }

    @Test
    public void test_intersect(){
        int[] a = {61,24,20,58,95,53,17,32,45,85,70,20,83,62,35,89,5,95,12,86,58,77,30,64,46,13,5,92,67,40,20,38,31,18,89,85,7,30,67,34,62,35,47,98,3,41,53,26,66,40,54,44,57,46,70,60,4,63,82,42,65,59,17,98,29,72,1,96,82,66,98,6,92,31,43,81,88,60,10,55,66,82,0,79,11,81};
        int[] b = {5,25,4,39,57,49,93,79,7,8,49,89,2,7,73,88,45,15,34,92,84,38,85,34,16,6,99,0,2,36,68,52,73,50,77,44,61,48};

        int[] result = intersect(a, b);
        //expect: [5,4,57,79,7,89,88,45,34,92,38,85,6,0,77,44,61]
        System.out.println(Arrays.toString(result));
    }



    /**
     * 220 存在重复元素 III
     * 给定一个整数数组，判断数组中是否有两个不同的索引 i 和 j，
     * 使得 nums [i] 和 nums [j] 的差的绝对值最大为 t，并且 i 和 j 之间的差的绝对值最大为 ķ。
     *
     * 示例 1:
     *
     * 输入: nums = [1,2,3,1], k = 3, t = 0
     * 输出: true
     *
     * 示例 2:
     *
     * 输入: nums = [1,0,1,1], k = 1, t = 2
     * 输出: true
     *
     * 示例 3:
     *
     * 输入: nums = [1,5,9,1,5,9], k = 2, t = 3
     * 输出: false
     */


    /**
     * 初始化一颗空的二叉搜索树 set
     * 对于每个元素xxx，遍历整个数组
     *
     *     在 set 上查找大于等于xxx的最小的数，如果s−x≤ts - x \leq ts−x≤t则返回 true
     *     在 set 上查找小于等于xxx的最大的数，如果x−g≤tx - g \leq tx−g≤t则返回 true
     *     在 set 中插入xxx
     *     如果树的大小超过了kkk, 则移除最早加入树的那个数。
     *
     * 返回 false
     */
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        TreeSet<Integer> set = new TreeSet<>();
        for (int i = 0; i < nums.length; ++i) {
            // Find the successor of current element
            Integer s = set.ceiling(nums[i]);
            if (s != null && s <= nums[i] + t) return true;

            // Find the predecessor of current element
            Integer g = set.floor(nums[i]);
            if (g != null && nums[i] <= g + t) return true;

            set.add(nums[i]);
            if (set.size() > k) {
                set.remove(nums[i - k]);
            }
        }
        return false;
    }

    @Test
    public void test_containsNearbyAlmostDuplicate(){
        int[] nums1  = {1,2,3,1};
        Assert.assertTrue(containsNearbyAlmostDuplicate(nums1, 3, 0));

        int[] nums2  = {1,5,9,1,5,9};
        Assert.assertFalse(containsNearbyAlmostDuplicate(nums2, 2, 3));

        int[] nums3  = {7,1,3};
        Assert.assertTrue(containsNearbyAlmostDuplicate(nums3, 2, 3));

        int[] nums4  = {3,6,0,4};
        Assert.assertTrue(containsNearbyAlmostDuplicate(nums4, 2, 2));

        int a = -1;
        int b = 2147483647;
        int min = Integer.MIN_VALUE;
        int max = Integer.MAX_VALUE;

        int c = b-a;

        int cc = b +100;

        int d = Math.abs(c);
        int[] nums5 = {-1,2147483647};
        Assert.assertFalse(containsNearbyAlmostDuplicate(nums5, 1, 2147483647));


        int[] nums6 = {2147483647,-2147483647};
        Assert.assertFalse(containsNearbyAlmostDuplicate(nums6, 1, 2147483647));
    }


}
