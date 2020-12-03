package com.zzk.study.io.exception_handle;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * http://tutorials.jenkov.com/java-exception-handling/try-with-resources.html
 * */
public class TryWithResourceDemo {
	private static void printFile() throws IOException {

		try(FileInputStream input = new FileInputStream("file.txt")) {

			int data = input.read();
			while(data != -1){
				System.out.print((char) data);
				data = input.read();
			}
		}
	}

	/**
	 * Closing Order
	 * The resources declared in a Java try-with-resources construct will be closed in reverse order of the order
	 * in which they are created / listed inside the parentheses.
	 * In the example in the previous section, first the will be closed, then the FileInputStream
	 * */
	private static void multiple_resources() throws IOException {

		try(  FileInputStream     input         = new FileInputStream("file.txt");
		      BufferedInputStream bufferedInput = new BufferedInputStream(input)
		) {

			int data = bufferedInput.read();
			while(data != -1){
				System.out.print((char) data);
				data = bufferedInput.read();
			}
		}
	}

	/**
	 * Custom AutoClosable Implementations
	 * */
	private void myAutoClosable() throws Exception {
		try(MyAutoClosable myAutoClosable = new MyAutoClosable()){
			myAutoClosable.doIt();
		}
	}

	public class MyAutoClosable implements AutoCloseable {

		public void doIt() {
			System.out.println("MyAutoClosable doing it!");
		}

		@Override
		public void close() throws Exception {
			System.out.println("MyAutoClosable closed!");
		}
	}

}
