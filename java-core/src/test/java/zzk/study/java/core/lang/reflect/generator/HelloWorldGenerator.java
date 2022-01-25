package zzk.study.java.core.lang.reflect.generator;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;

public class HelloWorldGenerator {
    public static void main(String[] args) {
        try {
            FileOutputStream fs = new FileOutputStream("HelloWorld.java");
            PrintWriter out = new PrintWriter(fs);
            out.println(
                    "class HelloWorld { \n"
                            + " public static void main( String[] args ) { \n"
                            + " System.out.println( \"Hello world!\" );\n"
                            + " } \n"
                            + "} "
            );
            out.flush();
            Process p = Runtime.getRuntime().exec("javac HelloWorld.java");
            p.waitFor();

            if (p.exitValue() == 0) {
                Class outputClassObject = Class.forName("HelloWorld");
                Class[] fpl = {String[].class};
                Method m = outputClassObject.getMethod("main", fpl);
                m.invoke(null, new Object[]{new String[]{}});
            } else {
                InputStream errStream = p.getErrorStream();
                for (int j = errStream.available(); j > 0; j--)
                    System.out.write(errStream.read());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}