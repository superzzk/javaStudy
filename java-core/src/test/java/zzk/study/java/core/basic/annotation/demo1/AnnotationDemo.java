package zzk.study.java.core.basic.annotation.demo1;

import java.lang.reflect.Method;

public class AnnotationDemo {
    @MethodInfo(
            author = "zzk",
            date = "2016/01/14",
            version = 1)
    public String getAppName() {
        return "demo";
    }

    public static void main(String[] args) {
        Class cls = AnnotationDemo.class;
        for (Method method : cls.getMethods()) {
            MethodInfo methodInfo = method.getAnnotation(MethodInfo.class);
            if (methodInfo != null) {
                System.out.println("method name:" + method.getName());
                System.out.println("method author:"  + methodInfo.author());
                System.out.println("method version:" + methodInfo.version());
                System.out.println("method date:" + methodInfo.date());
            }
        }
    }
}
