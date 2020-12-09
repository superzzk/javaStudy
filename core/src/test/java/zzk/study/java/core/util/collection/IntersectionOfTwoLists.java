package zzk.study.java.core.util.collection;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zhangzhongkun
 * @since 2019-07-26 15:26
 **/
public class IntersectionOfTwoLists {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("red", "blue", "blue", "green", "red");
        List<String> otherList = Arrays.asList("red", "green", "green", "yellow");

        List<String> rt = intersection(list, otherList);
        List<String> rt2 = intersection2(list, otherList);

        List<String> commonElements = new ArrayList<>(Arrays.asList("red", "green"));

        Assert.assertEquals(commonElements, rt);
        Assert.assertEquals(commonElements, rt2);
    }

    private static List<String> intersection(List<String> list1, List<String> list2){

        List<String> result = list1.stream()
                .distinct()
                .filter(list2::contains)
                .collect(Collectors.toList());

        return result;
    }

    private static List<String> intersection2(List<String> list1, List<String> list2){
        Set<String> result = new HashSet<>(list1);
        result.retainAll(list2);

        return new ArrayList<>(result);
    }
}
