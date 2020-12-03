package org.example.classloader;

import org.junit.Test;

public class PrintClassLoaderUnitTest {
    @Test(expected = ClassNotFoundException.class)
    public void givenAppClassLoader_whenParentClassLoader_thenClassNotFoundException() throws Exception {
        PrintClassLoader sampleClassLoader = (PrintClassLoader) Class.forName(PrintClassLoader.class.getName()).newInstance();
        sampleClassLoader.printClassLoaders();

        // not found exception
        Class.forName(PrintClassLoader.class.getName(), true, PrintClassLoader.class.getClassLoader().getParent());
    }
}