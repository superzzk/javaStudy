package com.zzk.study.library.mvel;

import com.google.common.collect.Maps;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;
import org.mvel2.MVEL;

import java.util.Map;

/**
 * https://blog.csdn.net/SunnyYoona/article/details/75244442
 * */
public class Demo {
    @Test
    public void empty_test(){
        String expression = "a == empty && b == empty";
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("a", "");
        paramMap.put("b", null);
        Object object = MVEL.eval(expression, paramMap);
        Assert.assertTrue((boolean) object);

        paramMap.clear();

        expression = "a == null && b == nil";
        paramMap.put("a", null);
        paramMap.put("b", null);
        object = MVEL.eval(expression, paramMap);
        Assert.assertTrue((boolean) object);
    }

    @Test
    public void access_property(){
        Person p = new Person();
        p.setName("ppp");
        String expression = "person.name";
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("person", p);
        Object object = MVEL.eval(expression, paramMap);
        Assert.assertEquals("ppp",p.getName());
    }

    @Test
    public void flow_control(){
        String expression = "if (param > 0) {return \"Greater than zero!\"; } else if (param == -1) { return \"Minus one!\"; } else { return \"Something else!\"; }";
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("param", 2);
        Object object = MVEL.eval(expression, paramMap);
        Assert.assertEquals("Greater than zero!", object);

        paramMap.put("param", -1);
        object = MVEL.eval(expression, paramMap);
        Assert.assertEquals("Minus one!", object);

        paramMap.put("param", -2);
        object = MVEL.eval(expression, paramMap);
        Assert.assertEquals("Something else!", object);
    }


    @Data
    private class Person {
        String name;
        int age;
    }
}

