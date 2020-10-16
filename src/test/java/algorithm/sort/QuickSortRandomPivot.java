package algorithm.sort;

import org.junit.Test;

import java.util.Arrays;

public class QuickSortRandomPivot {

    @Test
    public void demo(){
        int[] arr = {10,7,2,4,7,62,3,4,2,1,8,9,19};
        qSort(arr, 0, arr.length-1);
        System.out.println(Arrays.toString(arr));
    }

    private static void qSort(int[] data, int start, int end) {
        if (data == null || start < 0 || end > data.length - 1) {
            throw new IllegalArgumentException("Invalid Parameters");
        }
        if (start == end) return;
        int index = partition(data, start, end);
        if (index > start) {
            qSort(data, start, index - 1);
        }
        if (index < end) {
            qSort(data, index + 1, end);
        }
    }

    private static int partition(int[] data, int start, int end) {
        int index = start + (int)(Math.random() * (end - start + 1));
        swap(data, index, end);
        int small = start - 1;
        for (index = start; index < end; index++) {
            if (data[index] < data[end]) {
                small++;
                swap(data, index, small);
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
}