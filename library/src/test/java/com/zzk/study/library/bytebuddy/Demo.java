package com.zzk.study.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.BindingPriority;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static net.bytebuddy.matcher.ElementMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * TODO:
 *
 * @author zhangzhongkun02
 * @date 2022/3/11 2:21 PM
 */
public class Demo {
    @Test
    public void givenObject_whenToString_thenReturnHelloWorldString() throws InstantiationException, IllegalAccessException {
        //创建一个继承Object的类，并改写方法
        DynamicType.Unloaded unloadedType = new ByteBuddy()
                .subclass(Object.class)
                .method(ElementMatchers.isToString())
                .intercept(FixedValue.value("Hello World ByteBuddy!"))
                .make();

        //加载类
        Class<?> dynamicType = unloadedType.load(getClass().getClassLoader()).getLoaded();

        assertEquals(dynamicType.newInstance().toString(), "Hello World ByteBuddy!");
    }

    //Method Delegation
    @Test
    public void givenSayHelloFoo_whenMethodDelegation_thenSayHelloBar() throws IllegalAccessException, InstantiationException {

        String r = new ByteBuddy()
                .subclass(Foo.class)
                .method(named("sayHelloFoo")
                        .and(isDeclaredBy(Foo.class)
                                .and(returns(String.class))))
                .intercept(MethodDelegation.to(Bar.class))
                .make()
                .load(getClass().getClassLoader())
                .getLoaded()
                .newInstance()
                .sayHelloFoo();

        assertEquals(r, Bar.sayHelloBar());
    }

    // method and field definition
    @Test
    public void givenMethodName_whenDefineMethod_thenCreateMethod() throws Exception {
        Class<?> type = new ByteBuddy()
                .subclass(Object.class)
                .name("MyClassName")
                .defineMethod("custom", String.class, Modifier.PUBLIC)
                .intercept(MethodDelegation.to(Bar.class))
                .defineField("x", String.class, Modifier.PUBLIC)
                .make()
                .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded();

        Method m = type.getDeclaredMethod("custom", null);

        assertEquals(m.invoke(type.newInstance()), Bar.sayHelloBar());
        assertNotNull(type.getDeclaredField("x"));

    }

    //Redefining an Existing Class
    @Test
    public void givenFoo_whenRedefined_thenReturnFooRedefined() throws Exception {
        ByteBuddyAgent.install();
        new ByteBuddy()
                .redefine(Foo.class)
                .method(named("sayHelloFoo"))
                .intercept(FixedValue.value("Hello Foo Redefined"))
                .make()
                .load(Foo.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());

        Foo f = new Foo();
        assertEquals(f.sayHelloFoo(), "Hello Foo Redefined");
    }


    public static class Foo {
        public String sayHelloFoo() {
            return "Hello in Foo!";
        }
    }

    public static class Bar {

        @BindingPriority(3)
        public static String sayHelloBar() {
            return "Holla in Bar!";
        }

        @BindingPriority(2)
        public static String sayBar() {
            return "bar";
        }

        public String bar() {
            return Bar.class.getSimpleName() + " - Bar";
        }

    }


}
