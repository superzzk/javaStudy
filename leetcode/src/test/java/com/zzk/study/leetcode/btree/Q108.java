package com.zzk.study.leetcode.btree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 108. 将有序数组转换为二叉搜索树
 * 将一个按照升序排列的有序数组，转换为一棵高度平衡二叉搜索树。
 *
 * 本题中，一个高度平衡二叉树是指一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1。
 *
 * 示例:
 *
 * 给定有序数组: [-10,-3,0,5,9],
 *
 * 一个可能的答案是：[0,-3,9,-10,null,5]，它可以表示下面这个高度平衡二叉搜索树：
 *
 *       0
 *      / \
 *    -3   9
 *    /   /
 *  -10  5
 **/
public class Q108 {
    /**
     * 递归
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        return helper(nums, 0, nums.length - 1);
    }
    private TreeNode helper(int[] nums, int start, int end){
        if(start > end)
            return null;
        int mid = start + (end-start)/2;
        TreeNode node = new TreeNode(nums[mid]);
        TreeNode left = helper(nums,start,mid-1);
        if(left!=null)
            node.left = left;
        TreeNode right = helper(nums,mid+1,end);
        if(right!=null)
            node.right = right;
        return node;
    }


    /**
     * 栈 DFS
     */
    public TreeNode sortedArrayToBST2(int[] nums) {
        if (nums.length == 0) {
            return null;
        }
        //第一步，把根节点存起来
        Stack<MyTreeNode> rootStack = new Stack<>();
        int start = 0;
        int end = nums.length;
        int mid = (start + end) >>> 1;
        TreeNode root = new TreeNode(nums[mid]);
        TreeNode curRoot = root;
        rootStack.push(new MyTreeNode(root, start, end));

        while (end - start > 1 || !rootStack.isEmpty()) {
            //考虑左子树
            while (end - start > 1) {
                mid = (start + end) >>> 1; //当前根节点
                end = mid;//左子树的结尾
                mid = (start + end) >>> 1;//左子树的中点
                curRoot.left = new TreeNode(nums[mid]);
                curRoot = curRoot.left;
                rootStack.push(new MyTreeNode(curRoot, start, end));
            }
            //出栈考虑右子树
            MyTreeNode myNode = rootStack.pop();
            //当前作为根节点的 start end 以及 mid
            start = myNode.start;
            end = myNode.end;
            mid = (start + end) >>> 1;
            start = mid + 1; //右子树的 start
            curRoot = myNode.root; //当前根节点
            if (start < end) { //判断当前范围内是否有数
                mid = (start + end) >>> 1; //右子树的 mid
                curRoot.right = new TreeNode(nums[mid]);
                curRoot = curRoot.right;
                rootStack.push(new MyTreeNode(curRoot, start, end));
            }
        }
        return root;
    }
    class MyTreeNode {
        TreeNode root;
        int start;
        int end;

        MyTreeNode(TreeNode r, int s, int e) {
            this.root = r;
            this.start = s;
            this.end = e;
        }
    }

    /**
     * 队列 BFS
     */
    public TreeNode sortedArrayToBST3(int[] nums) {
        if (nums.length == 0) {
            return null;
        }
        Queue<MyTreeNode> rootQueue = new LinkedList<>();
        TreeNode root = new TreeNode(0);
        rootQueue.offer(new MyTreeNode(root, 0, nums.length));
        while (!rootQueue.isEmpty()) {
            MyTreeNode myRoot = rootQueue.poll();
            int start = myRoot.start;
            int end = myRoot.end;
            int mid = (start + end) >>> 1;
            TreeNode curRoot = myRoot.root;
            curRoot.val = nums[mid];
            if (start < mid) {
                curRoot.left = new TreeNode(0);
                rootQueue.offer(new MyTreeNode(curRoot.left, start, mid));
            }
            if (mid + 1 < end) {
                curRoot.right = new TreeNode(0);
                rootQueue.offer(new MyTreeNode(curRoot.right, mid + 1, end));
            }
        }

        return root;
    }


}
