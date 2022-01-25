package zzk.study.java.core.lang.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

/**
 * TODO:
 *
 * @author zhangzhongkun02
 * @date 2022/1/19 8:50 PM
 */
public class Utils {
    /**
     * 获取所有方法，包括父类
     * */
    public static Method getSupportedMethod(Class<?> cls, String name, Class<?>[] paramTypes) throws NoSuchMethodException {
        if (cls == null) {
            throw new NoSuchMethodException();
        }
        try {
            return cls.getDeclaredMethod(name, paramTypes);
        } catch (NoSuchMethodException ex) {
            return getSupportedMethod(cls.getSuperclass(), name, paramTypes);
        }
    }

    /**
     * 获取所有实例属性
     * */
    public static Field[] getInstanceVariables(Class cls) {
        List accum = new LinkedList();
        while (cls != null) {
            Field[] fields = cls.getDeclaredFields();
            for (int i=0; i<fields.length; i++) {
                if (!Modifier.isStatic(fields[i].getModifiers())) {
                    accum.add(fields[i]);
                }
            }
            cls = cls.getSuperclass();
        }
        Field[] retvalue = new Field[accum.size()];
        return (Field[]) accum.toArray(retvalue);
    }

    public static Field findField( Class cls, String name ) throws NoSuchFieldException {
        if ( cls != null ) {
            try {
                return cls.getDeclaredField( name );
            } catch(NoSuchFieldException e){
                return findField( cls.getSuperclass(), name );
            }
        } else {
            throw new NoSuchFieldException();
        }
    }
}
