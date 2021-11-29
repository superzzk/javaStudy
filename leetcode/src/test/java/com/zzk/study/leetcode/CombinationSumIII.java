package com.zzk.study.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Find all possible combinations of k numbers that add up to a number n,
 * given that only numbers from 1 to 9 can be used and each combination should be a unique set of numbers.
 *
 * Note:
 *
 *     All numbers will be positive integers.
 *     The solution set must not contain duplicate combinations.
 *
 * Example 1:
 *
 * Input: k = 3, n = 7
 * Output: [[1,2,4]]
 *
 * Example 2:
 *
 * Input: k = 3, n = 9
 * Output: [[1,2,6], [1,3,5], [2,3,4]]
 **/
public class CombinationSumIII {

    @Test
    public void solution1() {
        Solution1 s = new Solution1();

        List<List<Integer>> r = s.combinationSum3(3, 9);
        for (List<Integer> l : r) {
            System.out.println(l);
        }
    }

    @Test
    public void solution2() {
        Solution2 s = new Solution2();

        List<List<Integer>> r = s.combinationSum3(3, 9);
        for (List<Integer> l : r) {
            System.out.println(l);
        }
    }

    class Solution1 {
        public List<List<Integer>> combinationSum3(int k, int n) {
            int[] candidiates = {1,2,3,4,5,6,7,8,9};
            List<List<Integer>> results = new ArrayList<>();

            helper(results, candidiates, k, n, new ArrayList<>(), 0);

            return results;
        }

        private void helper(List<List<Integer>> results,
                       int[] candidiates, int count, int target,
                       List<Integer> intermediate, int index){

            if(target==0 && intermediate.size()==count){
                results.add(new ArrayList<>(intermediate));
                return;
            }
            if(intermediate.size()>=count)
                return;
            if (index >= candidiates.length)
                return;
            if (candidiates[index] > target)
                return;

//            List<Integer> newIntermediate = new ArrayList<>(intermediate);
//            newIntermediate.add(candidiates[index]);
            intermediate.add(candidiates[index]);
            helper(results,candidiates,count,target-candidiates[index],intermediate,index+1);

            intermediate.remove(intermediate.size()-1);
            helper(results,candidiates,count,target,intermediate,index+1);
        }

    }

    class Solution2 {
        int size;
        int target;
        List<List<Integer>>ar = new ArrayList<List<Integer>>();
        public List<List<Integer>> combinationSum3(int k, int n) {
            size = k;
            target = n;
            List<Integer> ark = new ArrayList<Integer>();
            check(0, 1, ark, 0);
            return ar;
        }

        public  void check(int sum, int index , List<Integer>intermediate, int last){
            if(sum == target && intermediate.size() == size){
                ar.add(new ArrayList<>(intermediate));
                return;
            }
            if(sum >= target) return;

            for(int i = index ; i <= 9; i++){
                if(intermediate.size() == size)return;

                intermediate.add(i);
                sum += i;
                last = i;
                check(sum, i+ 1, intermediate, last);
                sum -= last;

                //if(intermediate.size() > 0)
                intermediate.remove(intermediate.size() - 1);
            }
        }
    }

}
