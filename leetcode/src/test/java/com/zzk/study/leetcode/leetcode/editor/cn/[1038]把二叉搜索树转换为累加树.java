package com.zzk.study.leetcode.leetcode.editor.cn;
//给出二叉 搜索 树的根节点，该树的节点值各不相同，请你将其转换为累加树（Greater Sum Tree），使每个节点 node 的新值等于原树中大于或等于
// node.val 的值之和。 
//
// 提醒一下，二叉搜索树满足下列约束条件： 
//
// 
// 节点的左子树仅包含键 小于 节点键的节点。 
// 节点的右子树仅包含键 大于 节点键的节点。 
// 左右子树也必须是二叉搜索树。 
// 
//
// 注意：该题目与 538: https://leetcode-cn.com/problems/convert-bst-to-greater-tree/ 相同
//
//
// 示例 1： 
//
// 输入：[4,1,6,0,2,5,7,null,null,null,3,null,null,null,8]
//输出：[30,36,21,36,35,26,15,null,null,null,33,null,null,null,8]
// 
//
// 示例 2： 
//
// 输入：root = [0,null,1]
//输出：[1,null,1]
// 
//
// 示例 3： 
//
// 输入：root = [1,0,2]
//输出：[3,3,2]
// 
//
// 示例 4： 
//
// 输入：root = [3,2,4,1]
//输出：[7,9,4,10]
// 
//
// 
//
// 提示： 
//
// 
// 树中的节点数介于 1 和 100 之间。 
// 每个节点的值介于 0 和 100 之间。 
// 树中的所有值 互不相同 。 
// 给定的树为二叉搜索树。 
// 
// Related Topics 二叉搜索树 
// 👍 93 👎 0

import com.zzk.study.leetcode.leetcode.editor.cn.util.TreeUtils;

class Q1038_BinarySearchTreeToGreaterSumTree{
    public static void main(String[] args) {
        test3();
    }

    public static void test1(){
        Solution solution = new Q1038_BinarySearchTreeToGreaterSumTree().new Solution();
        TreeNode root = new TreeNode(3);
        TreeNode node1 = new TreeNode(2);
        TreeNode node2 = new TreeNode(4);
        TreeNode node3 = new TreeNode(1);
        root.left = node1;
        root.right = node2;
        node1.left = node3;
        solution.bstToGst(root);

        TreeUtils.printBTreeByLevel(root);
    }

    public static void test2(){
        Solution solution = new Q1038_BinarySearchTreeToGreaterSumTree().new Solution();
        TreeNode root = new TreeNode(1);
        TreeNode node1 = new TreeNode(0);
        TreeNode node2 = new TreeNode(2);
        root.left = node1;
        root.right = node2;
        solution.bstToGst(root);

        TreeUtils.printBTreeByLevel(root);
    }

    public static void test3() {
        Solution solution = new Q1038_BinarySearchTreeToGreaterSumTree().new Solution();
        Integer[] arr = {4, 1, 6, 0, 2, 5, 7, null, null, null, 3, null, null, null, 8};
        TreeNode head = TreeUtils.arrayToBTree(arr);
        solution.bstToGst(head);

        TreeUtils.printBTreeByLevel(head);
    }

//leetcode submit region begin(Prohibit modification and deletion)
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    int sum=0;
    public TreeNode bstToGst(TreeNode root) {
        if(root==null)
            return null;
        recur(root);
        return root;
    }

    //返回右子树最大值
    void recur( TreeNode node ){
        if(node==null)
            return;
        recur(node.right);
        sum += node.val;
        node.val = sum;
        recur(node.left);
    }

}
//leetcode submit region end(Prohibit modification and deletion)

}