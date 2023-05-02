package com.zzk.study.library.commons;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * TODO:
 *
 * @author zhangzhongkun02
 * @date 2023/4/20 8:41 PM
 */
public class FileUtilsDemo {
    @Test
    public void givenFileName_whenUsingFileUtils_thenFileData() throws IOException {
        String expectedData = "Hello, world!";

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("commons/fileTest.txt").getFile());
        String data = FileUtils.readFileToString(file, "UTF-8");

        assertEquals(expectedData, data.trim());
    }
}
