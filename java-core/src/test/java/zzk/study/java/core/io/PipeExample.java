package zzk.study.java.core.io;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * Pipes in Java IO provides the ability for two threads running in the same JVM to communicate.
 * Therefore pipes can also be sources or destinations of data.
 *
 * You cannot use a pipe to communicate with a thread in a different JVM (different process).
 * The pipe concept in Java is different from the pipe concept in Unix / Linux,
 * where two processes running in different address spaces can communicate via a pipe.
 * In Java, the communicating parties must be running in the same process, and should be different threads.
 *
 * There are many other ways than pipes that threads can communicate within the same JVM.
 * In fact, threads more often exchange complete objects rather than raw byte data.
 * But - if you need to exchange raw byte data between threads, Java IO's pipes are a possibility.
 * */
public class PipeExample {

	public static void main(String[] args) throws IOException {

		final PipedOutputStream output = new PipedOutputStream();
		final PipedInputStream input = new PipedInputStream(output);

		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					output.write("Hello world, pipe!".getBytes());
				} catch (IOException e) {
				}
			}
		});

		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					int data = input.read();
					while (data != -1) {
						System.out.print((char) data);
						data = input.read();
					}
				} catch (IOException e) {
				}
			}
		});

		thread1.start();
		thread2.start();
	}
}
