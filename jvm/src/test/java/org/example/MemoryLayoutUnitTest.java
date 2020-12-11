package org.example;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;
import sun.misc.Contended;

/**
 * https://www.baeldung.com/java-memory-layout
 * */
public class MemoryLayoutUnitTest {

    private volatile Object consumer;

    @Test
    public void printingTheVMDetails() {
        System.out.println(VM.current().details());
    }

    /**
     * the object header is 12 bytes, including 8 bytes of the mark and 4 bytes of klass.
     * After that, we have 4 bytes for the int state.
     * In total, any object from this class would consume 16 bytes
     * */
    @Test
    public void simpleMemoryLayout() {
        System.out.println(ClassLayout.parseClass(SimpleInt.class).toPrintable());
    }

    @Test
    public void identityHashCodeMemoryLayout() {
        SimpleInt instance = new SimpleInt();
        System.out.println(ClassLayout.parseInstance(instance).toPrintable());

        System.out.println("The identity hash code is " + System.identityHashCode(instance));
        System.out.println(ClassLayout.parseInstance(instance).toPrintable());
    }

    /**
     * To make this size a multiple of 8 bytes, the JVM adds 4 bytes of padding
     * can change with -XX:ObjectAlignmentInBytes
     * */
    @Test
    public void alignmentMemoryLayout() {
        System.out.println(ClassLayout.parseClass(SimpleLong.class).toPrintable());
    }

    /**
     * When a class has multiple fields, the JVM may distribute those fields in such a way as to minimize padding waste.
     * */
    @Test
    public void fieldPackingMemoryLayout() {
        System.out.println(ClassLayout.parseClass(FieldsArrangement.class).toPrintable());
    }

    /**
     * the mark word changes when we're holding the monitor lock
     * */
    @Test
    public void monitorLockMemoryLayout() {
        Lock lock = new Lock();
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());

        synchronized (lock) {
            System.out.println(ClassLayout.parseInstance(lock).toPrintable());
        }
    }

    /**
     * JVM keep track of the number of survivals for each object in mark word
     * */
    @Test
    public void ageAndTenuringMemoryLayout() {
        Object instance = new Object();
        long lastAddr = VM.current().addressOf(instance);
        ClassLayout layout = ClassLayout.parseInstance(instance);

        for (int i = 0; i < 10_000; i++) {
            long currentAddr = VM.current().addressOf(instance);
            if (currentAddr != lastAddr) {
                System.out.println(layout.toPrintable());
            }

            for (int j = 0; j < 10_000; j++) {
                consumer = new Object();
            }

            lastAddr = currentAddr;
        }
    }

    /**
     * The jdk.internal.vm.annotation.Contended annotation (or sun.misc.Contended on Java 8) is a hint
     * for the JVM to isolate the annotated fields to avoid false sharing.
     * Put simply, the Contended annotation adds some paddings around each annotated field to isolate each field on its own cache line.
     * Consequently, this will impact the memory layout.
     *
     * we can control the Contended padding size with -XX:ContendedPaddingWidth tuning flag
     *
     * note that the Contended annotation is JDK internal, therefore we should avoid using it.
     *
     * Also, we should run our code with the -XX:-RestrictContended tuning flag; otherwise, the annotation wouldn't take effect
     * */
    @Test
    public void contendedMemoryLayout() {
        System.out.println(ClassLayout.parseClass(Isolated.class).toPrintable());
    }

    /**
     * 16 bytes of object header containing 8 bytes of mark word, 4 bytes of klass word, and 4 bytes of length.
     * Immediately after the object header, we have 3 bytes for a boolean array with 3 elements
     * */
    @Test
    public void arrayMemoryLayout() {
        boolean[] booleans = new boolean[3];
        System.out.println(ClassLayout.parseInstance(booleans).toPrintable());
    }

    private static class SimpleInt {
        private int state;
    }

    private static class SimpleLong {
        private long state;
    }

    private static class FieldsArrangement {
        private boolean first;
        private char second;
        private double third;
        private int fourth;
        private boolean fifth;
    }

    private static class Lock {}

    private static class Isolated {

        @Contended
        private int i;

        @Contended
        private long l;
    }
}
