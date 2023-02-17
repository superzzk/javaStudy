package zzk.study.java.core.javassist;


import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class Tutorial {

    @Test
    public void demo() throws NotFoundException, CannotCompileException, InstantiationException, IllegalAccessException {
        ClassPool pool = ClassPool.getDefault();
        final CtClass cc = pool.get("zzk.study.java.core.javassist.Point");
        cc.setSuperclass(pool.get("zzk.study.java.core.javassist.Shape"));
        final Object o = cc.toClass().newInstance();
        Shape shape = (Shape)o;
        Assertions.assertEquals("this is shape", shape.getString());
    }


}
