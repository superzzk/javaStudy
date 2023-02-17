package com.zzk.study.leetcode.leetcode.editor.cn;
//给你两棵二叉树的根节点 p 和 q ，编写一个函数来检验这两棵树是否相同。
//
// 如果两个树在结构上相同，并且节点具有相同的值，则认为它们是相同的。 
//
// 
//
// 示例 1： 
// 
// 
//输入：p = [1,2,3], q = [1,2,3]
//输出：true
// 
//
// 示例 2： 
// 
// 
//输入：p = [1,2], q = [1,null,2]
//输出：false
// 
//
// 示例 3： 
// 
// 
//输入：p = [1,2,1], q = [1,1,2]
//输出：false
// 
//
// 
//
// 提示： 
//
// 
// 两棵树上的节点数目都在范围 [0, 100] 内 
// -10⁴ <= Node.val <= 10⁴ 
// 
//
// Related Topics 树 深度优先搜索 广度优先搜索 二叉树 👍 932 👎 0


import java.util.Stack;

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

class IsSameTree {

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public boolean isSameTree(TreeNode p, TreeNode q) {
            if(p==null && q==null)
                return true;
            if(p==null || q==null)
                return false;
            Stack<TreeNode> stack1 = new Stack<>();
            Stack<TreeNode> stack2 = new Stack<>();
            stack1.push(p);
            stack2.push(q);
            while (!stack1.empty() && !stack2.empty()) {
                final TreeNode n1 = stack1.pop();
                final TreeNode n2 = stack2.pop();
                if(n1.val!=n2.val) return false;
                if((n1.left==null && n2.left!=null) || (n1.left!=null && n2.left==null)) return false;
                if((n1.right==null && n2.right!=null) || (n1.right!=null && n2.right==null)) return false;
                if (n1.left != null) {
                    stack1.push(n1.left);
                    stack2.push(n2.left);
                }
                if (n2.right != null) {
                    stack1.push(n1.right);
                    stack2.push(n2.right);
                }
            }
            return stack1.empty() && stack2.empty();
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

    class Solution2 {
        public boolean isSameTree(TreeNode p, TreeNode q) {
            if(p==null && q==null) return true;
            if(p==null || q==null)   return false;
            if(p.val !=q.val) return false;
            return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
        }
    }
}