package com.zzk.study.leetcode.btree;

/**
 * 104
 * Given a binary tree, find its maximum depth.
 * <p>
 * The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.
 * <p>
 * Note: A leaf is a node with no children.
 * <p>
 * Example:
 * <p>
 * Given binary tree [3,9,20,null,null,15,7],
 * <p>
 * 3
 * / \
 * 9  20
 * /  \
 * 15   7
 * <p>
 * return its depth = 3.
 **/
public class Q104_MaximumDepthOfBinaryTree {
    Solution s = new Solution();

    public static void main(String[] args) {
        int[] nums = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        Q104_MaximumDepthOfBinaryTree ooo = new Q104_MaximumDepthOfBinaryTree();
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
        public int maxDepth(TreeNode root) {
            int max = 0;
            if (root == null)
                return 0;
            if (root.left == null && root.right == null)
                return 1;

            int leftDepth = maxDepth(root.left);
            int rightDepth = maxDepth(root.right);
            int childDepth = leftDepth > rightDepth ? leftDepth : rightDepth;

            return childDepth+1;
        }
    }

    class Solution2 {
        public int maxDepth(TreeNode root) {
            return (root == null)? 0 : Math.max(maxDepth(root.left), maxDepth(root.right))+1;
        }
    }
}
