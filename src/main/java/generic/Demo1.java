package generic;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: javaStudy
 * @author: zhangzhongkun
 * @create: 2019-06-19 11:21
 **/
public class Demo1 {
    public static void main(String[] args) {
        Demo1 d1 = new Demo1();
        List<String> list =new ArrayList<>();
        list.add("aaa");
        list.add("bbb");

        String tt = d1.getObj(list);
        System.out.println(tt);
    }

    private <E> E getObj(List<E> list){
        return list.get(0);
    }
}
