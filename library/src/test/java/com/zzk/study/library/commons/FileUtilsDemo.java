package com.zzk.study.library.commons;

import org.junit.Test;
import shaded.org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.junit.Assert.assertEquals;

/**
 * @author zhangzhongkun02
 * @date 2023/4/20 8:41 PM
 */
public class FileUtilsDemo {
    @Test
    public void readFileToString() throws IOException {
        String expectedData = "Hello, world!";

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("commons/fileTest.txt").getFile());
        String data = FileUtils.readFileToString(file, "UTF-8");

        assertEquals(expectedData, data.trim());
    }

    public static void downloadWithJavaIO(String url, String localFilename) {

        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream()); FileOutputStream fileOutputStream = new FileOutputStream(localFilename)) {

            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void downloadWithJava7IO(String url, String localFilename) {

        try (InputStream in = new URL(url).openStream()) {
            Files.copy(in, Paths.get(localFilename), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void downloadWithJavaNIO(String fileURL, String localFilename) throws IOException {
        URL url = new URL(fileURL);
        try (ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(localFilename); FileChannel fileChannel = fileOutputStream.getChannel()) {

            fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            fileOutputStream.close();
        }
    }

    public static void downloadWithApacheCommons(String url, String localFilename) {
        int CONNECT_TIMEOUT = 10000;
        int READ_TIMEOUT = 10000;
        try {
            FileUtils.copyURLToFile(new URL(url), new File(localFilename), CONNECT_TIMEOUT, READ_TIMEOUT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
