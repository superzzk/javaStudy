package zzk.study.java.core.util.collection.map;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class HashMapDemo {

    @Test
    public void test_null_key_and_value(){
        Map<String, String> map = new HashMap<>();
        map.put("", "");
        Assert.assertEquals("", map.get(""));

        map.put("", "null");
        Assert.assertEquals("null", map.get(""));

        map.put("", null);
        Assert.assertNull(map.get(""));
        Assert.assertTrue(map.containsKey(""));

        Assert.assertFalse(map.containsKey(null));
        map.put(null, "");
        Assert.assertEquals("", map.get(null));
        map.put(null, null);
        Assert.assertNull(map.get(null));
        Assert.assertTrue(map.containsKey(null));
    }

    @Test
    public void test_merge(){
        Map<String, String> map1 = new HashMap<>();
        map1.put("a", "A");
        map1.put("b", "B");
        map1.put("c", "C");
        //if key "a" exist, compute it
        map1.merge("a", "A", (oldValue,value)->{
            return "A plus";
        });
        //if key "z" not exist, put it
        map1.merge("z", "Z", (oldValue,value)->{
            return "A plus";
        });
        System.out.println(map1);
    }
}


