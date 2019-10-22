package basic.boxing;


public class Demo {
    public static void main(String[] args) {
        System.out.println("----------boxingTest------------");
        boxingTest();
        System.out.println("----------unboxingTest------------");
        unboxingTest();
        System.out.println("----------unboxingTest2------------");
        unboxingTest2();
        System.out.println("----------mainTest------------");
        mainTest();
    }

    public static void boxingTest() {
        Integer i1 = 17;
        Integer i2 = 17;
        Integer i3 = 137;
        Integer i4 = 137;
        // 自动装箱的时候会自动调用Integer的valueOf方法，已在前面赋值的时候完成
        // Integer对象自动缓存int值范围在low~high（-128~127），如果超出这个范围则会自动装箱为包装类。
        System.out.println(i1 == i2);//true
        System.out.println(i3 == i4);//false
    }

    public static void unboxingTest() {
        Integer i1 = 17;
        int i2 = 17;
        int i3 = 137;
        Integer i4 = 137;
        //当包装器类型和基本数据类型进行“==”比较时，包装器类型会自动拆箱为基本数据类型。
        System.out.println(i1 == i2);//true
        System.out.println(i3 == i4);//true
    }

    public static void unboxingTest2() {
        Integer i1 = 17;
        Integer i2 = 17;
        Integer i3 = 137;
        Integer i4 = 137;
// ==
        System.out.println(i1 == i2);//true
        System.out.println(i3 == i4);//false
// equals
        System.out.println(i1.equals(i2));//true
        System.out.println(i3.equals(i4));//true
    }

    public static void mainTest() {
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;
        Long h = 2L;

        // 会自动拆箱（会调用intValue方法）
        System.out.println(c == d);//true
        // 会自动拆箱后再自动装箱
        System.out.println(e == f);//false
        // 虽然“==”比较的是引用的是否是同一对象，但这里有算术运算，如果该引用为包装器类型则会导致自动拆箱
        System.out.println(c == (a + b));//true

        // equals 比较的是引用的对象的内容（值）是否相等，但这里有算术运算，如果该引用为包装器类型则会导
        // 致自动拆箱，再自动装箱
        // a+b触发自动拆箱得到值后，再自动装箱与c比较
        System.out.println(c.equals(a + b));//true

        // 首先a+b触发自动拆箱后值为int型，所以比较的是值是否相等
        System.out.println(g == (a + b));//true

        // 首先a+b触发自动拆箱后值为int型，自动装箱后为Integer型，然后g为Long型
        System.out.println(g.equals(a + b));

        // 首先a+h触发自动拆箱后值为long型，因为int型的a会自动转型为long型的g然后自动装箱后为Long型，
        // 而g也为Long型
        System.out.println(g.equals(a + h));
    }

}
