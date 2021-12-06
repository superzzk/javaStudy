package zzk.study.java.core.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class DatagramChannelDemo {
	public void demo1() throws IOException {
		DatagramChannel channel = DatagramChannel.open();
		channel.socket().bind(new InetSocketAddress(9999));

		ByteBuffer buf = ByteBuffer.allocate(48);
		buf.clear();

		channel.receive(buf);
	}

	public void demo2() throws IOException {
		DatagramChannel channel = DatagramChannel.open();
		String newData = "New String to write to file..."
				+ System.currentTimeMillis();

		ByteBuffer buf = ByteBuffer.allocate(48);
		buf.clear();
		buf.put(newData.getBytes());
		buf.flip();

		int bytesSent = channel.send(buf, new InetSocketAddress("jenkov.com", 80));


		channel.connect(new InetSocketAddress("jenkov.com", 80));
		int bytesRead = channel.read(buf);
	}
}
