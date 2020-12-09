package zzk.study.java.core.security;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.Checksum;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChecksumUtilsUnitTest {

    byte[] arr;

    @Before
    void setUp() {
        arr =  new byte[]{0,10,21,20,35,40,120,56,72,22};
    }

    @Test
    public void givenByteArray_whenChecksumCreated_checkCorrect() {

        long checksum = ChecksumUtils.getChecksumCRC32(arr);

        assertEquals(3915397664L, checksum);
    }

    @Test
    public void givenTwoDifferentStrings_whenChecksumCreated_checkCollision() {

        String plumless = "plumless";
        String buckeroo = "buckeroo";

        long plumlessChecksum = ChecksumUtils.getChecksumCRC32(plumless.getBytes());
        long buckerooChecksum = ChecksumUtils.getChecksumCRC32(buckeroo.getBytes());

        assertEquals(plumlessChecksum, buckerooChecksum);
    }

    @Test
    public void givenInputString_whenChecksumCreated_checkCorrect() throws IOException {

        InputStream inputStream = new ByteArrayInputStream(arr);
        long checksum = ChecksumUtils.getChecksumCRC32(inputStream, 10);

        assertEquals(3915397664L, checksum);

    }

    public static class ChecksumUtils {

        public static long getChecksumCRC32(byte[] bytes) {
            Checksum crc32 = new CRC32();
            crc32.update(bytes, 0, bytes.length);
            return crc32.getValue();
        }

        public static long getChecksumCRC32(InputStream stream, int bufferSize) throws IOException {
            CheckedInputStream checkedInputStream = new CheckedInputStream(stream, new CRC32());
            byte[] buffer = new byte[bufferSize];
            while (checkedInputStream.read(buffer, 0, buffer.length) >= 0) {}
            return checkedInputStream.getChecksum().getValue();
        }
    }

}