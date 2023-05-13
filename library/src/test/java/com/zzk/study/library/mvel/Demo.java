package com.zzk.study.library.mvel;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Test;
import org.mvel2.MVEL;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * https://blog.csdn.net/SunnyYoona/article/details/75244442
 */
public class Demo {
    @Test
    public void empty_test() {
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
    public void access_property() {
        // simple property accessor
        // also can:
        // person.pets["rover"].age   // map accessor
        // person.pets["rover"].?age   // null safe accessor
        Person p = new Person("name", 18);
        Object object = MVEL.eval("person.name", ImmutableMap.builder().put("person", p).build());
        assertEquals(p.getName(), object);

        // array
        int[] arr = {1, 2, 3, 4};
        Object eval = MVEL.eval("arr[0]", ImmutableMap.of("arr", arr));
        assertEquals(1, eval);

        final Object eval1 = MVEL.eval("{1,2,3}");
        System.out.println(eval1);

        // map
        final ImmutableMap<String, Integer> map = ImmutableMap.of("k1", 1,"k2", 2);
        eval = MVEL.eval("map.get('k1')", ImmutableMap.of("map", map));
        assertEquals(1, eval);
        eval = MVEL.eval("map['k1']", ImmutableMap.of("map", map));
        assertEquals(1, eval);
    }

    @Test
    public void flow_control() {
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

    @Test
    public void create_instance(){
        final Object eval = MVEL.eval("new com.zzk.study.library.mvel.Demo$Person().{ name = \"Bobba Fet\", age = \"50\" }");
        System.out.println(eval);
    }

    @Test
    public void null_safe_bean_navigation(){
        final Person mother = new Person("mother", 30);
        final Person child = new Person("child", 5, mother);
        assertEquals("mother", MVEL.eval("getMother().name", child));
        assertNull(MVEL.eval("?mother.name", mother));
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Person {
        String name;
        int age;

        Person mother;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }
}

