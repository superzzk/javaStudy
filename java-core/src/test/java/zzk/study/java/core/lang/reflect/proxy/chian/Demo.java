package zzk.study.java.core.lang.reflect.proxy.chian;

import org.junit.Test;
import zzk.study.java.core.lang.reflect.proxy.ProxyTutorial.*;

import java.io.Console;
import java.io.PrintWriter;

/**
 * TODO:
 *
 * @author zhangzhongkun02
 * @date 2022/1/24 2:35 PM
 */
public class Demo {
    @Test
    public void demo(){
        I i = (I)SynchronizedIH.createProxy(
                TracingIH.createProxy(new Imp(), new PrintWriter(System.out, true))
        );
        i.handle();
    }
}
