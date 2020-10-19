package leecode;

import org.junit.Test;

public class Solution {
	public int countRangeSum(int[] nums, int lower, int upper) {
		int count=0;
		for(int i=0;i<nums.length;i++){
			for(int j=i;j<nums.length;j++){
				if(check(nums, i,j,lower,upper))
					count++;
			}
		}
		return count;
	}

	private boolean check(int[] nums, int start, int end, int lower, int upper) {
		int sum = 0;
		for(int i=start;i<=end;i++){
			sum += nums[i];
		}
		return (sum >= lower && sum <= upper);
	}

	@Test
	public void test(){
		int[] nums = {-2, 5, -1};
		int cnt = countRangeSum(nums, -2, 2);
		System.out.println(cnt);
	}
}