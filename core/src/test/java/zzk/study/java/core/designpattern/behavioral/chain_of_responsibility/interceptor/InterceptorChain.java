package zzk.study.java.core.designpattern.behavioral.chain_of_responsibility.interceptor;

import java.util.ArrayList;
import java.util.List;

public class InterceptorChain {

    /** 放拦截器 */
    private final List<Interceptor> interceptors = new ArrayList<>();

    public Object wrapAll(Object target) {
        for (Interceptor interceptor : interceptors) {
            target = interceptor.wrap(target);
        }
        return target;
    }

    public void addInterceptor(Interceptor interceptor) {
        interceptors.add(interceptor);
    }
}