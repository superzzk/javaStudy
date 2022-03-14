package zzk.study.java.core.basic.string;

/**
 * @program: javaStudy
 * @description:
 * @author: zhangzhongkun
 * @create: 2019-02-19 11:13
 **/
public class CharDemo {
    public static void main(String[] args) {

        //test1();
        test2();

    }

    private static void test1(){
        char z = 'z';
        int index = charToIndex(z);
        System.out.println(index);
        System.out.println(indexToChar(index));
    }
    private static int charToIndex(char c){
        return c - 'a';
    }
    private static char indexToChar(int index){
        return (char) (index + 97);
    }

    /**
     * charAt and codePointAt
      **/
    private static void test2(){
        String str = "My Name is Zhang.";
        char a = str.charAt(4);
        System.out.println(a);
        int b = str.codePointAt(4);
        System.out.println(b);
    }


}
