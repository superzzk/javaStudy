package leetcode.explore.basic.algorithm;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author zhangzhongkun
 * @since 2019-10-14 08:39
 **/
public class Sort {

    /**
     * 合并两个有序数组
     *
     * 给定两个有序整数数组 nums1 和 nums2，将 nums2 合并到 nums1 中，使得 num1 成为一个有序数组。
     * 说明:
     *     初始化 nums1 和 nums2 的元素数量分别为 m 和 n。
     *     你可以假设 nums1 有足够的空间（空间大小大于或等于 m + n）来保存 nums2 中的元素。
     *
     * 示例:
     *
     * 输入:
     * nums1 = [1,2,3,0,0,0], m = 3
     * nums2 = [2,5,6],       n = 3
     *
     * 输出: [1,2,2,3,5,6]
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int index1=0;
        int index2=0;
        int startPos = 0;
        int endPos = 0;
        int nums1Length = m;
        while(index1<nums1Length && index2<n){
            if (nums2[index2] < nums1[index1]){
                startPos = index2;
//                while(index2<n-1 && nums2[index2+1] <nums1[index1]){
//                    index2++;
//                }
                while(index2<n && nums2[index2] <nums1[index1])
                    index2++;
                endPos = index2;
                int[] temp = Arrays.copyOfRange(nums2, startPos, endPos);
                insertArrayAtPos(nums1, nums1Length, temp, index1);
                nums1Length = nums1Length + endPos - startPos;
            }
            index1++;
        }
        while(index2<n){
            nums1[nums1Length++] = nums2[index2++];
        }
    }
    /**
     * description:
     * create time:
     *
     * @param length 输入数组中实际元素个数
     */
    private void insertArrayAtPos(int[] nums, int length, int[] insertNums, int pos){
        int shift = insertNums.length;
        for(int i=length-1; i>=pos; i--){
            nums[i + shift] = nums[i];
        }
        int index = pos;
        for(int i=0; i<insertNums.length;i++){
            nums[index++] = insertNums[i];
        }
    }
    @Test
    public void test_insertArrayAtPos(){
        int[] nums1 = {1, 2, 3, 0, 0, 0};
        int[] nums2 = {5, 6};
        insertArrayAtPos(nums1, 3, nums2, 2);

        int[] result  = {1,2,5,6,3,0};
        System.out.println(Arrays.toString(nums1));
        Assert.assertArrayEquals(nums1, result);
    }
    @Test
    public void test_merge(){
        int[] nums1 = {1, 2, 3, 0, 0, 0};
        int[] nums2 = {2, 5, 6};
        merge(nums1, 3, nums2, 3);

        int[] result  = {1,2,2,3,5,6};
        System.out.println(Arrays.toString(nums1));
        Assert.assertArrayEquals(nums1, result);


        int[] nums3 = {0,0,3,0,0,0,0,0,0};
        int[] nums4 = {-1,1,1,1,2,3};
        merge(nums3, 3, nums4, 6);
        //[-1,0,0,3,1,1,1,2,3]
        int[] result2  = {-1,0,0,1,1,1,2,3,3};
        System.out.println(Arrays.toString(nums1));
        Assert.assertArrayEquals(nums1, result);
    }


    /**
     * 第一个错误的版本
     *
     * 你是产品经理，目前正在带领一个团队开发新的产品。不幸的是，你的产品的最新版本没有通过质量检测。
     * 由于每个版本都是基于之前的版本开发的，所以错误的版本之后的所有版本都是错的。
     *
     * 假设你有 n 个版本 [1, 2, ..., n]，你想找出导致之后所有版本出错的第一个错误的版本。
     *
     * 你可以通过调用 bool isBadVersion(version) 接口来判断版本号 version 是否在单元测试中出错。
     * 实现一个函数来查找第一个错误的版本。你应该尽量减少对调用 API 的次数。
     *
     * 示例:
     *
     * 给定 n = 5，并且 version = 4 是第一个错误的版本。
     *
     * 调用 isBadVersion(3) -> false
     * 调用 isBadVersion(5) -> true
     * 调用 isBadVersion(4) -> true
     *
     * 所以，4 是第一个错误的版本。
     */
    public int firstBadVersion(int n) {
        if(n < 2)
            return n;

        return helper(1,n);
    }

    private int helper(int start, int end){
        int pos = start + (end-start)/2 ;
        if(pos == start || pos ==end){
            if(isBadVersion(start))
                return start;
            else
                return end;
        }
        if(isBadVersion( pos )){
            if(isFirstBadVersion(pos))
                return pos;
            else
                return helper(start, pos);
        }else{
            return helper(pos,end);
        }
    }
    private boolean isFirstBadVersion(int n){
        if(n==1)
            return true;
        return !isBadVersion(n-1);
    }


    private boolean isBadVersion(int version){
        return false;
    }
}
