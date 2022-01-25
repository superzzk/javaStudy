package zzk.study.java.core.lang.reflect;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TODO:
 *
 * @author zhangzhongkun02
 * @date 2022/1/21 4:48 PM
 */
public class ConstructorDemo {

    @Test
    public void getConstructor() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // 内部非静态类失败
        assertThrows(NoSuchMethodException.class, ()->{
            DemoClass.class.getConstructor(int.class);
        });

        //成功
        final Constructor<StaticDemoClass> constructor = StaticDemoClass.class.getConstructor(int.class);
        final StaticDemoClass o = constructor.newInstance(1);
        assertEquals(1, o.getIntField());
    }

    public class DemoClass {
        private int intField;

        public DemoClass(){}

        public DemoClass(int intField) {
            this.intField = intField;
        }

        public int getIntField() {
            return intField;
        }
    }

    public static class StaticDemoClass {
        private int intField;

        public StaticDemoClass(){}

        public StaticDemoClass(int intField) {
            this.intField = intField;
        }

        public int getIntField() {
            return intField;
        }
    }
}
