package zzk.study.java.core.net.download;

import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileDownload {

    public static void downloadWithJavaIO(String url, String localFilename) {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(localFilename)) {

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

    @Test
    public void download_to_mem(){
        String url = "http://mss.vip.sankuai.com:80/v1/mss_bc10d2fc3ee94a4bb81ee1fc1dce8a8a/magic/570533_0ca45a03-ef87-42ec-b37c-c96ac44da97b_1684357825556.txt";


    }
}
