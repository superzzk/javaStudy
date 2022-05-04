package zzk.study.java.core.nio;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

public class RemoteFileCopy {

    final class FileReader {

        private final FileChannel channel;
        private final FileSender sender;

        FileReader(final FileSender sender, final String path) throws IOException {
            if (Objects.isNull(sender) || StringUtils.isEmpty(path)) {
                throw new IllegalArgumentException("sender and path required");
            }

            this.sender = sender;
            this.channel = FileChannel.open(Paths.get(path), StandardOpenOption.READ);
        }

        void read() throws IOException {
            try {
                transfer();
            } finally {
                close();
            }
        }

        void close() throws IOException {
            this.sender.close();
            this.channel.close();
        }

        private void  transfer() throws IOException {
            this.sender.transfer(this.channel, 0l, this.channel.size());
        }
    }

    final class FileSender {

        private final InetSocketAddress hostAddress;
        private SocketChannel client;

        FileSender(final int port) throws IOException {
            this.hostAddress = new InetSocketAddress(port);
            this.client = SocketChannel.open(this.hostAddress);
        }

        void transfer(final FileChannel channel, long position, long size) throws IOException {
            assert !Objects.isNull(channel);

            while (position < size) {
                position += channel.transferTo(position, Integer.MAX_VALUE, this.client);
            }
        }

        SocketChannel getChannel() {
            return this.client;
        }

        void close() throws IOException {
            this.client.close();
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

        void receive() throws IOException {
            SocketChannel channel = null;

            try (final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
                init(serverSocketChannel);

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

        private void init(final ServerSocketChannel serverSocketChannel) throws IOException {
            assert !Objects.isNull(serverSocketChannel);

            serverSocketChannel.bind(new InetSocketAddress(this.port));
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
            while(buffer.hasRemaining()) {
                bytesWritten += this.channel.write(buffer, position + bytesWritten);
            }

            return bytesWritten;
        }

        void close() throws IOException {
            this.channel.close();
        }
    }
}
