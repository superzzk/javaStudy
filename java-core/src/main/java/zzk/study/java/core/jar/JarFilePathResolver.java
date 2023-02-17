package zzk.study.java.core.jar;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

/**
 * Problem: Let's say we have the class instance of Guava‘s Ascii class.
 * We want to create a method to find out the full path of the JAR file that holds the Ascii class.
 */
public class JarFilePathResolver {
    private static final String JAR_FILE_PREFIX = "jar:file:";
    /**
     * method 1, 存在Security检测抛异常问题
     * return jar file path which contains the given class
     * */
    public static String byGetProtectionDomain(Class clazz) throws URISyntaxException {
        URL url = clazz.getProtectionDomain().getCodeSource().getLocation();
        return Paths.get(url.toURI()).toString();
    }

    /**
     * method 2, better
     * return jar file path which contains the given class
     * */
    public static String byGetResource(Class clazz) {
        URL classResource = clazz.getResource(clazz.getSimpleName() + ".class");
        if (classResource == null) {
            throw new RuntimeException("class resource is null");
        }
        String url = classResource.toString();
        if (url.startsWith(JAR_FILE_PREFIX)) {
            // extract 'file:......jarName.jar' part from the url string
            String path = url.replaceAll("^jar:(file:.*[.]jar)!/.*", "$1");
            try {
                return Paths.get(new URL(path).toURI()).toString();
            } catch (Exception e) {
                throw new RuntimeException("Invalid Jar File URL String");
            }
        }
        throw new RuntimeException("Invalid Jar File URL String");
    }
}
