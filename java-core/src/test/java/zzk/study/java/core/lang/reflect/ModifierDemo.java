package zzk.study.java.core.lang.reflect;

import lombok.val;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Modifier;
import java.util.List;

/**
 * TODO:
 *
 * @author zhangzhongkun02
 * @date 2022/1/20 10:39 AM
 */
public class ModifierDemo {
    @Test
    public void demo() throws NoSuchMethodException, NoSuchFieldException {
        //接口中的方法默认abstract
        Assertions.assertTrue(Modifier.isAbstract(List.class.getMethod("size").getModifiers()));

        val numberField = DemoClass.class.getField("number");
        Assertions.assertTrue(Modifier.isStatic(numberField.getModifiers()));

    }



    public static class DemoClass{
        public static int number;
        private Long l;
    }
}
