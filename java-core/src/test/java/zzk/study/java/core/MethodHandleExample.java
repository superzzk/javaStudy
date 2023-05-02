package zzk.study.java.core;

import org.junit.jupiter.api.Test;

import static java.lang.invoke.MethodHandles.*;
import static java.lang.invoke.MethodType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;

public class MethodHandleExample {

    @Test
    public void demo1() throws Throwable {
        MethodHandle MH_concat = publicLookup().findVirtual(String.class, "concat", methodType(String.class, String.class));
        MethodHandle MH_hashCode = publicLookup().findVirtual(Object.class,
                "hashCode", methodType(int.class));
        MethodHandle MH_hashCode_String = publicLookup().findVirtual(String.class,
                "hashCode", methodType(int.class));
        assertEquals("xy", (String) MH_concat.invokeExact("x", "y"));
        assertEquals("xy".hashCode(), (int) MH_hashCode.invokeExact((Object) "xy"));
        assertEquals("xy".hashCode(), (int) MH_hashCode_String.invokeExact("xy"));
        // interface method:
        MethodHandle MH_subSequence = publicLookup().findVirtual(CharSequence.class,
                "subSequence", methodType(CharSequence.class, int.class, int.class));
        assertEquals("def", MH_subSequence.invoke("abcdefghi", 3, 6).toString());
        // constructor "internal method" must be accessed differently:
        MethodType MT_newString = methodType(void.class); //()V for new String()
        try {
            assertEquals("impossible", lookup()
                    .findVirtual(String.class, "<init>", MT_newString));
        } catch (NoSuchMethodException ex) {
        } // OK
        MethodHandle MH_newString = publicLookup()
                .findConstructor(String.class, MT_newString);
        assertEquals("", (String) MH_newString.invokeExact());

    }

    @Test
    public void demo2() throws Throwable {
        MethodHandleExample example = new MethodHandleExample();
        example.createCallSite("DOG").dynamicInvoker().invoke(example);    //I am a Dog :: So barking
        example.createCallSite("CAT").dynamicInvoker().invoke(example);    //I am a Cat :: So Mewaooing
    }

    private MutableCallSite callSite = null;

    public void bark() {
        System.out.println("I am a Dog :: So barking");
    }

    public void mewaoo() {
        System.out.println("I am a Cat :: So Mewaooing");
    }

    public MethodHandle findMethodHandle(String command) throws NoSuchMethodException, IllegalAccessException {
        MethodType voidType = MethodType.methodType(void.class);
        MethodHandle mh = null;

        if ("DOG".equalsIgnoreCase(command)) {
            mh = createMethodHandle(voidType, "bark");
        } else if ("CAT".equalsIgnoreCase(command)) {
            mh = createMethodHandle(voidType, "mewaoo");
        } else {
            throw new RuntimeException("Give a Proper Command");
        }
        return mh;

    }

    public CallSite createCallSite(String command) throws NoSuchMethodException, IllegalAccessException {
        MethodHandle mh = findMethodHandle(command);
        if (callSite == null) {
            callSite = new MutableCallSite(mh);
        } else {
            callSite.setTarget(mh);
        }
        return callSite;
    }

    public MethodHandle createMethodHandle(MethodType methodType, String methodName) throws NoSuchMethodException, IllegalAccessException {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        return lookup.findVirtual(this.getClass(), methodName, methodType);
    }


}