package util.collection;

import java.util.Collections;
import java.util.LinkedList;

/**
 * @author zhangzhongkun
 * @since 2019-07-17 10:21
 **/
public class CollectionsDemo {
    public static void main(String[] args) {
        LinkedList<String> stringList = new LinkedList<String>();
        stringList.add("a");
        stringList.add("c");
        stringList.add("b");
        System.out.println(stringList);

        //sort in descending order
        Collections.sort(stringList, Collections.reverseOrder());
        System.out.println(stringList);

        //sort in ascending order
        Collections.sort(stringList);
        System.out.println(stringList);
    }
}
