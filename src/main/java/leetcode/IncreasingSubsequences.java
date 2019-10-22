package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *  Given an integer array, your task is to find all the different possible increasing subsequences of the given array,
 *  and the length of an increasing subsequence should be at least 2 .
 *
 * Example:
 *
 * Input: [4, 6, 7, 7]
 * Output: [[4, 6], [4, 7], [4, 6, 7], [4, 6, 7, 7], [6, 7], [6, 7, 7], [7,7], [4,7,7]]
 *
 * Note:
 *
 *     The length of the given array will not exceed 15.
 *     The range of integer in the given array is [-100,100].
 *     The given array may contain duplicates, and two equal integers should also be considered as a special case of increasing sequence.
 **/
public class IncreasingSubsequences {
    public static void main(String[] args){
        IncreasingSubsequences demo = new IncreasingSubsequences();
        int[] nums = {1, 3, 5, 5, 7};
        System.out.println(demo.findSubsequences9(nums));
//        testLinkedList();
    }

    private boolean exist(List<List<Integer>> rt, List<Integer> list){
        boolean flag = false;
        for(List<Integer> l : rt){
            if(l.size() == list.size()){
                flag = true;
                for(int i=0; i<l.size(); ++i){
                    if(l.get(i) != list.get(i))
                        flag = false;
                }
            }
            if(flag)
                return flag;
        }
        return flag;
    }

    /*
    * list1已经是一个准备好的数字串，list2也是排序的数字串，
    * 将list1作为头，从list2选取子串，返回所有的组合可能
    * */
    private List<List<Integer>> ff(List<Integer> list1,List<Integer> list2){
        List<List<Integer>> rt = new ArrayList<>();


        return rt;
    }

    /*
     * list已经是一个准备好的数字串，num是选取数串的长度
     * 返回list中所有长度为num的子串，保持顺序
     * */
    private List<List<Integer>> ff2(List<Integer> list,int num){
        List<List<Integer>> rt = new ArrayList<>();


        return rt;
    }

    public List<List<Integer>> findSubsequences00(int[] nums) {
        List<List<Integer>> rt = new ArrayList<>();

        int last;
        List<Integer> temp = new ArrayList<>();
        for(int i=0; i<nums.length; ++i){
            last = nums[i];
            for (int j = i + 1; j < nums.length; ++j) {
                if (nums[j] >= last) {
                    last = nums[j];
                    temp.add(nums[j]);
                }
            }
        }
        return rt;
    }

    public List<List<Integer>> findSubsequences(int[] nums) {
        List<List<Integer>> rt = new ArrayList<>();

        List<Integer> temp = new ArrayList<>();
        int last;
        for(int i=0; i<nums.length; ++i){

            for(int k=1; k<nums.length-i; ++k) {
                temp.clear();
                temp.add(nums[i]);
                last = nums[i];

                for (int j = i + k; j < nums.length; ++j) {
                    if (nums[j] >= last) {
                        last = nums[j];
                        temp.add(nums[j]);
                        ArrayList<Integer> copy = new ArrayList<>(Arrays.asList(new Integer[temp.size()]));
                        Collections.copy(copy, temp);
                        if(!exist(rt,copy))
                            rt.add(copy);
                    }
                }
            }
        }

        return rt;
    }


    public List<List<Integer>> findSubsequences2(int[] nums) {
        List<List<Integer>> rt = new ArrayList<>();

        for(int i=0; i<nums.length; ++i){
            for(int j=i+1; j<nums.length; ++j){
                int a[] = Arrays.copyOfRange(nums,i,j+1);
                List<Integer> list = IntStream.of(a).boxed().collect(Collectors.toList());
                rt.add(list);
            }
        }

        return rt;
    }


    /*
    * Solution
     * */
    public List<List<Integer>> findSubsequences9(int[] nums) {
        List<List<Integer>> res = new LinkedList<>();
        helper(new LinkedList<Integer>(), 0, nums, res);
        return res;
    }
    private void helper(LinkedList<Integer> list, int index, int[] nums, List<List<Integer>> res){
        if(list.size()>1) res.add(new LinkedList<Integer>(list));
        Set<Integer> used = new HashSet<>();
        for(int i = index; i<nums.length; i++){
            if(used.contains(nums[i])) continue;
            if(list.size()==0 || nums[i]>=list.peekLast()){
                used.add(nums[i]);
                list.add(nums[i]);
                helper(list, i+1, nums, res);
                list.remove(list.size()-1);
            }
        }
    }

    public static void testLinkedList(){
        Integer[] a = {1,2,3,4};
        List<Integer> list2 = Arrays.asList(a);

        LinkedList<Integer> list = new LinkedList<>(list2);
        System.out.println(list);
    }
}
