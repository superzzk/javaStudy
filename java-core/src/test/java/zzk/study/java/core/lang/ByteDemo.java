package zzk.study.java.core.lang;

import org.junit.Test;

public class ByteDemo {
	@Test
	public void demo1(){

	}

	@Test
	public void convert_byte_to_binary_string(){
		byte b = -1;
		System.out.println(Integer.toBinaryString(b));


		byte b1 = (byte) 129;
		System.out.println(String.format("%8s", Integer.toBinaryString(b1 & 0xFF)));
		String s1 = String.format("%8s", Integer.toBinaryString(b1 & 0xFF)).replace(' ', '0');
		System.out.println(s1); // 10000001

		byte b2 = (byte) 2;
		System.out.println(String.format("%8s", Integer.toBinaryString(b2 & 0xFF)));
		String s2 = String.format("%8s", Integer.toBinaryString(b2 & 0xFF)).replace(' ', '0');
		System.out.println(s2); // 00000010


		byte b3 = (byte)(int)Integer.valueOf("10000010", 2);
		System.out.println(b3);// output -> -126
	}

	public static String byteToString(byte b) {
		byte[] masks = { -128, 64, 32, 16, 8, 4, 2, 1 };
		StringBuilder builder = new StringBuilder();
		for (byte m : masks) {
			if ((b & m) == m) {
				builder.append('1');
			} else {
				builder.append('0');
			}
		}
		return builder.toString();
	}

	public static String getByteBinaryString(byte b) {
		StringBuilder sb = new StringBuilder();
		for (int i = 7; i >= 0; --i) {
			sb.append(b >>> i & 1);
		}
		return sb.toString();
	}
}


