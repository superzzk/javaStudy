package jvm.inlining;

public class InliningExample {

    public static final int NUMBERS_OF_ITERATIONS = 15000;


    public static void main(String[] args) {
        test3();
    }

    /**
     * -XX:+PrintCompilation -XX:+UnlockDiagnosticVMOptions -XX:+PrintInlining
     * The first flag will log when JIT compilation happens.
     * The second flag enables additional flags including -XX:+PrintInlining,
     * which will print what methods are getting inlined and where.
     *
     * 输出不会有calculateSum记录
     * */
    private static void test1() {
        for (int i = 1; i < 100; i++) {
            calculateSum(i);
        }
    }

    /**
     * -XX:+PrintCompilation -XX:+UnlockDiagnosticVMOptions -XX:+PrintInlining
     * 输出有calculateSum记录
     * */
    private static void test2() {
        for (int i = 1; i < 1000; i++) {
            calculateSum(i);
        }
    }

    /**
     * -XX:+PrintCompilation -XX:+UnlockDiagnosticVMOptions -XX:+PrintInlining
     * 输出有多条calculateSum记录, hot
     * */
    private static void test3() {
        for (int i = 1; i < 10000; i++) {
            calculateSum(i);
        }
    }

    /**
     * -XX:+PrintCompilation -XX:+UnlockDiagnosticVMOptions -XX:+PrintInlining -XX:FreqInlineSize=10
     * The -XX:FreqInlineSize flag will limit the method size eligible for inlining to 10 bytes.
     * hot method too big
     * */
    private static void test4() {
        for (int i = 1; i < 10000; i++) {
            calculateSum(i);
        }
    }

    private static long calculateSum(int n) {
        return new ConsecutiveNumbersSum(n).getTotalSum();
    }
}
