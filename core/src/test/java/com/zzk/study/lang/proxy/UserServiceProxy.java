package com.zzk.study.lang.proxy;

/**
 * 静态代理
 * 为UserService记录响应时间
 */
public class UserServiceProxy implements IUserService {

    private IUserService userService;

    public UserServiceProxy(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void request() {
        long startTime = System.currentTimeMillis();
        userService.request();
        System.out.println("reques cost :" + (System.currentTimeMillis() - startTime));
    }

    public static void main(String[] args) {
        IUserService userService = new UserServiceImpl();
        UserServiceProxy userServiceProxy = new UserServiceProxy(userService);

        // this is userService
        // reques cost :0
        userServiceProxy.request();
    }
}