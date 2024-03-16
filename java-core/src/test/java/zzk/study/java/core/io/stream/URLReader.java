package zzk.study.java.core.io.stream;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * TODO:
 *
 * @author zhangzhongkun02
 * @date 2023/5/18 8:00 PM
 */
public class URLReader {
    public static void main(String[] args) throws Exception {
        URL oracle = new URL("http://www.oracle.com/");
        BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();
    }
}
