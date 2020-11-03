package algorithm.array;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

public class Rotate {
	@Test
	public void demo1(){
		//没有效果
		int arr1[] = {10, 20, 30, 40, 50};
		System.out.println(Arrays.toString(arr1));
		Collections.rotate(Arrays.asList(arr1), 1);
		System.out.println(Arrays.toString(arr1));

		//可以
		Integer arr2[] = {10, 20, 30, 40, 50};
		System.out.println(Arrays.toString(arr2));
		Collections.rotate(Arrays.asList(arr2), 1);
		System.out.println(Arrays.toString(arr2));
	}
}
