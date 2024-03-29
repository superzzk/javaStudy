package zzk.study.java.core.lang.classloader;

import org.junit.Test;

/**
 * @author zhangzhongkun
 * @since 2019-08-08 14:51
 **/
public class ClassLoaderDemo {

    @Test
    public void test1(){
        ClassLoader cl1 = getClass().getClassLoader();
        System.out.println(cl1);
        ClassLoader cl2 = cl1.getParent();
        System.out.println(cl2);
        ClassLoader cl3 = cl2.getParent();
        System.out.println(cl3);
    }

    @Test
    public void test2(){
        ClassLoader cl1 = Thread.currentThread().getContextClassLoader();
        System.out.println(cl1);

        Thread t = new Thread( ()->{
            System.out.println("My thread is in running state.");
        });
        ClassLoader cl2 = t.getContextClassLoader();
        System.out.println(cl2);

        ClassLoader classLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                return super.loadClass(name);
            }
        };
        t.setContextClassLoader(classLoader);
        ClassLoader cl3 = t.getContextClassLoader();
        System.out.println(cl3);
    }
}
