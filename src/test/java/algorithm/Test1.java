package algorithm;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 给一个数组，返回数组所有子集合
 * example: [1,2] - >
 * [
 *      [1],[2],[1,2]
 * ]
 **/
public class Test1 {

    @Test
    public void a() {
        List<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);
        source.add(4);

        List<List<Integer>> r = allSubSets(source);

        for (List<Integer> l : r ) {
            System.out.println(l);
        }
    }

    private List<List<Integer>> allSubSets(List<Integer> source){
        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < source.size(); i++) {
            helper(source,new ArrayList<Integer>(),i,result);
        }
        result = result.stream().distinct().collect(Collectors.toList());
        return new ArrayList<>(result);
    }

    private void helper(List<Integer> source, List<Integer> intermediate, int cursor, List<List<Integer>> result){
        if(cursor>source.size())
            return;
        if(intermediate.size()>0)
            result.add(new ArrayList<>(intermediate));

        for(int i=cursor;i<source.size();i++){
            intermediate.add(source.get(cursor));
            helper(source, intermediate, i+1, result);
            intermediate.remove(intermediate.size()-1);
        }

    }
}
