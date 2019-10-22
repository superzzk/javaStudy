package algorithm.sort;

public class QuickSort {
    private static void quickSort(int[] arr, int low, int high){
        int i,j,temp,t;
        if(low>high){
            return;
        }
        i=low;
        j=high;
        //temp就是基准位
        temp = arr[low];

        while (i<j) {
            //先看右边，依次往左递减
            while (temp<=arr[j]&&i<j) {
                j--;
            }
            //再看左边，依次往右递增
            while (temp>=arr[i]&&i<j) {
                i++;
            }
            //如果满足条件则交换
            if (i<j) {
                swap(arr, i, j);
            }

        }
        //最后将基准为与i和j相等位置的数字交换
        arr[low] = arr[i];
        arr[i] = temp;
        //递归调用左半数组
        quickSort(arr, low, j-1);
        //递归调用右半数组
        quickSort(arr, j+1, high);
    }

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
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    public static void quickSort_2(int[] data, int start, int end) {
        if (data == null || start >= end) return;
        int i = start, j = end;
        int pivotKey = data[start];
        while (i < j) {
            while (i < j && data[j] >= pivotKey) j--;
            if (i < j) data[i++] = data[j];
            while (i < j && data[i] <= pivotKey) i++;
            if (i < j) data[j--] = data[i];
        }
        data[i] = pivotKey;
        quickSort_2(data, start, i - 1);
        quickSort_2(data, i + 1, end);
    }

    public static void main(String[] args){
        int[] arr = {10,7,2,4,7,62,3,4,2,1,8,9,19};
        quickSort_1(arr, 0, arr.length-1);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
}