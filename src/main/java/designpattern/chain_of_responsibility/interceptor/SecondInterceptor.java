package designpattern.chain_of_responsibility.interceptor;

public class SecondInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) {
        try {
            return "plugin2 " + invocation.proceed() + " plugin2";
        } catch (Exception e) {
            return null;
        }
    }
//
//    @Override
//    public Object wrap(Object target) {
//        return Wrapper.wrap(target, this);
//    }
}