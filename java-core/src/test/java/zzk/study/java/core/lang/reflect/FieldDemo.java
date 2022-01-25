package zzk.study.java.core.lang.reflect;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TODO:
 *
 * @author zhangzhongkun02
 * @date 2022/1/20 10:55 AM
 */
public class FieldDemo {
    @Test
    public void accessField() throws NoSuchFieldException, IllegalAccessException {
        final DemoClass c = new DemoClass();
        final Field numberField = DemoClass.class.getField("number");

        Object number = numberField.get(c);
        Assertions.assertTrue(number instanceof Integer);

        numberField.set(c, 1);
        assertEquals(DemoClass.number,1);
    }

    @Test
    public void accessPrivateField() throws NoSuchFieldException, IllegalAccessException {
        //无法通过getField获取private field
        Assertions.assertThrows(NoSuchFieldException.class, () -> {
            DemoClass.class.getField("l");
        });

        final Field privateField = DemoClass.class.getDeclaredField("l");
        assertTrue(Modifier.isPrivate(privateField.getModifiers()));

        final DemoClass o = new DemoClass();
        assertThrows(IllegalAccessException.class, ()->{
            privateField.set(o, 1);
        });

        if (!Modifier.isPublic(privateField.getModifiers())) {
            privateField.setAccessible(true);
        }
        privateField.set(o, 1L);
        assertEquals(1, o.getL());

    }

    public static class DemoClass{
        public static int number;
        private Long l;

        public Long getL() {
            return l;
        }
    }
}
