package net;

import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketDemo {
	@Test
	public void demo1() throws IOException {
		ServerSocket serverSocket = new ServerSocket(9000);

		boolean isStopped = false;
		while(!isStopped){
			Socket clientSocket = serverSocket.accept();

			//do something with clientSocket
		}
	}
}
