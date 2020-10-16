package algorithm.sort;

import org.junit.Test;

import java.util.Arrays;

public class QuickSortDemo {

    private static void swap(int[] data, int i, int j){
        if(i==j)
            return;
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    @Test
    public void test1(){
        int[] arr = {10,7,2,4,7,62,3,4,2,1,8,9,19};
        qsort(arr, 0, arr.length-1);
        System.out.println(Arrays.toString(arr));
    }

    private static void qsort(int[] nums, int start, int end){
        if(start<0 || end >nums.length-1)
            return;
        if(start == end)
            return;
        int num = nums[start];
        int index = start;
        for(int i=start+1;i<end;i++){
            if(nums[i]<num){
                index++;
                swap(nums,i,index);
            }
        }
        swap(nums, start, index);
        qsort(nums, start, index);
        qsort(nums, index+1, end);
    }


    @Test
    public void test2(){
        int[] arr = {10,7,2,4,7,62,3,4,2,1,8,9,19};
        qsort2(arr, 0, arr.length-1);
        System.out.println(Arrays.toString(arr));
    }
    private static void qsort2(int[] nums, int start, int end){
        if(start>=end)
            return;
        int left = start;
        int right = end-1;
        while(left < right){
            while(nums[left]<nums[end]) left++;
            while(nums[right]>nums[end]) right--;
            if(left<right)
                swap(nums, left, right);
        }
        swap(nums,left,end);
        qsort2(nums, start, left - 1);
        qsort2(nums,left+1,end);
    }


    @Test
    public void test3(){
        int[] arr = {10,7,2,4,7,62,3,4,2,1,8,9,19};
//        int[] arr = {10,7,2,4};
        qSort(arr, 0, arr.length);
        System.out.println(Arrays.toString(arr));
    }
    public void qSort(int[] nums, int start, int end){

        if(start>=end)
            return;
        int left = start;
        int right = end-1;

        int target = nums[right];
        int index = start-1;
        for(int i=start; i<end; i++){
            if(nums[i]<target){
                index++;
                swap(nums, index, i);
            }
        }
        swap(nums, ++index, right);

        qSort(nums, start, index);
        qSort(nums, index+1, end);

    }
}