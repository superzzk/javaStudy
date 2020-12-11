package org.example;

import org.junit.Test;
import org.openjdk.jol.vm.VM;

public class MemoryAddressUnitTest {

    @Test
    public void printTheMemoryAddress() {
        String answer = "42";

        System.out.println("The memory address is " + VM.current().addressOf(answer));
    }

    /**
     * the “60addb54” is the hexadecimal version of the hash code, which is 1622006612.
     * The hashCode() method is one of the common methods for all Java objects.
     * When we don't declare a hashCode() method for a class, Java will use the identity hash code for it.
     * */
    @Test
    public void identityHashCodeAndMemoryAddress() {
        Object obj = new Object();

        System.out.println("Memory address: " + VM.current().addressOf(obj));
        System.out.println("hashCode: " + obj.hashCode());
        System.out.println("hashCode: " + System.identityHashCode(obj));
        System.out.println("toString: " + obj);
    }
}
