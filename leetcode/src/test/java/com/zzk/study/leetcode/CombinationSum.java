package com.zzk.study.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 39
 * Given a set of candidate numbers (candidates) (without duplicates) and a target number (target),
 * find all unique combinations in candidates where the candidate numbers sums to target.
 * <p>
 * The same repeated number may be chosen from candidates unlimited number of times.
 * <p>
 * Note:
 * <p>
 * All numbers (including target) will be positive integers.
 * The solution set must not contain duplicate combinations.
 * <p>
 * Example 1:
 * <p>
 * Input: candidates = [2,3,6,7], target = 7,
 * A solution set is:
 * [
 * [7],
 * [2,2,3]
 * ]
 * <p>
 * Example 2:
 * <p>
 * Input: candidates = [2,3,5], target = 8,
 * A solution set is:
 * [
 * [2,2,2,2],
 * [2,3,3],
 * [3,5]
 * ]
 **/
public class CombinationSum {
    @Test
    public void solution1() {
        Solution1 s = new Solution1();
        int[] candidates = {2, 3, 6, 7};

        List<List<Integer>> r = s.combinationSum(candidates, 7);
        for (List<Integer> l : r) {
            System.out.println(l);
        }
    }

    @Test
    public void solution2() {
        Solution2 s = new Solution2();
        int[] candidates = {2, 3, 6, 7};

        List<List<Integer>> r = s.combinationSum2(candidates, 7);
        for (List<Integer> l : r) {
            System.out.println(l);
        }
    }

    @Test
    public void solution3() {
        Solution3 s = new Solution3();
        int[] candidates = {2, 3, 6, 7};

        List<List<Integer>> r = s.combinationSum(candidates, 7);
        for (List<Integer> l : r) {
            System.out.println(l);
        }
    }

    @Test
    public void solution4() {
        Solution4 s = new Solution4();
        int[] candidates = {2, 3, 6, 7};

        List<List<Integer>> r = s.combinationSum(candidates, 7);
        for (List<Integer> l : r) {
            System.out.println(l);
        }
    }

    class Solution1 {
        public List<List<Integer>> combinationSum(int[] candidates, int target) {
            List<List<Integer>> answers = new ArrayList();
            if (candidates == null || candidates.length == 0) return answers;

            help(answers, new ArrayList(), candidates, target, 0);

            return answers;
        }

        private void help(List<List<Integer>> answers,
                          List<Integer> intermediate,
                          int[] candidates,
                          int target,
                          int index) {
            if (target < 0) return;

            if (target == 0) {
                answers.add(new ArrayList(intermediate));
                return;
            }

            for (int i = index; i < candidates.length; i++) {
                intermediate.add(candidates[i]);
                help(answers, intermediate, candidates, target - candidates[i], i);
                intermediate.remove(intermediate.size() - 1);
            }
        }

    }

    class Solution2 {
        public List<List<Integer>> combinationSum2(int[] arr, int target) {
            List<List<Integer>> outer = new LinkedList<List<Integer>>();
            int n = arr.length;
            for (int i = 0; i < n; ++i) {
                helper(arr, i, target, new LinkedList<Integer>(), outer);
            }
            return outer;
        }

        public void helper(int[] arr, int i, int sum, LinkedList<Integer> inner, List<List<Integer>> outer) {
            int n = arr.length;
            if (i == n || sum < 0) {
                return;
            }
            sum -= arr[i];
            inner.add(arr[i]);
            if (sum == 0) {
                outer.add(inner);
                return;
            }
            for (int j = i; j < n; ++j) {
                helper(arr, j, sum, new LinkedList(inner), outer);
            }
            return;
        }
    }

    class Solution3 {
        public List<List<Integer>> combinationSum(int[] candidates, int target) {
            List<List<Integer>> results = new ArrayList<>();

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

            for (int i = index; i < candidates.length; i++) {
                intermediate.add(candidates[i]);
                helper(results, candidates, target - candidates[i], intermediate, i);
                intermediate.remove(intermediate.size() - 1);
            }
        }
    }

    class Solution4 {
        public List<List<Integer>> combinationSum(int[] candidates, int target) {
            List<List<Integer>> results = new ArrayList<>();
            Arrays.sort(candidates);
            helper(results,candidates,target,new ArrayList<>(), 0);

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

            List<Integer> newIntermediate = new ArrayList<>(intermediate);
            newIntermediate.add(candidates[index]);

            helper(results,candidates,target-candidates[index],newIntermediate,index);
            helper(results,candidates,target,intermediate,index+1);
        }
    }
}

