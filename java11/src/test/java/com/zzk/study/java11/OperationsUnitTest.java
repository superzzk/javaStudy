package com.zzk.study.java11;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class OperationsUnitTest {

    public OperationsUnitTest() {
    }

    @Test(expected = IllegalAccessException.class)
    public void givenObject_whenInvokePrivateMethod_thenFail() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method andPrivateMethod = Operations.class.getDeclaredMethod("privateAnd", boolean.class, boolean.class);

        Operations operationsInstance = new Operations();
        Boolean result = (Boolean) andPrivateMethod.invoke(operationsInstance, true, false);

        assertFalse(result);
    }

    @Test
    public void givenObject_whenInvokePrivateMethod_thenCorrect() throws Exception {
        Method andPrivatedMethod = Operations.class.getDeclaredMethod("privateAnd", boolean.class, boolean.class);
        andPrivatedMethod.setAccessible(true);

        Operations operationsInstance = new Operations();
        Boolean result = (Boolean) andPrivatedMethod.invoke(operationsInstance, true, false);

        assertFalse(result);
    }
    
    @Test
    public void givenObject_whenInvokePrivateMethod_thenCheckAccess() throws Exception {
        Operations operationsInstance = new Operations();
        Method andPrivatedMethod = Operations.class.getDeclaredMethod("privateAnd", boolean.class, boolean.class);
        boolean isAccessEnabled = andPrivatedMethod.canAccess(operationsInstance);
        
        assertFalse(isAccessEnabled);
    }
    
    @Test
    public void givenObject_whenInvokePublicMethod_thenEnableAccess() throws Exception {
        Operations operationsInstance = new Operations();
        Method andPrivatedMethod = Operations.class.getDeclaredMethod("privateAnd", boolean.class, boolean.class);
        andPrivatedMethod.trySetAccessible();
        boolean isAccessEnabled = andPrivatedMethod.canAccess(operationsInstance);
        
        assertTrue(isAccessEnabled);
    }

    @Test
    public void givenObject_whenInvokePublicMethod_thenCorrect() throws Exception {
        Method sumInstanceMethod = Operations.class.getMethod("publicSum", int.class, double.class);

        Operations operationsInstance = new Operations();
        Double result = (Double) sumInstanceMethod.invoke(operationsInstance, 1, 3);

        assertThat(result, equalTo(4.0));
    }

    @Test
    public void givenObject_whenInvokeStaticMethod_thenCorrect() throws Exception {
        Method multiplyStaticMethod = Operations.class.getDeclaredMethod("publicStaticMultiply", float.class, long.class);

        Double result = (Double) multiplyStaticMethod.invoke(null, 3.5f, 2);

        assertThat(result, equalTo(7.0));
    }

    public static class Operations {

        public double publicSum(int a, double b) {
            return a + b;
        }

        public static double publicStaticMultiply(float a, long b) {
            return a * b;
        }

        private boolean privateAnd(boolean a, boolean b) {
            return a && b;
        }

        protected int protectedMax(int a, int b) {
            return a > b ? a : b;
        }

    }


}
