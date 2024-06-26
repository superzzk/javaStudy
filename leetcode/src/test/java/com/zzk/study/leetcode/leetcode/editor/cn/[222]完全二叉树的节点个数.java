package com.zzk.study.leetcode.leetcode.editor.cn;//给出一个完全二叉树，求出该树的节点个数。
//
// 说明： 
//
// 完全二叉树的定义如下：在完全二叉树中，除了最底层节点可能没填满外，其余每层节点数都达到最大值，并且最下面一层的节点都集中在该层最左边的若干位置。若最底层为
//第 h 层，则该层包含 1~ 2h 个节点。 
//
// 示例: 
//
// 输入: 
//    1
//   / \
//  2   3
// / \  /
//4  5 6
//
//输出: 6 
// Related Topics 树 二分查找

class Solution222 {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

//leetcode submit region begin(Prohibit modification and deletion)
    class Solution {

        public int countNodes(TreeNode root) {
            if(root == null)
                return 0;
            int left = countNodes(root.left);
            int right = countNodes(root.right);

            return left + right + 1;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)
}