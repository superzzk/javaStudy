package zzk.study.java.core.designpattern.singleton.lazy;

/**
 * @program: designpattern
 * @description:
 * @author: zhangzhongkun
 * @create: 2019-03-29 09:22
 **/
public class Singleton {
    private static Singleton instance;
    private Singleton (){}
    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
