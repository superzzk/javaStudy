package com.zzk.study.leetcode;

/**
 * 100
 * Given two binary trees, write a function to check if they are the same or not.
 * <p>
 * Two binary trees are considered the same if they are structurally identical and the nodes have the same value.
 * <p>
 * Example 1:
 * <p>
 * Input:     1         1
 * / \       / \
 * 2   3     2   3
 * <p>
 * [1,2,3],   [1,2,3]
 * <p>
 * Output: true
 * <p>
 * Example 2:
 * <p>
 * Input:     1         1
 * /           \
 * 2             2
 * <p>
 * [1,2],     [1,null,2]
 * <p>
 * Output: false
 * <p>
 * Example 3:
 * <p>
 * Input:     1         1
 * / \       / \
 * 2   1     1   2
 * <p>
 * [1,2,1],   [1,1,2]
 * <p>
 * Output: false
 **/
public class Q100_SameTree {
    Solution s = new Solution();

    public static void main(String[] args) {
        int[] nums = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        Q100_SameTree ooo = new Q100_SameTree();
    }
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    class Solution {
        public boolean isSameTree(TreeNode p, TreeNode q) {
            if(p==q)
                return true;
            if(p==null || q ==null)
                return false;
            if(p.val == q.val){
                if(!isSameTree(p.left,q.left))
                    return false;
                if(!isSameTree(p.right,q.right))
                    return false;
            }else
                return false;

            return true;
        }
    }

    public boolean isSameTree2(TreeNode p, TreeNode q) {
        if(p == null && q == null)
            return true;
        if(p == null || q == null || p.val != q.val)
            return false;
        return isSameTree2(p.left, q.left) && isSameTree2(p.right, q.right);
    }


}
