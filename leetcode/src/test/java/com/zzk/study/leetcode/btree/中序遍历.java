package com.zzk.study.leetcode.btree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * 94. 二叉树的中序遍历
 * 给定一个二叉树，返回它的中序 遍历。
 *
 * 示例:
 *
 * 输入: [1,null,2,3]
 *    1
 *     \
 *      2
 *     /
 *    3
 *
 * 输出: [1,3,2]
 *
 * 进阶: 递归算法很简单，你可以通过迭代算法完成吗？
 **/
public class 中序遍历 {

    /**
     * 递归
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        helper(root, result);
        return result;
    }

    private void helper(TreeNode node, List<Integer> result){
        if(node == null)
            return;
        helper(node.left, result);
        result.add(node.val);
        helper(node.right,result);
    }

    /**
     * 迭代
     */
    private List<Integer> iter(TreeNode node){
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();

        while(node!=null || !stack.isEmpty()){
            while(node!=null){
                stack.offerFirst(node);
                node = node.left;
            }
            node = stack.pollFirst();
            result.add(node.val);
            node = node.right;
        }

        return result;
    }
}
