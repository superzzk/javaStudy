package designpattern.chain_of_responsibility.interceptor;

/**
 * 我现在给一个需求，一个应用返回字符串0，我加一个插件在字符串的左右两边加plugin1，再加一个插件在字符串的左右两边加plugin2
 */
public class Main {

    public static void main(String[] args) {

        // 配置插件
        InterceptorChain interceptorChain = new InterceptorChain();
        interceptorChain.addInterceptor(new FirstInterceptor());
        interceptorChain.addInterceptor(new SecondInterceptor());

        // 获得代理对象
        IGetStr getStr = new GetStrImpl();
        getStr = (IGetStr) interceptorChain.wrapAll(getStr);

        String result = getStr.getStrZero();
        // plugin2 plugin1 0 plugin1 plugin2
        System.out.println(result);

        result = getStr.getStrOne();
        // 1
        System.out.println(result);
    }
}