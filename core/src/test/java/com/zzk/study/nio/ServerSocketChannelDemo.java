package com.zzk.study.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerSocketChannelDemo {
	public void demo1() throws IOException {
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

		serverSocketChannel.socket().bind(new InetSocketAddress(9999));

		while(true){
			SocketChannel socketChannel = serverSocketChannel.accept();

			//do something with socketChannel...
		}
	}
}
