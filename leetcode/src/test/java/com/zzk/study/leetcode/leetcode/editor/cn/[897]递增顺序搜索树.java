//给你一棵二叉搜索树的
// root ，请你 按中序遍历 将其重新排列为一棵递增顺序搜索树，使树中最左边的节点成为树的根节点，并且每个节点没有左子节点，只有一个右子节点。 
//
// 
//
// 示例 1： 
// 
// 
//输入：root = [5,3,6,2,4,null,8,1,null,null,null,7,9]
//输出：[1,null,2,null,3,null,4,null,5,null,6,null,7,null,8,null,9]
// 
//
// 示例 2： 
// 
// 
//输入：root = [5,1,7]
//输出：[1,null,5,null,7]
// 
//
// 
//
// 提示： 
//
// 
// 树中节点数的取值范围是 [1, 100] 
// 0 <= Node.val <= 1000 
// 
//
// Related Topics 栈 树 深度优先搜索 二叉搜索树 二叉树 👍 341 👎 0


import com.zzk.study.leetcode.leetcode.editor.cn.TreeNode;


class Q897 {
//leetcode submit region begin(Prohibit modification and deletion)


    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     * int val;
     * TreeNode left;
     * TreeNode right;
     * TreeNode() {}
     * TreeNode(int val) { this.val = val; }
     * TreeNode(int val, TreeNode left, TreeNode right) {
     * this.val = val;
     * this.left = left;
     * this.right = right;
     * }
     * }
     */
    class Solution {
        private TreeNode cur;
        private TreeNode res;

        public TreeNode increasingBST(TreeNode root) {
            recur(root);
            cur.left = null;
            cur.right = null;
            return res;
        }

        private void recur(TreeNode node) {
            if (node == null)
                return;
            recur(node.left);
            if (cur == null) {
                cur = node;
                res = node;
            } else {
                cur.right = node;
                cur.left = null;
                cur = cur.right;
            }
            recur(node.right);
        }
    }
//leetcode submit region end(Prohibit modification and deletion)
}