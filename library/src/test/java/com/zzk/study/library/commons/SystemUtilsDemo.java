package com.zzk.study.library.commons;

import org.apache.commons.lang3.JavaVersion;
import org.apache.commons.lang3.SystemUtils;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * TODO:
 *
 * @author zhangzhongkun02
 * @date 2023/6/18 8:20 PM
 */
public class SystemUtilsDemo {
    @Test
    public void getJavaHome() {
        assertThat(SystemUtils.getJavaHome()).isEqualTo(new File("path/to/java/jdk"));
    }

    @Test
    public void getUserHome() {
        assertThat(SystemUtils.getUserHome()).isEqualTo(new File("path/to/user/home"));
    }

    @Test
    public void isJavaVersionAtLeast() {
        assertThat(SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_RECENT)).isTrue();
    }
}
