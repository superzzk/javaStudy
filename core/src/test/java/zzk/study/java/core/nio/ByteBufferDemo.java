package zzk.study.java.core.nio;

import org.apache.commons.lang3.builder.ToStringExclude;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;
import java.nio.channels.FileChannel;
import java.util.Arrays;

public class ByteBufferDemo {

	/**
	 * Basic Buffer Usage
	 * Using a Buffer to read and write data typically follows this little 4-step process:
	 *
	 * 1. Write data into the Buffer
	 * 2. Call buffer.flip()
	 * 3. Read data out of the Buffer
	 * 4. Call buffer.clear() or buffer.compact()
	 * */
	@Test
	public void simpleUsage() throws IOException {
		RandomAccessFile aFile = new RandomAccessFile("data/nio-data.txt", "rw");
		FileChannel inChannel = aFile.getChannel();

		//create buffer with capacity of 48 bytes
		ByteBuffer buf = ByteBuffer.allocate(48);

		int bytesRead = inChannel.read(buf); //read into buffer.
		while (bytesRead != -1) {

			buf.flip();  //make buffer ready for read

			while(buf.hasRemaining()){
				System.out.print((char) buf.get()); // read 1 byte at a time
			}

			buf.clear(); //make buffer ready for writing
			bytesRead = inChannel.read(buf);
		}
		aFile.close();
	}

	@Test
	public void test1(){
		// Declaring the capacity of the ByteBuffer
		int capacity = 4;

		// Creating the ByteBuffer
		try {
			// creating object of ByteBuffer and allocating size capacity
			ByteBuffer bb = ByteBuffer.allocate(capacity);

			// putting the int to byte typecast value in ByteBuffer using putInt() method
			bb.put((byte)20);
			bb.put((byte)30);
			bb.put((byte)40);
			bb.put((byte)50);
			bb.rewind();

			// print the ByteBuffer
			System.out.println("Original ByteBuffer:  " + Arrays.toString(bb.array()));
		} catch (IllegalArgumentException e) {
			System.out.println("IllegalArgumentException catched");
		} catch (ReadOnlyBufferException e) {

			System.out.println("ReadOnlyBufferException catched");
		}
	}

	@Test
	public void test2()	{
		System.out.println("----------Test allocate--------");
		System.out.println("before alocate:" + Runtime.getRuntime().freeMemory());

		ByteBuffer buffer = ByteBuffer.allocate(1024 * 1000);
		System.out.println("buffer = " + buffer);

		System.out.println("after alocate:" + Runtime.getRuntime().freeMemory());

		// 这部分直接用的系统内存，所以对JVM的内存没有影响
		ByteBuffer directBuffer = ByteBuffer.allocateDirect(1024 * 1000);
		System.out.println("directBuffer = " + directBuffer);
		System.out.println("after direct alocate:" + Runtime.getRuntime().freeMemory());

		System.out.println("----------Test wrap--------");
		byte[] bytes = new byte[32];
		buffer = ByteBuffer.wrap(bytes);
		System.out.println(buffer);

		buffer = ByteBuffer.wrap(bytes, 10, 10);
		System.out.println(buffer);

		System.out.println("--------Test reset----------");
		buffer.clear();
		buffer.position(5);
		buffer.mark();
		buffer.position(10);
		System.out.println("before reset:" + buffer);
		buffer.reset();
		System.out.println("after reset:" + buffer);

		System.out.println("--------Test rewind--------");
		buffer.clear();
		buffer.position(10);
		buffer.limit(15);
		System.out.println("before rewind:" + buffer);
		buffer.rewind();
		System.out.println("after rewind:" + buffer);

		System.out.println("--------Test compact--------");
		buffer.clear();
		buffer.put("abcd".getBytes());
		System.out.println("before compact:" + buffer);
		System.out.println(new String(buffer.array()));
		buffer.flip();
		System.out.println("after flip:" + buffer);
		System.out.println((char) buffer.get());
		System.out.println((char) buffer.get());
		System.out.println((char) buffer.get());
		System.out.println("after three gets:" + buffer);
		System.out.println("\t" + new String(buffer.array()));
		buffer.compact();
		System.out.println("after compact:" + buffer);
		System.out.println("\t" + new String(buffer.array()));

		System.out.println("------Test get-------------");
		buffer = ByteBuffer.allocate(32);
		buffer.put((byte) 'a').put((byte) 'b').put((byte) 'c').put((byte) 'd')
				.put((byte) 'e').put((byte) 'f');
		System.out.println("before flip()" + buffer);
		// 转换为读取模式
		buffer.flip();
		System.out.println("before get():" + buffer);
		System.out.println((char) buffer.get());
		System.out.println("after get():" + buffer);
		// get(index)不影响position的值
		System.out.println((char) buffer.get(2));
		System.out.println("after get(index):" + buffer);
		byte[] dst = new byte[10];
		buffer.get(dst, 0, 2);
		System.out.println("after get(dst, 0, 2):" + buffer);
		System.out.println("\t dst:" + new String(dst));
		System.out.println("buffer now is:" + buffer);
		System.out.println("\t" + new String(buffer.array()));

		System.out.println("--------Test put-------");
		ByteBuffer bb = ByteBuffer.allocate(32);
		System.out.println("before put(byte):" + bb);
		System.out.println("after put(byte):" + bb.put((byte) 'z'));
		System.out.println("\t" + bb.put(2, (byte) 'c'));
		// put(2,(byte) 'c')不改变position的位置
		System.out.println("after put(2,(byte) 'c'):" + bb);
		System.out.println("\t" + new String(bb.array()));
		// 这里的buffer是 abcdef[pos=3 lim=6 cap=32]
		bb.put(buffer);
		System.out.println("after put(buffer):" + bb);
		System.out.println("\t" + new String(bb.array()));
	}



}
