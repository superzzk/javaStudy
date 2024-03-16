package com.zzk.study.library.commons;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * TODO:
 *
 * @author zhangzhongkun02
 * @date 2023/6/18 8:18 PM
 */
public class NumnberUtilsDemo {

    @Test
    public void compare_int() {
        assertThat(NumberUtils.compare(1, 1)).isEqualTo(0);
    }

    @Test
    public void compare_long() {
        assertThat(NumberUtils.compare(1L, 1L)).isEqualTo(0);
    }
    @Test
    public void createNumber() {
        assertThat(NumberUtils.createNumber("123456"))
                .isEqualTo(123456);
    }

    @Test
    public void isDigits() {
        assertThat(NumberUtils.isDigits("123456")).isTrue();
    }
    @Test
    public void max() {
        int[] array = {1, 2, 3, 4, 5, 6};
        assertThat(NumberUtils.max(array)).isEqualTo(6);
    }

    @Test
    public void min() {
        int[] array = {1, 2, 3, 4, 5, 6};
        assertThat(NumberUtils.min(array)).isEqualTo(1);
    }

}
