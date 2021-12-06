package zzk.study.java.core.security.encrypt;

import org.junit.Test;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class FileEncrypterDecrypterIntegrationTest {

    @Test
    public void givenStringAndFilename_whenEncryptingIntoFile_andDecryptingFileAgain_thenOriginalStringIsReturned() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, InvalidAlgorithmParameterException {
        String originalContent = "foobar";
        SecretKey secretKey = KeyGenerator.getInstance("AES").generateKey();

        FileEncrypterDecrypter fileEncrypterDecrypter = new FileEncrypterDecrypter(secretKey, "AES/CBC/PKCS5Padding");
        fileEncrypterDecrypter.encrypt(originalContent, "baz.enc");

        String decryptedContent = fileEncrypterDecrypter.decrypt("baz.enc");
        assertThat(decryptedContent, is(originalContent));

        new File("baz.enc").delete(); // cleanup
    }

    static class FileEncrypterDecrypter {

        private SecretKey secretKey;
        private Cipher cipher;

        FileEncrypterDecrypter(SecretKey secretKey, String cipher) throws NoSuchPaddingException, NoSuchAlgorithmException {
            this.secretKey = secretKey;
            this.cipher = Cipher.getInstance(cipher);
        }

        void encrypt(String content, String fileName) throws InvalidKeyException, IOException {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] iv = cipher.getIV();

            try (
                    FileOutputStream fileOut = new FileOutputStream(fileName);
                    CipherOutputStream cipherOut = new CipherOutputStream(fileOut, cipher)
            ) {
                fileOut.write(iv);
                cipherOut.write(content.getBytes());
            }

        }

        String decrypt(String fileName) throws InvalidAlgorithmParameterException, InvalidKeyException, IOException {

            String content;

            try (FileInputStream fileIn = new FileInputStream(fileName)) {
                byte[] fileIv = new byte[16];
                fileIn.read(fileIv);
                cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(fileIv));

                try (
                        CipherInputStream cipherIn = new CipherInputStream(fileIn, cipher);
                        InputStreamReader inputReader = new InputStreamReader(cipherIn);
                        BufferedReader reader = new BufferedReader(inputReader)
                ) {

                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    content = sb.toString();
                }

            }
            return content;
        }
    }

}
