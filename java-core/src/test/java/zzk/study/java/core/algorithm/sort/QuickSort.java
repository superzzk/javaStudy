package zzk.study.java.core.algorithm.sort;

import java.util.Arrays;

import static org.apache.commons.lang3.ArrayUtils.swap;

/*
Like Merge Sort, QuickSort is a Divide and Conquer algorithm.
It picks an element as pivot and partitions the given array around the picked pivot.
There are many different versions of quickSort that pick pivot in different ways.

1. Always pick first element as pivot.
2. Always pick last element as pivot (implemented below)
3. Pick a random element as pivot.
4. Pick median as pivot.

The key process in quickSort is partition().
Target of partitions is, given an array and an element x of array as pivot,
put x at its correct position in sorted array and put all smaller elements (smaller than x) before x,
and put all greater elements (greater than x) after x.
All this should be done in linear time.

//low  --> Starting index,  high  --> Ending index
quickSort(arr[], low, high)
{
	if (low < high)
	{
		// pi is partitioning index, arr[pi] is now at right place
		pi = partition(arr, low, high);

		quickSort(arr, low, pi - 1);  // Before pi
		quickSort(arr, pi + 1, high); // After pi
	}
}
*/
public class QuickSort {

	class Solution1 {
		/* This function takes last element as pivot, places the pivot element at its correct
		   position in sorted array, and places all smaller (smaller than pivot) to left of
		   pivot and all greater elements to right of pivot */
		int partition(int arr[], int low, int high) {
			int pivot = arr[high];
			int i = (low - 1); // index of smaller element
			for (int j = low; j < high; j++) {
				// If current element is smaller than the pivot
				if (arr[j] < pivot) {
					i++;

					// swap arr[i] and arr[j]
					int temp = arr[i];
					arr[i] = arr[j];
					arr[j] = temp;
				}
			}

			// swap arr[i+1] and arr[high] (or pivot)
			int temp = arr[i + 1];
			arr[i + 1] = arr[high];
			arr[high] = temp;

			return i + 1;
		}


		/* The main function that implements QuickSort()
		  arr[] --> Array to be sorted,
		  low  --> Starting index,
		  high  --> Ending index */
		void sort(int[] arr, int low, int high) {
			if (low < high) {
            /* pi is partitioning index, arr[pi] is
              now at right place */
				int pi = partition(arr, low, high);

				// Recursively sort elements before
				// partition and after partition
				sort(arr, low, pi - 1);
				sort(arr, pi + 1, high);
			}
		}

		void sort(int[] arr){
			sort(arr, 0, arr.length-1);
		}
	}

	// 20240531
	class Solution3{
		private void quickSort(int[] arr, int left, int right) {
			if(left >= right)
				return;
			int pivot= arr[right-1];
			int i = left;
			int j = right-1;
			while (i < j) {
				while(i<j && arr[i] <= pivot)
					i++;
				while (i<j && arr[j] >= pivot)
					j--;
				if(i<j)
					swap(arr, i, j);
			}
			swap(arr, i, right-1);
			quickSort(arr, left, i);
			quickSort(arr, i+1, right);
		}

		public void sort(int[] nums){
			quickSort(nums, 0, nums.length);
		}
	}

	public static void main(String[] args) {
//		int arr[] = {10, 7, 8, 9, 1, 5};
//		int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
//		int[] arr = {10,7,2,4,7,62,3,4,2,1,8,9,19};
		int[] arr= {1,1,1,3,3,4,3,2,4,2};
		new QuickSort().new Solution3().sort(arr);
		System.out.println(Arrays.toString(arr));
	}

	private static void swap(int[] data, int i, int j){
		if(i==j)
			return;
		int temp = data[i];
		data[i] = data[j];
		data[j] = temp;
	}
}

