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
}


