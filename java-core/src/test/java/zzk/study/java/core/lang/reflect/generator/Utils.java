package zzk.study.java.core.lang.reflect.generator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class Utils {
    public static String createRenamedConstructor(Constructor c, String name, String code) {
        Class[] pta = c.getParameterTypes();
        String fpl = formalParametersToString(pta);
        String apl = actualParametersToString(pta);
        Class[] eTypes = c.getExceptionTypes();
        String result = name + "(" + fpl + ")\n";
        if (eTypes.length != 0)
            result += " throws "
                    + classArrayToString(eTypes)
                    + "\n";
        result += "{\n super(" + apl + ");\n" + code + "}\n";
        return result;
    }

    public static String formalParametersToString(Class[] pts) {
        String result = "";
        for (int i = 0; i < pts.length; i++) {
            result += getTypeName(pts[i]) + " p" + i;
            if (i < pts.length - 1)
                result += ",";
        }
        return result;
    }

    public static String actualParametersToString(Class[] pts) {
        String result = "";
        for (int i = 0; i < pts.length; i++) {
            result += "p" + i;
            if (i < pts.length - 1)
                result += ",";
        }
        return result;
    }

    public static String classArrayToString(Class[] pts) {
        String result = "";
        for (int i = 0; i < pts.length; i++) {
            result += getTypeName(pts[i]);
            if (i < pts.length - 1)
                result += ",";
        }
        return result;
    }

    public static String getTypeName(Class cls) {
        if (!cls.isArray()) {
            return cls.getName();
        } else {
            return getTypeName(cls.getComponentType()) + "[]";
        }
    }

    public static int getModifiersWithout(Method m, int unwantedModifiers) {
        int mods = m.getModifiers();
        return (mods ^ unwantedModifiers) & mods;
    }

    /**
     * @return 返回所有没有实现的方法
     */
    public static Method[] getMethodsLackingImplementation(Class cls) {
        //查找所有非abstract方法
        UQueue imq = selectMethods0(cls, 0, Modifier.ABSTRACT, null);
        //查找所有abstract方法
        UQueue amq = selectMethods0(cls, Modifier.ABSTRACT, 0, null);

        UQueue rmq = new UQueue(Method.class, equalSignaturesMethod);
        for (int i = 0; i < amq.size(); i++) {
            Method rm = (Method) amq.elementAt(i);
            if (!imq.contains(rm))
                rmq.add(rm);
        }
        return (Method[]) rmq.toArray();
    }

    public static Method[] selectMethods(Class cls, int mustHave, int mustNotHave) {
        return (Method[]) selectMethods0(cls, mustHave, mustNotHave, null).toArray();
    }

    /**
     * 根据限定符查找满足的方法
     *
     * @param cls         查找目标类
     * @param mustHave    必须包含的限定符
     * @param mustNotHave 不能包含的限定符
     * @param limit       限制向上查找的类
     * @return 找到的所有类
     */
    public static Method[] selectMethods(Class cls, int mustHave, int mustNotHave, Class limit) {
        return (Method[]) selectMethods0(cls, mustHave, mustNotHave, limit).toArray();
    }


    private static UQueue selectMethods0(Class cls, int mustHave, int mustNotHave, Class limit) {
        UQueue mq = new UQueue(Method.class, equalSignaturesMethod);
        Class[] ca = selectAncestors(cls, 0, 0, limit);
        for (int j = 0; j < ca.length; j++) {
            Method[] ma = ca[j].getDeclaredMethods();
            for (int i = 0; i < ma.length; i++) {
                int mods = ma[i].getModifiers();
                if (((mods & mustHave) == mustHave) && ((mods & mustNotHave) == 0))
                    mq.add(ma[i]);
            }
        }
        return mq;
    }

    // 方法equalSignatures的引用
    static private Method equalSignaturesMethod;

    static {
        Class[] fpl = {Method.class, Method.class};
        try {
            equalSignaturesMethod = Utils.class.getMethod("equalSignatures", fpl);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return 方法签名是否相同
     */
    public static boolean equalSignatures(Method m1, Method m2) {
        if (!m1.getName().equals(m2.getName())) return false;
        if (!Arrays.equals(m1.getParameterTypes(), m2.getParameterTypes()))
            return false;
        return true;
    }

    public static Class[] selectAncestors(Class cls,
                                          int mustHave,
                                          int mustNotHave) {
        return selectAncestors(cls, mustHave, mustNotHave, null);
    }

    /**
     * 根据限定符向上查找满足类
     *
     * @param cls         查找目标类
     * @param mustHave    必须包含的限定符
     * @param mustNotHave 不能包含的限定符
     * @param limit       限制向上查找的类
     * @return 找到的所有类
     */
    public static Class[] selectAncestors(Class cls, int mustHave, int mustNotHave, Class limit) {
        UQueue cq = new UQueue(Class.class);
        if (!cls.isInterface()) {
            for (Class x = cls; x != limit; x = x.getSuperclass()) {
                int mods = x.getModifiers();
                if (((mods & mustHave) == mustHave) && ((mods & mustNotHave) == 0))
                    cq.add(x);
            }
        }
        Class[] ca = getAllInterfaces(cls, limit);
        for (int i = 0; i < ca.length; i++) {
            int mods = ca[i].getModifiers();
            if (((mods & mustHave) == mustHave) && ((mods & mustNotHave) == 0))
                cq.add(ca[i]);
        }
        return (Class[]) cq.toArray();
    }

    public static Class[] getAllInterfaces(Class cls, Class limit) {
        assert (limit == null
                || (!limit.isInterface() && !limit.isPrimitive()));
        UQueue cq = new UQueue(Class.class);
        if (cls.isInterface())
            cq.add(cls);
        for (Class x = cls; x != null && x != limit; x = x.getSuperclass())
            getInterfaceSubtree(x, cq);
        return (Class[]) cq.toArray();
    }

    private static void getInterfaceSubtree(Class cls, UQueue cq) {
        Class[] iArray = cls.getInterfaces();
        for (int j = 0; j < iArray.length; j++) {
            cq.add(iArray[j]);
            getInterfaceSubtree(iArray[j], cq);
        }
    }

    /**
     * 新生成的方法中增加前后代码段
     * eg.
     *      code1
     *      var ret = super.method()
     *      code2
     *      return ret
     * */
    public static String createCooperativeWrapper(Method m,String code1,String code2) {
        Class[] pta = m.getParameterTypes();
        Class retType = m.getReturnType();
        String fpl = formalParametersToString(pta);
        String apl = actualParametersToString(pta);
        Class[] eTypes = m.getExceptionTypes();

        String result= retType.getName() + " " + m.getName() + "(" + fpl + ")\n";
        if (eTypes.length != 0)
            result += " throws " + classArrayToString(eTypes) + "\n";
        result += "{\n" + code1 + " ";

        if (retType != void.class)
            result += retType.getName() + " cooperativeReturnValue = ";
        result += "super." + m.getName() + "(" + apl + ");\n";
        result += code2;
        if (retType != void.class)
            result += " return cooperativeReturnValue;\n";
        result += "}\n";
        return result;
    }
}
