package algorithm.sort;

import org.junit.Test;

import java.util.Arrays;

public class MergeSortDemo {

	@Test
	public void main()
	{
		int arr[] = { 12, 11, 13, 5, 6, 7 };

		System.out.println("Given Array");
		System.out.println(Arrays.toString(arr));

		MergeSort ob = new MergeSort();
		ob.sort(arr, 0, arr.length - 1);

		System.out.println("\nSorted array");
		System.out.println(Arrays.toString(arr));
	}

	public void sort(int[] nums, int left, int right){

		if(left < right){
			int mid = (right-left)/2;
			sort(nums, left, mid);
			sort(nums, mid, right);

			merge(nums, left, mid, right);
		}
	}

	private void merge(int[] nums, int left, int mid, int right) {
		int leftSize = mid-left;
		int rightSize = right-mid;

		int[] L = Arrays.copyOfRange(nums, left, mid);
		int[] R = Arrays.copyOfRange(nums, mid, right);

		int index = left;
		int l=0,r=0;
		while(l<L.length && r<R.length){
			if(L[l]<R[r]){
				nums[index++] = L[l++];
			}else{
				nums[index++] = R[r++];
			}
		}
		while(l<L.length)
			nums[index++] = L[l++];
		while(r<R.length)
			nums[index++] = R[r++];
	}
}
