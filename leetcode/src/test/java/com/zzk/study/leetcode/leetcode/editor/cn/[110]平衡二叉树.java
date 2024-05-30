package com.zzk.study.leetcode.leetcode.editor.cn;//给定一个二叉树，判断它是否是高度平衡的二叉树。
//
// 本题中，一棵高度平衡二叉树定义为： 
//
// 
// 一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过1。 
// 
//
// 示例 1: 
//
// 给定二叉树 [3,9,20,null,null,15,7] 
//
//     3
//   / \
//  9  20
//    /  \
//   15   7 
//
// 返回 true 。 
// 
//示例 2: 
//
// 给定二叉树 [1,2,2,3,3,null,null,4,4] 
//
//        1
//      / \
//     2   2
//    / \
//   3   3
//  / \
// 4   4
// 
//
// 返回 false 。 
//
// 
// Related Topics 树 深度优先搜索

class IsBalancedBinaryTree {
    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public boolean isBalanced(TreeNode root) {
            if(root == null)
                return true;
            return height(root) != -1;
        }

        private int height(TreeNode root) {
            if(root == null)
                return 0;
            int left = height(root.left);
            int right = height(root.right);
            if(left == -1 || right == -1)
                return -1;

            if(Math.abs(left -right) > 1)
                return -1;
            return 1 + Math.max(left, right);
        }

    }
//leetcode submit region end(Prohibit modification and deletion)
}