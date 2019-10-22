package util.collection;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @program: javaStudy
 * @description:
 * @author: zhangzhongkun
 * @create: 2019-02-26 18:36
 **/
public class LinkedHashMapDemo {
    public static void main(String[] args)
    {
        LinkedHashMap<String, String> linkedHashMap =
                new LinkedHashMap<String, String>(16, 0.75f, true);
        linkedHashMap.put("111", "111");
        linkedHashMap.put("222", "222");
        linkedHashMap.put("333", "333");
        linkedHashMap.put("444", "444");
        loopLinkedHashMap(linkedHashMap);//111=111	222=222	333=333	444=444
        //每次访问一个元素（get或put），被访问的元素都被提到最后面去了
        linkedHashMap.get("111");
        loopLinkedHashMap(linkedHashMap);//222=222	333=333	444=444	111=111
        linkedHashMap.put("222", "2222");
        loopLinkedHashMap(linkedHashMap);//333=333	444=444	111=111	222=2222
    }

    public static void loopLinkedHashMap(LinkedHashMap<String, String> linkedHashMap)
    {
        Set<Map.Entry<String, String>> set = linkedHashMap.entrySet();
        Iterator<Map.Entry<String, String>> iterator = set.iterator();

        while (iterator.hasNext())
        {
            System.out.print(iterator.next() + "\t");
        }
        System.out.println();
    }
}
