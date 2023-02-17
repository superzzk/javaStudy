package zzk.study.java.core.algorithm.sort.heap;

/**
 * Heap sort is a comparison based sorting technique based on Binary Heap data structure.
 * It is similar to selection sort where we first find the maximum element and place the maximum element at the end.
 * We repeat the same process for the remaining elements.
 * <p>
 * What is Binary Heap?
 * Let us first define a Complete Binary Tree.
 * A complete binary tree is a binary tree in which every level, except possibly the last,
 * is completely filled, and all nodes are as far left as possible (Source Wikipedia)
 * <p>
 * A Binary Heap is a Complete Binary Tree where items are stored in a special order
 * such that value in a parent node is greater(or smaller) than the values in its two children nodes.
 * The former is called as max heap and the latter is called min-heap. The heap can be represented by a binary tree or array.
 * <p>
 * Why array based representation for Binary Heap?
 * Since a Binary Heap is a Complete Binary Tree, it can be easily represented as an array and the array-based representation is space-efficient.
 * If the parent node is stored at index I, the left child can be calculated by 2 * I + 1 and right child by 2 * I + 2 (assuming the indexing starts at 0).
 * <p>
 * Heap Sort Algorithm for sorting in increasing order:
 * 1. Build a max heap from the input data.
 * 2. At this point, the largest item is stored at the root of the heap.
 * Replace it with the last item of the heap followed by reducing the size of heap by 1. Finally, heapify the root of the tree.
 * 3. Repeat step 2 while size of heap is greater than 1.
 * <p>
 * How to build the heap?
 * Heapify procedure can be applied to a node only if its children nodes are heapified.
 * So the heapification must be performed in the bottom-up order.
 * <p>
 * Lets understand with the help of an example:
 * <p>
 * Input data: 4, 10, 3, 5, 1
 * 4(0)
 * /   \
 * 10(1)   3(2)
 * /   \
 * 5(3)    1(4)
 * <p>
 * The numbers in bracket represent the indices in the array
 * representation of data.
 * <p>
 * Applying heapify procedure to index 1:
 * 4(0)
 * /   \
 * 10(1)    3(2)
 * /   \
 * 5(3)    1(4)
 * <p>
 * Applying heapify procedure to index 0:
 * 10(0)
 * /  \
 * 5(1)  3(2)
 * /   \
 * 4(3)    1(4)
 * The heapify procedure calls itself recursively to build heap
 * in top down manner.
 * <p>
 * Notes:
 * Heap sort is an in-place algorithm.
 * Its typical implementation is not stable, but can be made stable (See this)(https://www.geeksforgeeks.org/stability-in-sorting-algorithms/)
 * <p>
 * Time Complexity: Time complexity of heapify is O(Logn).
 * Time complexity of createAndBuildHeap() is O(n) and overall time complexity of Heap Sort is O(nLogn).
 * <p>
 * Applications of HeapSort
 * 1. Sort a nearly sorted (or K sorted) array(https://www.geeksforgeeks.org/nearly-sorted-algorithm/)
 * 2. k largest(or smallest) elements in an array(https://www.geeksforgeeks.org/k-largestor-smallest-elements-in-an-array/)
 * <p>
 * Heap sort algorithm has limited uses because Quicksort and Mergesort are better in practice.
 * Nevertheless, the Heap data structure itself is enormously used.
 * See Applications of Heap Data Structure(https://www.geeksforgeeks.org/applications-of-heap-data-structure/)
 */
public class HeapSort {

    // Driver program
    public static void main(String args[]) {
        int arr[] = {12, 11, 13, 5, 6, 7};
        int n = arr.length;

        HeapSort ob = new HeapSort();
        ob.sort(arr);

        System.out.println("Sorted array is");
        printArray(arr);
    }

    public void sort(int arr[]) {
        int n = arr.length;
        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i);
        printArray(arr);

        // One by one extract an element from heap
        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);// Move current root to end
            heapify(arr, i, 0);// call max heapify on the reduced heap
        }
    }

    // To heapify a subtree rooted with node i which is an index in arr[]. n is size of heap
    void heapify(int arr[], int n, int i) {
        int largest = i; // Initialize largest as root
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        // If left child is larger than root
        if (left < n && arr[left] > arr[largest]) largest = left;
        // If right child is larger than largest so far
        if (right < n && arr[right] > arr[largest]) largest = right;

        // If largest is not root
        if (largest != i) {
            swap(arr, i, largest);
            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest);
        }
    }

    /* A utility function to print array of size n */
    static void printArray(int arr[]) {
        int n = arr.length;
        for (int i = 0; i < n; ++i)
            System.out.print(arr[i] + " ");
        System.out.println();
    }

    private static void swap(int[] data, int i, int j) {
        if (i == j)
            return;
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }
}
