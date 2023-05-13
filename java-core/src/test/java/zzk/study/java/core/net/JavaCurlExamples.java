package zzk.study.java.core.net;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaCurlExamples {

    public static String inputStreamToString(InputStream inputStream) {
        final int bufferSize = 8 * 1024;
        byte[] buffer = new byte[bufferSize];
        final StringBuilder builder = new StringBuilder();
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, bufferSize)) {
            while (bufferedInputStream.read(buffer) != -1) {
                builder.append(new String(buffer));
            }
        } catch (IOException ex) {
            Logger.getLogger(JavaCurlExamples.class.getName()).log(Level.SEVERE, null, ex);
        }
        return builder.toString();
    }

    public static void consumeInputStream(InputStream inputStream) {
        inputStreamToString(inputStream);
    }


    @Test
    public void givenCommand_whenCalled_thenProduceZeroExitCode() throws IOException, InterruptedException {
        String command = "curl -X GET https://postman-echo.com/get?foo1=bar1&foo2=bar2";
        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
        processBuilder.directory(new File("/home/"));
        Process process = processBuilder.start();
        // wait process finish
        Thread.sleep(2000);
        InputStream inputStream = process.getInputStream();
        // Consume the inputStream so the process can exit
        consumeInputStream(inputStream);
        int exitCode = process.exitValue();

        Assert.assertEquals(0, exitCode);
    }

    @Test
    public void givenNewCommands_whenCalled_thenCheckIfIsAlive() throws IOException {
        String command = "curl -X GET https://postman-echo.com/get?foo1=bar1&foo2=bar2";
        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
        processBuilder.directory(new File("/home/"));
        Process process = processBuilder.start();

        // Re-use processBuilder
        processBuilder.command(new String[]{"newCommand", "arguments"});

        Assert.assertEquals(true, process.isAlive());
    }

    @Test
    public void whenRequestPost_thenCheckIfReturnContent() throws IOException {
        String command = "curl -X POST https://postman-echo.com/post --data foo1=bar1&foo2=bar2";
        Process process = Runtime.getRuntime().exec(command);

        // Get the POST result
        String content = JavaCurlExamples.inputStreamToString(process.getInputStream());

        Assert.assertTrue(null != content && !content.isEmpty());
    }

}
