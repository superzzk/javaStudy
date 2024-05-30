package com.zzk.study.leetcode.leetcode.editor.cn;//给定一个二叉树，返回它的 后序 遍历。
//
// 示例: 
//
// 输入: [1,null,2,3]  
//   1
//    \
//     2
//    /
//   3 
//
//输出: [3,2,1] 
//
// 进阶: 递归算法很简单，你可以通过迭代算法完成吗？ 
// Related Topics 栈 树


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class Solution145 {
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

        public List<Integer> postorderTraversal(TreeNode root) {
            List<Integer> res = new ArrayList<>();
            Stack<TreeNode> stack = new Stack<>();

            TreeNode node = root;
            TreeNode pre = null;
            while (!stack.isEmpty() || node != null) {
                while (node != null) {
                    stack.push(node);
                    node = node.left;
                }
                node = stack.pop();

                if (node.right == pre || node.right == null) {
                    res.add(node.val);
                    pre = node;
                    node = null;
                }else {
                    stack.push(node);
                    node = node.right;
                }
            }

            return  res;
        }


    }
//leetcode submit region end(Prohibit modification and deletion)

    class SolutionRecur {

        public List<Integer> postorderTraversal(TreeNode root) {
            return recur_help(root);
        }

        private List<Integer> recur_help(TreeNode root) {
            List<Integer> result = new ArrayList<>();
            recur(root, result);
            return result;
        }

        private void recur(TreeNode node, List<Integer> result) {
            if (node == null)
                return;
            recur(node.left, result);
            recur(node.right, result);
            result.add(node.val);
        }
    }
}