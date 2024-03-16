package com.zzk.study.library.commons;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * TODO:
 *
 * @author zhangzhongkun02
 * @date 2023/6/18 8:14 PM
 */
public class ArrayUtilsDemo {
    @Test
    public void toString_() {
        String[] array = {"a", "b", "c"};
        assertThat(ArrayUtils.toString(array)).isEqualTo("{a,b,c}");
    }

    @Test
    public void toString_null() {
        assertThat(ArrayUtils.toString(null, "Array is null")).isEqualTo("Array is null");
    }

    @Test
    public void hashcode() {
        String[] array = {"a", "b", "c"};
        assertThat(ArrayUtils.hashCode(array)).isEqualTo(997619);
    }

    @Test
    public void toMap() {
        String[][] array = {{"1", "one", }, {"2", "two", }, {"3", "three"}};
        Map map = new HashMap();
        map.put("1", "one");
        map.put("2", "two");
        map.put("3", "three");
        assertThat(ArrayUtils.toMap(array)).isEqualTo(map);
    }
    @Test
    public void isSameLength() {
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2, 3};
        assertThat(ArrayUtils.isSameLength(array1, array2)).isTrue();
    }

    @Test
    public void indexOf() {
        int[] array = {1, 2, 3};
        assertThat(ArrayUtils.indexOf(array, 1, 0)).isEqualTo(0);
    }
}
