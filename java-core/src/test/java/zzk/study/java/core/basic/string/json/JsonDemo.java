package zzk.study.java.core.basic.string.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: javaStudy
 * @description:
 * @author: zhangzhongkun
 * @create: 2019-03-25 19:12
 **/
public class JsonDemo {
    public static void main2(String[] args) {
        Map<String, String> m = new HashMap<>();
        m.put("1", "a");
        m.put("2", "b");
        m.put("3", "c");
        System.out.println(JSON.toJSONString(m));

        Set<String> s = new HashSet<>();
        s.add("abc");
        s.add("def");
        s.add("ghk");

        String jsonStr =  JSON.toJSONString(s);
        System.out.println(jsonStr);

        JSONArray jsonArray = JSON.parseArray(jsonStr);
        System.out.println(jsonArray);



    }

    public static void main(String[] args) {
        String a="50 M";
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(a);
        System.out.println( m.replaceAll("").trim());
    }
}
