package designpattern.chain_of_responsibility.interceptor;

public interface Interceptor {

    /** 执行拦截逻辑的方法 */
    Object intercept(Invocation invocation);

    /**
     * target是被拦截的对象，作用是给被拦截对象生成一个代理对象，并返回它。
     * 为了方便，可以直接使用Mybatis中org.apache.ibatis.plugin类的wrap方法（是静态方法）生成代理对象
     * 我这里也写了一个Plugin方法
     */
    default Object wrap(Object target) {
        return Wrapper.wrap(target, this);
    }
}