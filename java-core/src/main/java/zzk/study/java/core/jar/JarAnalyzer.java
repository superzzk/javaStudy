package zzk.study.java.core.jar;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * TODO:
 *
 * @author zhangzhongkun02
 * @date 2022/12/4 8:59 PM
 */
public class JarAnalyzer {
    /**
     * jar 文件路径
     */
    private String jarFilePath;

    /**
     * 构造函数
     * @param jarFilePath 文件路径
     */
    public JarAnalyzer(String jarFilePath) {
        this.jarFilePath = jarFilePath;
    }

    /**
     * 获取jar包属性
     * @return jar包所有属性
     * @throws IOException
     */
    public Map<String, String> getJarAttrs() throws IOException {
        URL url = new File(this.jarFilePath).toURI().toURL();
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{url});
        URL manifestUrl = urlClassLoader.findResource("META-INF/MANIFEST.MF");
        Manifest manifest = new Manifest(manifestUrl.openStream());

        Attributes mainAttributes = manifest.getMainAttributes();
        Map<String, String> attrs = new HashMap<>();

        mainAttributes.forEach((key, value) -> {
            attrs.put(String.valueOf(key), String.valueOf(value));
        });
        return attrs;
    }

    /**
     * 获取入口类全路径名
     * @return 入口类全路径名
     * @throws IOException
     */
    public String getProgamClass() throws IOException {
        for (String key : getJarAttrs().keySet()) {
            if ("program-class".equals(key)) {
                return getJarAttrs().get(key);
            }
        }
        return null;
    }

    /**
     * 获取jar包所有类名
     *
     * @return jar包所有类名
     * @throws IOException
     */
    public static Set<String> getClassNamesFromJarFile(File jarFilePath) throws IOException {
        Set<String> classNames = new HashSet<>();

        try (JarFile jarFile = new JarFile(jarFilePath)) {
            Enumeration<JarEntry> e = jarFile.entries();
            while (e.hasMoreElements()) {
                JarEntry jarEntry = e.nextElement();
                if (jarEntry.getName().endsWith(".class")) {
                    String className = jarEntry.getName()
                            .replace("/", ".")
                            .replace(".class", "");
                    classNames.add(className);
                }
            }
            return classNames;
        }
    }

    public static Set<Class> getClassesFromJarFile(File jarFile) throws IOException, ClassNotFoundException {
        Set<String> classNames = getClassNamesFromJarFile(jarFile);
        Set<Class> classes = new HashSet<>(classNames.size());
        /*
        * jar file example:
        * jar:http://www.example.com/some_jar_file.jar!/
        * jar:file:/local/path/to/some_jar_file.jar!/
        * jar:file:/C:/windows/path/to/some_jar_file.jar!/
        * */
        try (URLClassLoader cl = URLClassLoader.newInstance(
                new URL[] { new URL("jar:file:" + jarFile + "!/") })) {
            for (String name : classNames) {
                // Load the class by its name
                Class clazz = cl.loadClass(name);
                classes.add(clazz);
            }
        }
        return classes;
    }
}
