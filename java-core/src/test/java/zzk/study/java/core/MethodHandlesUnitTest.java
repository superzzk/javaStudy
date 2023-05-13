package zzk.study.java.core;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.WrongMethodTypeException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Test case for the {@link MethodHandles} API
 */
public class MethodHandlesUnitTest {

    @Test
    public void lookup_findVirtual() throws Throwable {
        MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();
        MethodType mt = MethodType.methodType(String.class, String.class);
        MethodHandle concatMH = publicLookup.findVirtual(String.class, "concat", mt);

        String output = (String) concatMH.invoke("Effective ", "Java");

        assertEquals("Effective Java", output);
    }

    @Test
    public void invoke_with_arguments() throws Throwable {
        MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();
        MethodType mt = MethodType.methodType(List.class, Object[].class);
        MethodHandle asListMH = publicLookup.findStatic(Arrays.class, "asList", mt);

        List<Integer> list = (List<Integer>) asListMH.invokeWithArguments(1, 2);

        assertThat(Arrays.asList(1, 2), is(list));
    }

    @Test
    public void invoke_constructor() throws Throwable {
        MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();
        MethodType mt = MethodType.methodType(void.class, String.class);
        MethodHandle newIntegerMH = publicLookup.findConstructor(Integer.class, mt);

        Integer integer = (Integer) newIntegerMH.invoke("1");

        assertEquals(1, integer.intValue());
    }

    @Test
    public void field_getter() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle getTitleMH = lookup.findGetter(Book.class, "title", String.class);

        Book book = new Book("ISBN-1234", "Effective Java");
        assertEquals("Effective Java", getTitleMH.invoke(book));
    }

    @Test
    public void private_method() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        Method formatBookMethod = Book.class.getDeclaredMethod("formatBook");
        formatBookMethod.setAccessible(true);

        MethodHandle formatBookMH = lookup.unreflect(formatBookMethod);

        Book book = new Book("ISBN-123", "Java in Action");
        assertEquals("ISBN-123 > Java in Action", formatBookMH.invoke(book));
    }

    @Test
    public void reflection_invoke_replace() throws Throwable {
        Method replaceMethod = String.class.getMethod("replace", char.class, char.class);
        assertEquals("java", (String) replaceMethod.invoke("jovo", 'o', 'a'));
    }

    @Test
    public void handle_invoke_replace() throws Throwable {
        MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();
        MethodType mt = MethodType.methodType(String.class, char.class, char.class);
        MethodHandle replaceMH = publicLookup.findVirtual(String.class, "replace", mt);
        assertEquals("java", (String) replaceMH.invoke("jovo", 'o', 'a'));
    }

    @Test
    public void handle_invoke_exact() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType mt = MethodType.methodType(String.class, char.class, char.class);
        MethodHandle replaceMH = lookup.findVirtual(String.class, "replace", mt);
        assertEquals("java", (String) replaceMH.invokeExact("jovo", 'o', 'a'));
    }

    @Test
    public void findStatic() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType mt = MethodType.methodType(int.class, int.class, int.class);
        MethodHandle sumMH = lookup.findStatic(Integer.class, "sum", mt);

        assertEquals(12, (int) sumMH.invokeExact(1, 11));
    }

    @Test
    public void invoke_diff_invoke_exact() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType mt = MethodType.methodType(int.class, int.class, int.class);
        MethodHandle sumMH = lookup.findStatic(Integer.class, "sum", mt);
        sumMH.invoke(1, 11);
        Assertions.assertThrows(WrongMethodTypeException.class, () -> sumMH.invokeExact(Integer.valueOf(1), 11));
    }

    @Test
    public void asSpreader() throws Throwable {
        MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();
        MethodType mt = MethodType.methodType(boolean.class, Object.class);
        MethodHandle equalsMH = publicLookup.findVirtual(String.class, "equals", mt);

        MethodHandle methodHandle = equalsMH.asSpreader(Object[].class, 2);

        assertTrue((boolean) methodHandle.invoke(new Object[]{"java", "java"}));
        assertFalse((boolean) methodHandle.invoke(new Object[]{"java", "jova"}));
    }

    @Test
    public void bindTo() throws Throwable {
        MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();
        MethodType mt = MethodType.methodType(String.class, String.class);
        MethodHandle concatMH = publicLookup.findVirtual(String.class, "concat", mt);

        MethodHandle bindedConcatMH = concatMH.bindTo("Hello ");

        assertEquals("Hello World!", bindedConcatMH.invoke("World!"));
    }

    public class Book {

        String id;
        String title;

        public Book(String id, String title) {
            this.id = id;
            this.title = title;
        }

        @SuppressWarnings("unused")
        private String formatBook() {
            return id + " > " + title;
        }
    }
}