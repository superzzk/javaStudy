package zzk.study.java.core.net;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketDemo {
	@Test
	public void demo1() throws IOException {
		Socket socket = new Socket("jenkov.com", 80);
		OutputStream out = socket.getOutputStream();

		out.write("some data".getBytes());
		out.flush();
		out.close();


		InputStream in = socket.getInputStream();


		int data = ((InputStream) in).read();
		System.out.println(data);

		in.close();
		socket.close();

	}
}
