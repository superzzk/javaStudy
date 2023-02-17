package zzk.study.java.core.basic.builtin;

import org.junit.Test;

public class LongDemo {

	@Test
	public void demo_highestOneBit(){
		long l = 9;

		// returns a long value with at most a
		// single one-bit, in the position
		// of the highest-order ("rightmost")
		// one-bit in the specified long value.
		System.out.println("highest one bit = " + Long.highestOneBit(l));

		l = 45;
		System.out.println("highest one bit = " + Long.highestOneBit(l));

		l = -9223372036854775808L;
		System.out.println(Long.toBinaryString(l));
	}

	@Test
	public void numberOfTrailingZeros(){
		long l = 8;

		// returns the number of zero bits following the lowest-order set bit
		System.out.println("Number of 8 trailing zeros = " + Long.numberOfTrailingZeros(l));

		// second example
		l = 25;
		System.out.println("Number of 25 trailing zeros = " + Long.numberOfTrailingZeros(l));

		l = -12;

		// returns the number of zero bits following the lowest-order
		// set bit
		System.out.println("Number of -12 trailing zeros = " + Long.numberOfTrailingZeros(l));
	}

	@Test
	public void byteValue(){
		Long value = -1023l;

		// returns the value of Long as a byte
		byte byteValue = value.byteValue();
		System.out.println("Byte Value of num = " + byteValue);

		// 2nd example
		value = -12l;
		byteValue = value.byteValue();
		System.out.println("Byte Value of num = " + byteValue);
	}
}
