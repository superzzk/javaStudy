package net;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class URLDemo {
	@Test
	public void demo1() throws IOException {
		URL url = new URL("http://jenkov.com");

		URLConnection urlConnection = url.openConnection();
		InputStream input = urlConnection.getInputStream();

		int data = input.read();
		while(data != -1){
			System.out.print((char) data);
			data = input.read();
		}
		input.close();
	}

}
