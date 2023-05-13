package zzk.study.java.core.net.uriurl;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class URIDemo {
    private final Logger logger = LoggerFactory.getLogger(URIDemo.class);

    String URISTRING = "https://wordpress.org:443/support/topic/page-jumps-within-wordpress/?replies=3#post-2278484";
    // parsed locator
    String URISCHEME = "https";
    String URIHOST = "wordpress.org";
    static String URIAUTHORITY = "wordpress.org:443";
    String URIPATH = "/support/topic/page-jumps-within-wordpress/";
    int URIPORT = 443;
    String URIQUERY = "replies=3";
    String URIFRAGMENT = "post-2278484";
    String URICOMPOUND = URISCHEME + "://" + URIHOST + ":" + URIPORT + URIPATH + "?" + URIQUERY + "#" + URIFRAGMENT;

    private String getParsedPieces(URI uri) {
        logger.info("parse uri:{}", uri);
        logger.info("scheme: " + uri.getScheme());
        logger.info("scheme specific part: " + uri.getSchemeSpecificPart());
        logger.info("authority: " + uri.getAuthority());
        logger.info("host: " + uri.getHost());
        logger.info("path: " + uri.getPath());
        logger.info("port: " + uri.getPort());
        logger.info("query: " + uri.getQuery());
        logger.info("fragment: " + uri.getFragment());

        URL url = null;
        try {
            url = uri.toURL();
        } catch (MalformedURLException e) {
            logger.info("MalformedURLException thrown: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            logger.info("IllegalArgumentException thrown: " + e.getMessage());
            e.printStackTrace();
        }
        return url.toString();
    }

    public String testURIAsNew(String URIString) {
        URI uri;
        // creating URI object
        try {
            uri = new URI(URIString);
        } catch (URISyntaxException e) {
            logger.info("URISyntaxException thrown: " + e.getMessage());
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
        return getParsedPieces(uri);
    }

    public String testURIAsCreate(String URIString) {
        // creating URI object
        URI uri = URI.create(URIString);
        return getParsedPieces(uri);
    }

    public static void main(String[] args) throws Exception {
        URIDemo demo = new URIDemo();
        String contentCreate = demo.testURIAsCreate(demo.URICOMPOUND);
        demo.logger.info("result url:" + contentCreate);
        String contentNew = demo.testURIAsNew(demo.URICOMPOUND);
        demo.logger.info(contentNew);
    }

    @Test
    public void givenURI_whenURIIsParsed_thenSuccess() throws URISyntaxException {
        URI uri = new URI(URICOMPOUND);
        assertEquals("URI string is not equal", uri.toString(), URISTRING);
        assertEquals("Scheme is not equal", uri.getScheme(), URISCHEME);
        assertEquals("Authority is not equal", uri.getAuthority(), URIAUTHORITY);
        assertEquals("Host string is not equal", uri.getHost(), URIHOST);
        assertEquals("Path string is not equal", uri.getPath(), URIPATH);
        assertEquals("Port number is not equal", uri.getPort(), URIPORT);
        assertEquals("Query string is not equal", uri.getQuery(), URIQUERY);
        assertEquals("Fragment string is not equal", uri.getFragment(), URIFRAGMENT);
    }
}
