package com.zzk.study.library.commons;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 * @author zhangzhongkun02
 * @date 2023/5/18 8:08 PM
 */
public class IOUtilsDemo {

    @Test
    public void toByteArray() throws IOException {
        String file = "";
        final byte[] bytes = IOUtils.toByteArray(new FileInputStream(file));
    }

}
