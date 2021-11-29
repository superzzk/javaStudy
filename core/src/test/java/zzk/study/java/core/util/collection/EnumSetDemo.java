package zzk.study.java.core.util.collection;

import org.junit.Test;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class EnumSetDemo {
    enum Season {
        SPRING, SUMMER, FALL, WINTER
    }

    @Test
    public void main() {
        //创建一个EnumSet集合，集合元素就是Season枚举类的全部枚举值
        EnumSet<Season> es1 = EnumSet.allOf(Season.class);
        assertEquals("[SPRING, SUMMER, FALL, WINTER]", es1.toString());

        //创建一个EnumSet空集合，指定其集合元素是Season类的枚举值
        EnumSet<Season> es2 = EnumSet.noneOf(Season.class);
        assertEquals("[]", es2.toString());

        //手动添加两个元素
        es2.add(Season.WINTER);
        es2.add(Season.SPRING);
        assertEquals("[SPRING, WINTER]", es2.toString());

        //以指定枚举值创建EnumSet集合
        EnumSet<Season> es3 = EnumSet.of(Season.SUMMER, Season.WINTER);
        assertEquals("[SUMMER, WINTER]", es3.toString());

        //创建一个包含两个枚举值范围内所有枚举值的EnumSet集合
        EnumSet<Season> es4 = EnumSet.range(Season.SUMMER, Season.WINTER);
        assertEquals("[SUMMER, FALL, WINTER]", es4.toString());

        //es5集合元素 + es4集合元素=Season枚举类的全部枚举值
        EnumSet<Season> es5 = EnumSet.complementOf(es4);
        assertEquals("[SPRING]", es5.toString());

        //创建一个集合
        HashSet<Season> c = new HashSet<>();
        c.add(Season.SPRING);
        c.add(Season.WINTER);
        //复制Collection集合中的所有元素来创建EnumSet集合
        EnumSet<Season> es6 = EnumSet.copyOf(c);
        assertEquals("[SPRING, WINTER]", es6.toString());
    }
}
