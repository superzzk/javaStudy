package zzk.study.java.core.algorithm;

import java.util.Random;

/**
 * 100万个数据里面找出100个最大的
 *
 * 建立大小为100的最小堆，然后遍历余下的所有数字，大于堆顶的数字放入堆中，不断进行维护
 * */

public class TopHundred2 {
    public static void main(String[] args) {
        // the size of the array
        int number = 100000000;
        // the top k values
        int k = 100;
        // the range of the values in the array
        int range = 1000000001;

        //input for minHeap based method
        int[] array = new int[number];

        Random random = new Random();
        for (int i = 0; i < number; i++) {
            array[i] = random.nextInt(range);
        }

        TopHundred2 thh = new TopHundred2();

        long t1, t2;
        //start time
        t1 = System.currentTimeMillis();
        int[] top = thh.topHundred(array, k);

        //end time
        t2 = System.currentTimeMillis();
        System.out.println("The total execution time of " +
                "quicksort based method is " + (t2 - t1) +" millisecond!");

        // print out the top k largest values in the top array
        System.out.println("The top "+ k + "largest values are:");
        for (int i = 0; i < k; i++) {
            System.out.println(top[i]);
        }
    }

    public int[] topHundred(int[] array, int k) {
        // the heap with size k
        int[] top = new int[k];

        for (int i = 0; i < k; i++) {//默认前100个数据来完成堆的构造
            top[i] = array[i];
        }

        buildMinHeap(top);

        for (int i = k; i < array.length; i++) {
            if (top[0] < array[i]) {//大于最小堆的堆顶
                top[0] = array[i];
                minHeapify(top, 0, top.length);
            }
        }

        return top;
    }

    // create a min heap
    public void buildMinHeap(int[] array) {
        int heapSize = array.length;
        for (int i = array.length / 2 - 1; i >= 0; i--) {//从下向上调整
            minHeapify(array, i, heapSize);
        }
    }

    /// MinHeapify is to build the min heap from the 'position'最小堆，父节点比左右节点都小
    public void minHeapify(int[] array, int position, int heapSize)
    {
        int left = left(position);//得到当前子树的左子树的位置
        int right = right(position);
        int minPosition = position;

        if (left < heapSize && array[left] < array[position]) {//左子树比父节点小
            minPosition = left;
        }

        if (right < heapSize && array[right] < array[minPosition]) {//右子树比父节点小
            minPosition = right;
        }

        if (position != minPosition) {
            swap(array, position, minPosition);//完成父节点的值最小
            minHeapify(array, minPosition, heapSize);//继续调整，直到叶子节点
        }
    }

    public void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /// return the left child position
    public int left(int i)
    {
        return 2 * i + 1;
    }
    /// return the right child position
    public int right(int i)
    {
        return 2 * i + 2;
    }
}
