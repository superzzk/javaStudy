package zzk.study.java.core.basic.object;

import java.time.Instant;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author zhangzhongkun
 * @since 2019-08-06 19:19
 **/
public class ObjectsDemo {
    public static void main(String[] args) {
        testHash();
        System.out.println("--------------------");
        testEquals();
        System.out.println("--------------------");
        testToString();
        System.out.println("--------------------");
        testRequireNonNull();
    }
    private static void testHash() {
        // Compute hash code for two integers, a char, and a string
        int hash = Objects.hash(10, 800, "\u20b9", "Hello");
        System.out.println("Hash Code is " + hash);
    }

    private static void testEquals() {
        // Test for equality
        boolean isEqual = Objects.equals(null, null);
        System.out.println("null is  equal to null:  " + isEqual);

        isEqual = Objects.equals(null, "XYZ");
        System.out.println("null is  equal to XYZ: " + isEqual);
    }
    public static void testToString() {
        // toString() method test
        System.out.println("toString(null) is  " + Objects.toString(null));
        System.out.println("toString(null, \"XXX\")  is "
                + Objects.toString(null, "XXX"));
    }

    public static void testRequireNonNull() {
        try {
            printName("A");
            printName(null);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        try {
            Supplier<String> messageSupplier = () -> "Name is  required. Error generated on  " + Instant.now();
            printNameWithSupplier("asdf", messageSupplier);
            printNameWithSupplier(null, messageSupplier);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void printName(String name) {
        Objects.requireNonNull(name, "Name is required.");
        System.out.println("Name is " + name);
    }

    public static void printNameWithSupplier(String name,
                                            Supplier<String> messageSupplier) {
        Objects.requireNonNull(name, messageSupplier);
        System.out.println("Name is " + name);
    }

}
