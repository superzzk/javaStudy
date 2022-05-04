package zzk.study.java.core.nio.filecopy;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

public class RemoteFileCopy {

    final String FROM = "/Users/kun/test/from/hhh";
    final String TO = "/Users/kun/test/to/hhh";
    final String LOCAL_IP = "192.168.1.22";


    @Test
    public void startReceiver() throws IOException {
        FileWriter writer = new FileWriter(TO);
        FileReceiver receiver = new FileReceiver(8888, writer, Paths.get(FROM).toFile().length());
        receiver.receive();

    }

    @Test
    public void startSender() throws IOException {
        FileSender sender = new FileSender(8888, FROM);
        sender.send();
    }

    final class FileSender {

        private final InetSocketAddress hostAddress;
        private final FileChannel channel;
        private SocketChannel client;

        public FileSender(final int port, String path) throws IOException {
            this.hostAddress = new InetSocketAddress(InetAddress.getByName(LOCAL_IP), port);
            this.channel = FileChannel.open(Paths.get(path), StandardOpenOption.READ);
            this.client = SocketChannel.open(this.hostAddress);
        }

        public void send() throws IOException {
            try {
                transfer(this.channel, 0l, this.channel.size());
            } finally {
                close();
            }
        }

        private void transfer(final FileChannel channel, long position, long size) throws IOException {
            assert !Objects.isNull(channel);

            while (position < size) {
                position += channel.transferTo(position, Integer.MAX_VALUE, this.client);
            }
        }

        SocketChannel getChannel() {
            return this.client;
        }

        private void close() throws IOException {
            this.client.close();
            this.channel.close();
        }
    }

    final class FileReceiver {

        private final int port;
        private final FileWriter fileWriter;
        private final long size;

        FileReceiver(final int port, final FileWriter fileWriter, final long size) {
            this.port = port;
            this.fileWriter = fileWriter;
            this.size = size;
        }

        public void receive() throws IOException {
            SocketChannel channel = null;

            try (final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
                serverSocketChannel.bind(new InetSocketAddress(this.port));
                channel = serverSocketChannel.accept();

                doTransfer(channel);
            } finally {
                if (!Objects.isNull(channel)) {
                    channel.close();
                }

                this.fileWriter.close();
            }
        }

        private void doTransfer(final SocketChannel channel) throws IOException {
            assert !Objects.isNull(channel);

            this.fileWriter.transfer(channel, this.size);
        }
    }

    final class FileWriter {

        private final FileChannel channel;

        FileWriter(final String path) throws IOException {
            if (StringUtils.isEmpty(path)) {
                throw new IllegalArgumentException("path required");
            }

            this.channel = FileChannel.open(Paths.get(path), StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);
        }

        void transfer(final SocketChannel channel, final long bytes) throws IOException {
            assert !Objects.isNull(channel);

            long position = 0l;
            while (position < bytes) {
                position += this.channel.transferFrom(channel, position, Integer.MAX_VALUE);
            }
        }

        int write(final ByteBuffer buffer, long position) throws IOException {
            assert !Objects.isNull(buffer);

            int bytesWritten = 0;
            while (buffer.hasRemaining()) {
                bytesWritten += this.channel.write(buffer, position + bytesWritten);
            }

            return bytesWritten;
        }

        void close() throws IOException {
            this.channel.close();
        }
    }
}
