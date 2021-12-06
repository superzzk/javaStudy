package zzk.study.java.core.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketChannelDemo {
	public void demo1() throws IOException {
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.connect(new InetSocketAddress("http://jenkov.com", 80));

		ByteBuffer buf = ByteBuffer.allocate(48);

		int bytesRead = socketChannel.read(buf);


		String newData = "New String to write to file..." + System.currentTimeMillis();

		ByteBuffer writeBuf = ByteBuffer.allocate(48);
		writeBuf.clear();
		writeBuf.put(newData.getBytes());

		writeBuf.flip();

		while(writeBuf.hasRemaining()) {
			socketChannel.write(writeBuf);
		}
	}

	public void nonBlockingDemo() throws IOException {
		SocketChannel socketChannel = SocketChannel.open();

		socketChannel.configureBlocking(false);
		socketChannel.connect(new InetSocketAddress("http://jenkov.com", 80));

		while(! socketChannel.finishConnect() ){
			//wait, or do something else...
		}
	}
}
