package zzk.study.java.core.lang.reflect.proxy.stub;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class Demo {
    /**
     * 没有实现的接口依然可以测试
     * */
    @Test
    public void demo(){
        I i = (I)StubIH.createStub(new Class[]{I.class}, null);
        final int calc = i.calc(0);
        Assertions.assertEquals(0, calc);
    }

    public interface I{
        int calc(int input);
    }
}
