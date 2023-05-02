package com.zzk.study.leetcode.leetcode.editor.cn;//给定一个二叉树，返回它的中序 遍历。
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
//输出: [1,3,2] 
//
// 进阶: 递归算法很简单，你可以通过迭代算法完成吗？ 
// Related Topics 栈 树 哈希表


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.*;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution94 {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }


    public List<Integer> inorderTraversal(TreeNode root) {
//        return recur(root);
        return it2(root);
    }

    private List<Integer> recur(TreeNode root){
        List<Integer> result = new ArrayList<>();
        if(root==null)
            return  result;
        recursive(root,result);
        return result;
    }

    private void recursive(TreeNode root, List<Integer> result) {
        if(root==null)
            return;

        recursive(root.left,result);
        result.add(root.val);
        recursive(root.right, result);
    }

    private List<Integer> it1(TreeNode node) {
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();

        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            res.add(node.val);
            node = node.right;
        }

        return res;
    }
    private List<Integer> it2(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        if(root==null)
            return result;
        TreeNode node = root;
        while(node!=null){
            stack.offer(node);
            node = node.left;
        }
        node = stack.pollLast();
        while(node!=null){
            result.add(node.val);
            node = node.right;
            while(node!=null){
                stack.offer(node);
                node = node.left;
            }
            node = stack.pollLast();
        }

        return result;
    }

}
//leetcode submit region end(Prohibit modification and deletion)

