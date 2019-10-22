package designpattern.chain_of_responsibility.interceptor;

public class FirstInterceptor implements Interceptor {

    /** 执行拦截逻辑的方法 */
    @Override
    public Object intercept(Invocation invocation) {
        try {
            return "plugin1 " + invocation.proceed() + " plugin1";
        } catch (Exception e) {
            return null;
        }
    }

    /** 为原先的类生成代理对象 */
//    @Override
//    public Object wrap(Object target) {
//        return Wrapper.wrap(target, this);
//    }
}