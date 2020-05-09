package algorithm.sort;

import java.util.Arrays;

public class QuickSort {

    private static void quickSort_1(int[] data, int start, int end) {
        if (data == null || start < 0 || end > data.length - 1) {
            throw new IllegalArgumentException("Invalid Parameters");
        }
        if (start == end) return;
        int index = partition(data, start, end);
        if (index > start) {
            quickSort_1(data, start, index - 1);
        }
        if (index < end) {
            quickSort_1(data, index + 1, end);
        }
    }

    private static int partition(int[] data, int start, int end) {
        int index = start + (int)(Math.random() * (end - start + 1));
        swap(data, index, end);
        int small = start - 1;
        for (index = start; index < end; index++) {
            if (data[index] < data[end]) {
                small++;
                if (small != index) {
                    swap(data, index, small);
                }
            }
        }
        swap(data, small + 1, end);
        return small + 1;
    }

    private static void swap(int[] data, int i, int j){
        if(i==j)
            return;
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }


    public static void main(String[] args){
        test2();
    }

    private static void test1(){
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


    private static void test2(){
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
}