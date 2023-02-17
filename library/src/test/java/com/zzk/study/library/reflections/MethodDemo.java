package com.zzk.study.library.reflections;

import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.hamcrest.MatcherAssert.assertThat;

//import com.zzk.study.library.reflections.TestModel.*;

/**
 * TODO:
 *
 * @author zhangzhongkun02
 * @date 2022/12/2 12:25 PM
 */
public class MethodDemo {

    public @Retention(RUNTIME) @interface AM1 {
        public abstract String value();
    }
    public @interface AM2 {}
    public class C4 {
        public C4() { }

        @AM1("1") protected void m1() {}
        @AM1("1") public void m1(int integer, @AM2 String... strings) {}
        @AM1("1") public void m1(int[][] integer, String[][] strings) {}

        @AM2 public void m2(){}
        @AM1("2") public String m3() {return null;}
        public String m4(@AM1("2") @AM2 String string) {return null;}
        public int add(int i1, int i2) { return i1+i2; }
    }

    @Test
    public void findMethodAnnotation() throws NoSuchMethodException {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forClass(MethodDemo.class))
                .setScanners(
                        Scanners.MethodsAnnotated
                ));

        assertThat(reflections.getMethodsAnnotatedWith(AM1.class),
                TestUtil.are(C4.class.getDeclaredMethod("m1"),
                        C4.class.getDeclaredMethod("m1", int.class, String[].class),
                        C4.class.getDeclaredMethod("m1", int[][].class, String[][].class),
                        C4.class.getDeclaredMethod("m3")));

        // 即使没有Retention RUNTIME也可以
        assertThat(reflections.getMethodsAnnotatedWith(AM2.class),
                TestUtil.are(C4.class.getDeclaredMethod("m2")));
    }

    @Test
    public void findMethodAnnotationWithProperty() throws NoSuchMethodException {
        AM1 am1 = new AM1() {
            public String value() {
                return "1";
            }

            public Class<? extends Annotation> annotationType() {
                return AM1.class;
            }
        };
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forClass(MethodDemo.class))
                .setScanners(
                        Scanners.MethodsAnnotated
                ));
        // 查找AM1注解，且value值为1
        assertThat(reflections.getMethodsAnnotatedWith(am1),
                TestUtil.are(C4.class.getDeclaredMethod("m1"),
                        C4.class.getDeclaredMethod("m1", int.class, String[].class),
                        C4.class.getDeclaredMethod("m1", int[][].class, String[][].class)));
    }

}
