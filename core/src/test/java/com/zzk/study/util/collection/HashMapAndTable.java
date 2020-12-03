package com.zzk.study.util.collection;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangzhongkun
 * @since 2019-08-05 16:17
 **/
public class HashMapAndTable {

    public static void main(String[] args)
    {
//        testAsStack();
        testHashMap();
    }

    public static void testHashMap(){
        Map<String, String> map = new HashMap<>();
        map.put("", "");
        map.put("", "null");
        map.put("", null);

        map.put(null, "");
        map.put(null, "null");
        map.put(null, null);

        for(Integer i=0; i<20; i++){
            map.put(i.toString(), i.toString());
        }

        for (Map.Entry<String,String > entry : map.entrySet() ) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }
}
