package com.zzk.study.library.reflections;

import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;

import static com.zzk.study.library.reflections.TestUtil.annotatedWith;
import static com.zzk.study.library.reflections.TestUtil.are;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * TODO:
 *
 * @author zhangzhongkun02
 * @date 2022/12/3 4:20 PM
 */
public class TypeDemo {

    @Test
    public void testSubTypesOf() {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forClass(TypeDemo.class))
                .setScanners(
                        Scanners.SubTypes
                ));
        assertThat(reflections.getSubTypesOf(I1.class), are(I2.class, C1.class, C2.class, C3.class, C5.class));
        assertThat(reflections.getSubTypesOf(C1.class), are(C2.class, C3.class, C5.class));

        assertFalse(reflections.getAllTypes().isEmpty(), "getAllTypes should not be empty when Reflections is configured with SubTypesScanner(false)");
    }


    @Test
    public void testTypesAnnotatedWith() {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forClass(TypeDemo.class))
                .setScanners(
                        Scanners.SubTypes,
                        Scanners.TypesAnnotated
                ));
        assertThat(reflections.getTypesAnnotatedWith(MAI1.class, true), are(AI1.class));
        assertThat(reflections.getTypesAnnotatedWith(MAI1.class, true), annotatedWith(MAI1.class));

        assertThat(reflections.getTypesAnnotatedWith(AI2.class, true), are(I2.class));
        assertThat(reflections.getTypesAnnotatedWith(AI2.class, true), annotatedWith(AI2.class));

        assertThat(reflections.getTypesAnnotatedWith(AC1.class, true), are(C1.class, C2.class, C3.class, C5.class));
        assertThat(reflections.getTypesAnnotatedWith(AC1.class, true), annotatedWith(AC1.class));

        assertThat(reflections.getTypesAnnotatedWith(AC1n.class, true), are(C1.class));
        assertThat(reflections.getTypesAnnotatedWith(AC1n.class, true), annotatedWith(AC1n.class));
        assertThat(reflections.getTypesAnnotatedWith(MAI1.class), are(AI1.class, I1.class, I2.class, C1.class, C2.class, C3.class, C5.class));
        assertThat(reflections.getTypesAnnotatedWith(AI1.class), are(I1.class, I2.class, C1.class, C2.class, C3.class, C5.class));
        assertThat(reflections.getTypesAnnotatedWith(AI2.class), are(I2.class, C1.class, C2.class, C3.class, C5.class));

        assertTrue(reflections.getTypesAnnotatedWith(AM1.class).isEmpty());

        //annotation member value matching
        AC2 ac2 = new AC2() {
            public String value() {return "ac2";}
            public Class<? extends Annotation> annotationType() {return AC2.class;}};

        assertThat(reflections.getTypesAnnotatedWith(ac2), are(C3.class, C5.class, I3.class, C6.class, AC3.class, C7.class));

        assertThat(reflections.getTypesAnnotatedWith(ac2, true), are(C3.class, I3.class, AC3.class));
    }

    public @Retention(RUNTIME) @Inherited
    @interface MAI1 {}
    public @Retention(RUNTIME) @MAI1 @interface AI1 {}
    public @AI1 interface I1 {}
    public @Retention(RUNTIME) @Inherited @interface AI2 {}
    public @AI2 interface I2 extends I1 {}

    public @Retention(RUNTIME) @Inherited @interface AC1 {}
    public @Retention(RUNTIME) @interface AC1n {}
    public @AC1 @AC1n class C1 implements I2 {}
    public @Retention(RUNTIME) @interface AC2 {
        public abstract String value();
    }

    public @AC2("") class C2 extends C1 {}
    public @AC2("ac2") class C3 extends C1 {}

    public @Retention(RUNTIME) @interface AM1 {
        public abstract String value();
    }
    public @interface AM2 {}
    public @Retention(RUNTIME) @interface AF1 {
        public abstract String value();
    }
    public class C4 {
        @AF1("1") private String f1;
        @AF1("2") protected String f2;
        protected String f3;

        public C4() { }
        @AM1("1") public C4(@AM1("1") String f1) { this.f1 = f1; }

        @AM1("1") protected void m1() {}
        @AM1("1") public void m1(int integer, @AM2 String... strings) {}
        @AM1("1") public void m1(int[][] integer, String[][] strings) {}
        @AM1("2") public String m3() {return null;}
        public String m4(@AM1("2") @AM2 String string) {return null;}
        public C3 c2toC3(C2 c2) {return null;}
        public int add(int i1, int i2) { return i1+i2; }
    }

    public class C5 extends C3 {}
    public @AC2("ac2") interface I3 {}
    public class C6 implements I3 {}

    public @AC2("ac2") @interface AC3 { } // not @Retention(RUNTIME)
    public @AC3 class C7 {}
}
