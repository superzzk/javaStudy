package com.zzk.study.lang;

import org.junit.Test;

public class IntegerDemo {
	@Test
	public void demo()
	{
		int send = 1;
		System.out.println( "[Input] Integer value: " + send + "\n" );
		countBits(  send );
		System.out.println();
		System.out.println("-----------------------------------------");

		send = -1;
		System.out.println( "[Input] Integer value: " + send + "\n" );
		countBits(  send );
		System.out.println();
		System.out.println("-----------------------------------------");

		send = 8549658;
		System.out.println( "[Input] Integer value: " + send + "\n" );
		countBits(  send );
	}

	private void countBits(int i)
	{
		System.out.println( "Integer.toBinaryString: " + Integer.toBinaryString(i) );
		System.out.println( "Integer.toHexString: " + Integer.toHexString(i) );
		System.out.println( "Integer.bitCount: "+ Integer.bitCount(i) );

		int d = i & 0xff000000;
		int c = i & 0xff0000;
		int b = i & 0xff00;
		int a = i & 0xff;

		System.out.println( "\nByte 4th Hex Str: " + Integer.toHexString(d) );
		System.out.println( "Byte 3rd Hex Str: " + Integer.toHexString(c) );
		System.out.println( "Byte 2nd Hex Str: " + Integer.toHexString(b) );
		System.out.println( "Byte 1st Hex Str: " + Integer.toHexString(a) );

		int all = a+b+c+d;
		System.out.println( "\n(1st + 2nd + 3rd + 4th (int(s)) as Integer.toHexString: " + Integer.toHexString(all) );

		System.out.println("(1st + 2nd + 3rd + 4th (int(s)) ==  Integer.toHexString): " +
				Integer.toHexString(all).equals(Integer.toHexString(i) ) );

		System.out.println( "\nIndividual bits for each byte in a 4 byte int:");

		/*
		 * Because we are sending the MSF bytes to a method
		 * which will work on a single byte and print some
		 * bits we are generalising the MSF bytes
		 * by making them all the same in terms of their position
		 * purely for the purpose of printing or analysis
		 */
		System.out.print(
				getBits( (byte) (d >> 24) ) + " " +
						getBits( (byte) (c >> 16) ) + " " +
						getBits( (byte) (b >> 8) ) + " " +
						getBits( (byte) (a >> 0) )
		);


	}

	private String getBits( byte inByte )
	{
		// Go through each bit with a mask
		StringBuilder builder = new StringBuilder();
		for ( int j = 0; j < 8; j++ )
		{
			// Shift each bit by 1 starting at zero shift
			byte tmp =  (byte) ( inByte >> j );

			// Check byte with mask 00000001 for LSB
			int expect1 = tmp & 0x01;

			builder.append(expect1);
		}
		return ( builder.reverse().toString() );
	}
}
