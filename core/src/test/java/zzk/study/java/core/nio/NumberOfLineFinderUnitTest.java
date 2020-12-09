package zzk.study.java.core.nio;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class NumberOfLineFinderUnitTest {
    private static final String INPUT_FILE_NAME = "src/main/resources/input.txt";
    private static final int ACTUAL_LINE_COUNT = 45;

    @Test
    public void whenUsingBufferedReader_thenReturnTotalNumberOfLines() {
        int lines = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE_NAME))) {
            while (reader.readLine() != null) {
                lines++;
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        assertEquals(ACTUAL_LINE_COUNT, lines);
    }

    @Test
    public void whenUsingScanner_thenReturnTotalNumberOfLines() {
        int lines = 0;
        try (Scanner scanner = new Scanner(new FileReader(INPUT_FILE_NAME))) {
            while (scanner.hasNextLine()) {
                scanner.nextLine();
                lines++;
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        assertEquals(ACTUAL_LINE_COUNT, lines);
    }

    @Test
    public void whenUsingNIOFiles_thenReturnTotalNumberOfLines() {
        int lines = 0;
        try (Stream<String> fileStream = Files.lines(Paths.get(INPUT_FILE_NAME))) {
            lines = (int) fileStream.count();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        assertEquals(ACTUAL_LINE_COUNT, lines);
    }

    @Test
    public void whenUsingNIOFilesReadAllLines_thenReturnTotalNumberOfLines() {
        int lines = 0;
        try {
            List<String> fileStream = Files.readAllLines(Paths.get(INPUT_FILE_NAME));
            lines = fileStream.size();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        assertEquals(ACTUAL_LINE_COUNT, lines);
    }

    @Test
    public void whenUsingNIOFileChannel_thenReturnTotalNumberOfLines() {
        int lines = 1;
        try (FileChannel channel = FileChannel.open(Paths.get(INPUT_FILE_NAME), StandardOpenOption.READ)) {
            ByteBuffer byteBuffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
            while (byteBuffer.hasRemaining()) {
                byte currentChar = byteBuffer.get();
                if (currentChar == '\n') {
                    lines++;
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        assertEquals(ACTUAL_LINE_COUNT, lines);
    }

    @Test
    public void whenUsingApacheCommonsIO_thenReturnTotalNumberOfLines() {
        int lines = 0;
        try {
            LineIterator lineIterator = FileUtils.lineIterator(new File(INPUT_FILE_NAME));
            while (lineIterator.hasNext()) {
                lineIterator.nextLine();
                lines++;
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        assertEquals(ACTUAL_LINE_COUNT, lines);
    }

    @Test
    public void whenUsingGoogleGuava_thenReturnTotalNumberOfLines() {
        int lines = 0;
        try {
            List<String> lineItems = com.google.common.io.Files.readLines(Paths.get(INPUT_FILE_NAME)
                    .toFile(), Charset.defaultCharset());
            lines = lineItems.size();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        assertEquals(ACTUAL_LINE_COUNT, lines);
    }

}
