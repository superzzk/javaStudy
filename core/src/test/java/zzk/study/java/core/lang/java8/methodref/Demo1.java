package zzk.study.java.core.lang.java8.methodref;

/**
 * @program: javaStudy
 * @description:
 * @author: zhangzhongkun
 * @create: 2019-02-19 10:15
 **/
public class Demo1 {
    public static String stringOp(StringFunc sf, String s) {
        return sf.func(s);
    }

    public static void main(String[] args) {
        String inStr = "lambda add power to Java";
        //MyStringOps::strReverse 相当于实现了接口方法func()
        // 并在接口方法func()中作了MyStringOps.strReverse()操作
        String outStr = stringOp(MyStringOps::strReverse, inStr);
        System.out.println("Original string: " + inStr);
        System.out.println("String reserved: " + outStr);
    }
}

class MyStringOps {

    //静态方法： 反转字符串
    public static String strReverse(String str) {
        String result = "";
        for (int i = str.length() - 1; i >= 0; i--) {
            result += str.charAt(i);
        }
        return result;
    }
}
