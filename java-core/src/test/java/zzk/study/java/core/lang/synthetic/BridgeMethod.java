package zzk.study.java.core.lang.synthetic;

import org.junit.Test;

import java.lang.reflect.Method;

/**
 * 如果一个类继承了一个范型类或者实现了一个范型接口, 那么编译器在编译这个类的时候就会生成一个叫做桥接方法的混合方法(混合方法简单的说就是由编译器生成的方法,
 * 方法上有synthetic修饰符), 这个方法用于范型的类型安全处理, 用户一般不需要关心桥接方法. 更详细的可以看JSL bridge method
 *
 * 编译器生成bridge method的意义
 * 简单来说, 编译器生成bridge method的目的就是为了和jdk1.5之前的字节码兼容.
 * 因为范型是在jdk1.5之后才引入的. 在jdk1.5之前例如集合的操作都是没有范型支持的, 所以生成的字节码中参数都是用Object接收的,
 * 所以也可以往集合中放入任意类型的对象, 集合类型的校验也被拖到运行期.
 *
 * 但是在jdk1.5之后引入了范型, 因此集合的内容校验被提前到了编译期,
 * 但是为了兼容jdk1.5之前的版本java使用了范型擦除, 所以如果不生成桥接方法就和jdk1.5之前的字节码不兼容了.
 *
 * 上面可以看到在Parent.class中, 由于范型擦除, class文件中范型都是由Object替代了.
 * 所以如果子类中要是不生成bridge method那么子类就没有实现接口中的方法, 这个java语义就不对了(虽然已经生成class文件了, 不会有编译错误)
 *
 */
public class BridgeMethod {
    // 定义一个范型接口
    public interface Parent<T> {
        T bridgeMethod(T param);
    }

    // 定义一个类实现范型接口
    public class Child implements Parent<String> {
        public String bridgeMethod(String param) {
            return param;
        }
    }

    // 测试方法
    @Test
    public void demo() throws Exception {
        // 使用java的多态
        Parent parent = new Child();
        System.out.println(parent.bridgeMethod("abc123"));// 调用的是实际的方法
        Class<? extends Parent> clz = parent.getClass();
        Method method = clz.getMethod("bridgeMethod", Object.class); // 获取桥接方法
        System.out.println(method.isBridge()); // true
        System.out.println(method.invoke(parent, "hello")); // 调用的是桥接方法

        // 调用的是桥接方法, 会报ClassCastException: java.lang.Object cannot be cast to java.lang.String`错误`
        System.out.println(parent.bridgeMethod(new Object()));
    }
}
