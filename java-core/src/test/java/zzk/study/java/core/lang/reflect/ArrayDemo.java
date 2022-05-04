package zzk.study.java.core.lang.reflect;

import lombok.val;
import org.junit.Test;

import java.lang.reflect.Array;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TODO:
 *
 * @author zhangzhongkun02
 * @date 2022/1/20 11:53 AM
 */
public class ArrayDemo {

    @Test
    public void getLengthTest(){
        final DemoClass[] objs = new DemoClass[]{new DemoClass()};
        final int length = Array.getLength(objs);
        assertEquals(1, length);
        assertEquals(objs[0], Array.get(objs,0));
    }

    @Test
    public void newInstanceTest(){
        final Object arr = Array.newInstance(DemoClass.class, 3);
        assertTrue(arr.getClass().isArray());
        assertEquals(3, Array.getLength(arr));
        assertNull(Array.get(arr, 0));

        Array.set(arr, 0, new DemoClass());
        assertNotNull(Array.get(arr,0));

        // 创建二维数组
        val arr2 = Array.newInstance(String[].class, 5);
        assertTrue(arr2.getClass().isArray());
        assertEquals(5, Array.getLength(arr2));

        /*
        The top-level array is a length-two array of String arrays with each component initialized to an array of String of
        length 3. Each second-level array has its elements initialized to null
        */
        final val arr3 = Array.newInstance(String.class, new int[]{2, 3});
        assertEquals(2, Array.getLength(arr3));

        // a three-dimensional array of String with the top two dimensions initialized
        //and the third set to null
        final Object arr4 = Array.newInstance(String[].class, new int[]{2, 3});
        assertEquals(2, Array.getLength(arr4));

    }

    @Test
    public void setTest(){
        int[] intArr = new int[]{1,2,3};
        assertEquals(1, Array.getInt(intArr, 0));
        Array.setInt(intArr, 0, -1);
        assertEquals(-1, intArr[0]);
    }

    public static class DemoClass{
        public boolean bVal;
    }
}
