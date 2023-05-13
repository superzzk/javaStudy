package zzk.study.java.core.net.uriurl;

import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UrlChecker {

    public int getResponseCodeForURL(String address) throws IOException {
        return getResponseCodeForURLUsing(address, "GET");
    }

    public int getResponseCodeForURLUsingHead(String address) throws IOException {
        return getResponseCodeForURLUsing(address, "HEAD");
    }

    private int getResponseCodeForURLUsing(String address, String method) throws IOException {
        HttpURLConnection.setFollowRedirects(false); // Set follow redirects to false
        final URL url = new URL(address);
        HttpURLConnection huc = (HttpURLConnection) url.openConnection();
        huc.setRequestMethod(method);
        return huc.getResponseCode();
    }
    @Test
    public void givenValidUrl_WhenUsingHEAD_ThenReturn200() throws IOException {
        int responseCode = getResponseCodeForURLUsingHead("http://www.example.com");
        assertEquals(200, responseCode);
    }

    @Test
    public void givenInvalidIUrl_WhenUsingHEAD_ThenReturn404() throws IOException {
        int responseCode = getResponseCodeForURLUsingHead("http://www.example.com/unkownurl");
        assertEquals(404, responseCode);
    }

    @Test
    public void givenValidUrl_WhenUsingGET_ThenReturn200() throws IOException {
        int responseCode = getResponseCodeForURL("http://www.example.com");
        assertEquals(200, responseCode);
    }

    @Test
    public void givenInvalidIUrl_WhenUsingGET_ThenReturn404() throws IOException {
        int responseCode = getResponseCodeForURL("http://www.example.com/unkownurl");
        assertEquals(404, responseCode);
    }
}
