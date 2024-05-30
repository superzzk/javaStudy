package com.zzk.study.leetcode.leetcode.editor.cn;//给你二叉树的根结点 root ，请你将它展开为一个单链表：
//
// 
// 展开后的单链表应该同样使用 TreeNode ，其中 right 子指针指向链表中下一个结点，而左子指针始终为 null 。 
// 展开后的单链表应该与二叉树 先序遍历 顺序相同。 
// 
//
// 
//
// 示例 1： 
// 
// 
//输入：root = [1,2,5,3,4,null,6]
//输出：[1,null,2,null,3,null,4,null,5,null,6]
// 
//
// 示例 2： 
//
// 
//输入：root = []
//输出：[]
// 
//
// 示例 3： 
//
// 
//输入：root = [0]
//输出：[0]
// 
//
// 
//
// 提示： 
//
// 
// 树中结点数在范围 [0, 2000] 内 
// -100 <= Node.val <= 100 
// 
//
// 
//
// 进阶：你可以使用原地算法（O(1) 额外空间）展开这棵树吗？ 
//
// Related Topics 栈 树 深度优先搜索 链表 二叉树 👍 1675 👎 0

import com.zzk.study.leetcode.explore.basic.algorithm.Tree;
import com.zzk.study.leetcode.leetcode.editor.cn.TreeNode;

import java.util.Stack;

class Q114 {
    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        TreeNode pre = null;
        public void flatten(TreeNode root) {
            if(root == null){
                return;
            }
            if(pre!=null)
                pre.right = root;
            TreeNode temp = root.right;
            root.right = root.left;
            root.left = null;
            pre = root;
            flatten(root.right);
            flatten(temp);
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

    // 栈
    class Solution1 {
        public void flatten(TreeNode root) {
            Stack<TreeNode> stack = new Stack<>();
            Stack<TreeNode> stack2 = new Stack<>();
            while (root != null || !stack.isEmpty()) {
                while (root != null) {
                    stack2.push(root);
                    stack.push(root);
                    root = root.left;
                }
                root = stack.pop();
                root = root.right;
            }

            TreeNode pre = null;
            while (!stack2.isEmpty()) {
                root = stack2.pop();
                root.right = pre;
                root.left = null;
                pre = root;
            }
        }
    }
}