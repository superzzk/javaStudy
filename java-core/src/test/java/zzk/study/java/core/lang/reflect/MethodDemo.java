package zzk.study.java.core.lang.reflect;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * TODO:
 *
 * @author zhangzhongkun02
 * @date 2022/1/19 8:36 PM
 */
public class MethodDemo {
    @Test
    public void invokeTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        DemoClass c = new DemoClass();
        Method m = c.getClass().getMethod("primitiveReturnObject", int.class);
        Object result = m.invoke(c, 0);
        Assert.assertNull(result);

        m = c.getClass().getMethod("intReturnInt", int.class);
        result = m.invoke(c, 0);
        Assert.assertEquals(Integer.class, result.getClass());
        Assert.assertEquals(0, 0);

        m = c.getClass().getMethod("noArgument");
        Assert.assertEquals(void.class, m.getReturnType());

        //无法获取private方法
        Assertions.assertThrows(NoSuchMethodException.class, () -> {
            c.getClass().getMethod("privateMethod");
        });

    }


    public class DemoClass {
        public Object primitiveReturnObject( int index ){return null;}
        public int intReturnInt( int index ){return index;}
        public void noArgument(){}

        private int privateMethod(){return 0;}
        public boolean collectionParam(Collection c){return true;}
        public void arrayParam( Object[] anArray ) {}
    }
}
