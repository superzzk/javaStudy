package com.zzk.study.util.collection;

import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @program: javaStudy
 * @author: zhangzhongkun
 * @create: 2019-05-07 11:28
 **/
public class HashMapDemo {

    /**
     * 当插入HashMap的key相同时，会覆盖原有的Value，且返回原Value值
     */
    @Test
    public void test1() {

        HashMap<String,Integer> map = new HashMap<String,Integer>();

        //出入两个Value相同的值，没有问题
        map.put("egg", 1);
        map.put("niu", 1);

        //插入key相同的值，看返回结果
        int egg = map.put("egg", 3);

        System.out.println(egg);   //输出1
        System.out.println(map.get("egg"));   //输出3，将原值1覆盖
        System.out.println(map.get("niu"));   //输出1
    }

    @Test
    public void tt(){
        HashSet<Integer> set = new HashSet<>();
        set.add(1);
        set.add(2);
        StringBuilder sb = new StringBuilder();
        for(int i : set){
            sb.append(i + ",");
        }
        System.out.println(sb);
    }
}


