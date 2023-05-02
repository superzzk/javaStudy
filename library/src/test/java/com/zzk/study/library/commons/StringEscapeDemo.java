package com.zzk.study.library.commons;

import org.apache.commons.text.StringEscapeUtils;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.apache.commons.text.StringEscapeUtils.escapeJava;

/**
 * @author zhangzhongkun02
 * @date 2023/2/10 4:20 PM
 */
public class StringEscapeDemo {
    @Test
    public void escapeTest() throws FileNotFoundException {
        String a = "{\n" +
                "                    \"name\": \"FunTester\",\n" +
                "                    \"age\": \"30\"\n" +
                "                }";
        System.out.println(a);
        System.out.println(StringEscapeUtils.escapeJava(a));
    }

    @Test
    public void unescapeTest() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("string-escape-test").getFile());
        InputStream inputStream = new FileInputStream(file);
        String data = readFromInputStream(inputStream);
        System.out.println(data);
        final String rt = StringEscapeUtils.unescapeJava(data);
        System.out.println(rt);
    }

    private String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }

        return resultStringBuilder.toString();
    }
}
