package com.zzk.study.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 40
 * Given a collection of candidate numbers (candidates) and a target number (target),
 * find all unique combinations in candidates where the candidate numbers sums to target.
 * <p>
 * Each number in candidates may only be used once in the combination.
 * <p>
 * Note:
 * <p>
 * All numbers (including target) will be positive integers.
 * The solution set must not contain duplicate combinations.
 * <p>
 * Example 1:
 * <p>
 * Input: candidates = [10,1,2,7,6,1,5], target = 8,
 * A solution set is:
 * [
 * [1, 7],
 * [1, 2, 5],
 * [2, 6],
 * [1, 1, 6]
 * ]
 * <p>
 * Example 2:
 * <p>
 * Input: candidates = [2,5,2,1,2], target = 5,
 * A solution set is:
 * [
 * [1,2,2],
 * [5]
 * ]
 **/
public class CombinationSumII {
    @Test
    public void solution1() {
        Solution1 s = new Solution1();
        int[] candidates = {10, 1, 2, 7, 6, 1, 5};

        List<List<Integer>> r = s.combinationSum2(candidates, 8);
        for (List<Integer> l : r) {
            System.out.println(l);
        }
    }

    @Test
    public void solution2() {
        Solution2 s = new Solution2();
        int[] candidates = {10, 1, 2, 7, 6, 1, 5};

        List<List<Integer>> r = s.combinationSum2(candidates, 8);
        for (List<Integer> l : r) {
            System.out.println(l);
        }
    }

    @Test
    public void solution3() {
        Solution3 s = new Solution3();
        int[] candidates = {10, 1, 2, 7, 6, 1, 5};

        List<List<Integer>> r = s.combinationSum2(candidates, 8);
        for (List<Integer> l : r) {
            System.out.println(l);
        }
    }

    @Test
    public void solution4() {
        Solution4 s = new Solution4();
        int[] candidates = {10, 1, 2, 7, 6, 1, 5};

        List<List<Integer>> r = s.combinationSum2(candidates, 8);
        for (List<Integer> l : r) {
            System.out.println(l);
        }
    }

    class Solution1 {
        public List<List<Integer>> combinationSum2(int[] candidates, int target) {
            List<List<Integer>> result = new ArrayList<>();
            Arrays.sort(candidates);
            //for (int i = 0; i < candidates.length; i++) {
            helper(candidates, result, target, 0, new ArrayList<Integer>());
            //}
            result = result.stream().distinct().collect(Collectors.toList());

            return result;
        }

        private void helper(int candidates[], List<List<Integer>> result, int target, int index, List<Integer> intermediate) {
            if (target == 0) {
                result.add(new ArrayList<>(intermediate));
                return;
            }
            if (index >= candidates.length || target < 0 || target < candidates[index])
                return;

            for (int i = index; i < candidates.length; i++) {
                intermediate.add(candidates[i]);
                helper(candidates, result, target - candidates[i], i + 1, intermediate);
                intermediate.remove(intermediate.size() - 1);
            }
        }
    }


    class Solution2 {
        /*
        sort array so we can have an early exit once candidates[idx]>target

        for each recursive call, the element is either included or excluded

        if the element is excluded make sure to also exclude duplicates
        i.e. ++i until it's no longer equal to curr element

        once target is 0, add currlist to res
        return

        if idx > len || target < 0 || candidates[idx] > target
        return

        */

        public List<List<Integer>> combinationSum2(int[] candidates, int target) {
            Arrays.sort(candidates);
            List<List<Integer>> res = new ArrayList<>();
            rCS(candidates, target, 0, new ArrayList<>(), res);
            return res;
        }

        public void rCS(int[] candidates, int target, int idx, List<Integer> list, List<List<Integer>> res) {
            if (target == 0) {
                res.add(list);
                return;
            }

            if (idx >= candidates.length || target < 0 || candidates[idx] > target) {
                return;
            }

            List<Integer> iList = new ArrayList<>(list);
            iList.add(candidates[idx]);
            rCS(candidates, target - candidates[idx], idx + 1, iList, res);

            while (idx + 1 < candidates.length && candidates[idx] == candidates[idx + 1]) ++idx;

            rCS(candidates, target, idx + 1, list, res);
        }
    }

    //hand write
    class Solution3 {
        public List<List<Integer>> combinationSum2(int[] candidates, int target) {
            List<List<Integer>> results = new ArrayList<>();
            Arrays.sort(candidates);

            helper(results,candidates,target,new ArrayList<>(), 0);

            return results;
        }

        private void helper(List<List<Integer>> results,
                            int[] candidates, int target,
                            List<Integer> intermediate, int index) {
            if (target == 0) {
                results.add(new ArrayList<>(intermediate));
                return;
            }
            if (target < 0)
                return;
            if (index >= candidates.length)
                return;
            if(candidates[index]>target)
                return;

            for (int i = index; i < candidates.length; i++) {
                intermediate.add(candidates[i]);

                helper(results, candidates, target - candidates[i], intermediate, i+1);
                for(int j=i+1;j<candidates.length;j++){
                    if(candidates[j] == candidates[i])
                        i++;
                }
                intermediate.remove(intermediate.size() - 1);
            }
        }
    }

    //hand write
    class Solution4 {
        public List<List<Integer>> combinationSum2(int[] candidates, int target) {
            List<List<Integer>> results = new ArrayList<>();
            Arrays.sort(candidates);

            helper(results, candidates, target, new ArrayList<>(), 0);

            return results;
        }

        private void helper(List<List<Integer>> results,
                            int[] candidates, int target,
                            List<Integer> intermediate, int index) {
            if (target == 0) {
                results.add(intermediate);
                return;
            }
            if (target < 0)
                return;
            if (index >= candidates.length)
                return;
            if(candidates[index]>target)
                return;

            List<Integer> nextIntermediate = new ArrayList<>(intermediate);
            nextIntermediate.add(candidates[index]);
            helper(results, candidates, target - candidates[index], nextIntermediate, index + 1);

//            for(int j=index+1;j<candidates.length;j++){
//                if(candidates[j] == candidates[index])
//                    index++;
//            }
            while (index + 1 < candidates.length && candidates[index] == candidates[index + 1]) ++index;
            helper(results, candidates, target, intermediate, index+1);
        }
    }
}
