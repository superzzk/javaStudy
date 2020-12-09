package zzk.study.java.core.security.cipher;

import org.apache.commons.codec.binary.Hex;
import org.junit.Before;
import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import static org.assertj.core.api.Assertions.assertThat;

public class CipherDemo {
    private String encKeyString;
    private String message;
    private String  certificateString;

    @Before
    public void init(){
        encKeyString =  "1234567890123456";
        message = "This is a secret message";
        certificateString = "-----BEGIN CERTIFICATE-----\n" +
                "MIICVjCCAb8CAg37MA0GCSqGSIb3DQEBBQUAMIGbMQswCQYDVQQGEwJKUDEOMAwG\n" +
                "A1UECBMFVG9reW8xEDAOBgNVBAcTB0NodW8ta3UxETAPBgNVBAoTCEZyYW5rNERE\n" +
                "MRgwFgYDVQQLEw9XZWJDZXJ0IFN1cHBvcnQxGDAWBgNVBAMTD0ZyYW5rNEREIFdl\n" +
                "YiBDQTEjMCEGCSqGSIb3DQEJARYUc3VwcG9ydEBmcmFuazRkZC5jb20wHhcNMTIw\n" +
                "ODIyMDUyNzIzWhcNMTcwODIxMDUyNzIzWjBKMQswCQYDVQQGEwJKUDEOMAwGA1UE\n" +
                "CAwFVG9reW8xETAPBgNVBAoMCEZyYW5rNEREMRgwFgYDVQQDDA93d3cuZXhhbXBs\n" +
                "ZS5jb20wgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAMYBBrx5PlP0WNI/ZdzD\n" +
                "+6Pktmurn+F2kQYbtc7XQh8/LTBvCo+P6iZoLEmUA9e7EXLRxgU1CVqeAi7QcAn9\n" +
                "MwBlc8ksFJHB0rtf9pmf8Oza9E0Bynlq/4/Kb1x+d+AyhL7oK9tQwB24uHOueHi1\n" +
                "C/iVv8CSWKiYe6hzN1txYe8rAgMBAAEwDQYJKoZIhvcNAQEFBQADgYEAASPdjigJ\n" +
                "kXCqKWpnZ/Oc75EUcMi6HztaW8abUMlYXPIgkV2F7YanHOB7K4f7OOLjiz8DTPFf\n" +
                "jC9UeuErhaA/zzWi8ewMTFZW/WshOrm3fNvcMrMLKtH534JKvcdMg6qIdjTFINIr\n" +
                "evnAhf0cwULaebn+lMs8Pdl7y37+sfluVok=\n" +
                "-----END CERTIFICATE-----";
    }

    @Test
    public void givenEncryptionKey_whenMessageIsPassedToEncryptor_thenMessageIsEncrypted() throws Exception {

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKey secretKey = new SecretKeySpec(encKeyString.getBytes(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedMessage = cipher.doFinal(message.getBytes());

        System.out.println(encryptedMessage.length);
        System.out.println(Hex.encodeHexString(encryptedMessage));
        assertThat(encryptedMessage).isNotNull();
        assertThat(encryptedMessage.length  % 32).isEqualTo(0);
    }

    @Test
    public void givenCertificateWithPublicKey_whenMessageIsPassedToEncryptor_thenMessageIsEncrypted() throws Exception {
        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        InputStream is = new ByteArrayInputStream(certificateString.getBytes());
        X509Certificate certificate = (X509Certificate) factory.generateCertificate(is);

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, certificate);
        byte[] encryptedMessage = cipher.doFinal(message.getBytes());

        System.out.println(encryptedMessage.length);
        System.out.println(Hex.encodeHexString(encryptedMessage));
        assertThat(encryptedMessage).isNotNull();
        assertThat(encryptedMessage.length  % 128).isEqualTo(0);
    }

    @Test
    public void givenEncryptionKey_whenMessageIsEncrypted_thenDecryptMessage() throws Exception{

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKey secretKey = new SecretKeySpec(encKeyString.getBytes(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedMessage = cipher.doFinal(message.getBytes());

        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] clearMessage = cipher.doFinal(encryptedMessage);

        assertThat(message).isEqualTo(new String(clearMessage));
    }
}
