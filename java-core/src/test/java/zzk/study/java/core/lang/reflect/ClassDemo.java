package zzk.study.java.core.lang.reflect;

import org.apache.commons.math3.stat.inference.GTest;
import org.assertj.core.internal.IterableElementComparisonStrategy;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TODO:
 *
 * @author zhangzhongkun02
 * @date 2022/1/19 7:58 PM
 */
public class ClassDemo {

    @Test
    public void getNameTest() {
        System.out.println("name:" + DemoClass.class.getName());
        System.out.println("canonical name:"+DemoClass.class.getCanonicalName());
        System.out.println("type name:"+DemoClass.class.getTypeName());
    }
    @Test
    public void getMethodTest() throws NoSuchMethodException {
        Method m = DemoClass.class.getMethod("primitiveParam", int.class);
        System.out.println(m);

        m = DemoClass.class.getMethod( "collectionParam", Collection.class);
        System.out.println(m);

        m = DemoClass.class.getMethod( "arrayParam", Object[].class);
        System.out.println(m);

        // 无法获取父类的protected方法
        Assertions.assertThrows(NoSuchMethodException.class, ()->{
            DemoClass.class.getMethod("parentProtectedMethod");
        });

        Method parentProtectedMethod = Utils.getSupportedMethod(DemoClass.class, "parentProtectedMethod", null);
        Assert.assertEquals(DemoParentClass.class, parentProtectedMethod.getDeclaringClass());
    }

    @Test
    public void isArrayTest(){
        Assert.assertTrue(Object[].class.isArray());
        Assert.assertFalse(Object.class.isArray());
    }

    @Test
    public void isInterfaceTest(){
        Assert.assertTrue(DemoInterface.class.isInterface());
        Assert.assertFalse(DemoClass.class.isInterface());
    }

    @Test
    public void isPrimitive(){
        Assert.assertTrue(int.class.isPrimitive());
        Assert.assertFalse(Object.class.isPrimitive());
    }

    @Test
    public void getComponentTypeTest(){
        Class<?> componentType = Object[][].class.getComponentType();
        Assert.assertEquals(Object[].class, componentType);
        Assert.assertEquals(Object.class, componentType.getComponentType());
    }

    @Test
    public void isAssignableFromTest(){
        assertTrue(Object.class.isAssignableFrom(String.class));
        assertTrue(java.util.List.class.isAssignableFrom(java.util.Vector.class));
        assertTrue(double.class.isAssignableFrom(double.class));
        assertFalse(Object.class.isAssignableFrom(double.class));

        assertTrue(Object.class.isAssignableFrom(Class.class));
        assertFalse(Class.class.isAssignableFrom(Object.class));
    }

    @Test
    public void isInstanceTest(){
        DemoClass demoClass = new DemoClass();
        Assert.assertTrue(DemoParentClass.class.isInstance(demoClass));

        // fun
        assertTrue(Class.class.isInstance(Class.class));

        assertTrue(Class.class.isInstance(Object.class));
        assertTrue(Object.class.isInstance(Class.class));
    }

    @Test
    public void newInstanceTest() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        //无法创建非static内部类
        assertThrows(InstantiationException.class, ()->{
                Class.forName("zzk.study.java.core.lang.reflect.ClassDemo$DemoClass").newInstance();
        });

        final Object o = Class.forName("zzk.study.java.core.lang.reflect.ClassDemo$StaticDemoClass").newInstance();
        assertTrue(o instanceof StaticDemoClass);
    }

    @Test
    public void forNameTest() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        assertEquals("[Ljava.lang.String;", String[].class.getName());
        assertEquals("char",char.class.getName());
        assertEquals("[C",char[].class.getName());
        assertEquals("byte",byte.class.getName());
        assertEquals("[B",byte[].class.getName());
        assertEquals("int",int.class.getName());
        assertEquals("[I",int[].class.getName());


        assertThrows(ClassNotFoundException.class, ()->{
            Class.forName(char.class.getName());
        });

    }

    public class DemoClass extends DemoParentClass{
        private int intField;

        public synchronized Object primitiveParam( int index ){return null;}
        public boolean collectionParam(Collection c){return true;}
        public void arrayParam( Object[] anArray ) {}

        public DemoClass(){}

        public DemoClass(int intField) {
            this.intField = intField;
        }

        public int getIntField() {
            return intField;
        }
    }

    public class DemoParentClass{
        protected void parentProtectedMethod(){};
    }

    public interface DemoInterface {
        void test();
    }

    public static class StaticDemoClass{}
}
