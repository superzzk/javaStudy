package zzk.study.java.core.security.hashing;

import com.google.common.hash.Hashing;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;

public class SHA256HashingUnitTest {

    private static String originalValue = "abc123";
    private static String hashedValue = "6ca13d52ca70c883e0f0bb101e425a89e8624de51db2d2392593af6a84118090";

    @Test
    public void testHashWithJavaMessageDigest() throws Exception {
        final String currentHashedValue = SHA256Hashing.HashWithJavaMessageDigest(originalValue);
        assertEquals(hashedValue, currentHashedValue);
    }

    @Test
    public void testHashWithGuava() throws Exception {
        final String currentHashedValue = SHA256Hashing.hashWithGuava(originalValue);
        assertEquals(hashedValue, currentHashedValue);
    }

    @Test
    public void testHashWithApacheCommans() throws Exception {
        final String currentHashedValue = SHA256Hashing.HashWithApacheCommons(originalValue);
        assertEquals(hashedValue, currentHashedValue);
    }

    public static class SHA256Hashing {

        public static final String SHA_256 = "SHA-256";

        public static String HashWithJavaMessageDigest(final String originalString) throws NoSuchAlgorithmException {
            final MessageDigest digest = MessageDigest.getInstance(SHA_256);
            final byte[] encodedhash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedhash);
        }

        public static String hashWithGuava(final String originalString) {
            final String sha256hex = Hashing.sha256().hashString(originalString, StandardCharsets.UTF_8).toString();
            return sha256hex;
        }

        public static String HashWithApacheCommons(final String originalString) {
            final String sha256hex = DigestUtils.sha256Hex(originalString);
            return sha256hex;
        }

        public static String bytesToHex(byte[] hash) {
            StringBuffer hexString = new StringBuffer();
            for (byte h : hash) {
                String hex = Integer.toHexString(0xff & h);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }

    }

}