package com.zzk.study.leetcode.leetcode.editor.cn.util;

import com.zzk.study.leetcode.leetcode.editor.cn.TreeNode;
import com.zzk.study.leetcode.leetcode.editor.cn.TreeNode;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

public class TreeUtils {
    public static TreeNode arrayToBTree(Integer[] arr) {
        if (arr == null || arr.length == 0)
            return null;
        TreeNode head = new TreeNode(arr[0]);

        recur(arr, head, 0);
        return head;
    }

    static void recur(Integer[] arr, TreeNode root, int index) {
        int l = index * 2 + 1;
        int r = index * 2 + 2;
        if (l < arr.length) {
            if (arr[l] != null) {
                TreeNode left = new TreeNode(arr[l]);
                root.setLeft(left);
                recur(arr, left, l);
            }
        }
        if (r < arr.length) {
            if (arr[r] != null) {
                TreeNode right = new TreeNode(arr[r]);
                root.setRight(right);
                recur(arr, right, r);
            }
        }
    }

    public static void printBTreePre(TreeNode head) {
        if (head != null) {
            System.out.print(head.val);
            printBTreePre(head.left);
            printBTreePre(head.right);
        }
    }

    public static void printBTreeByLevel(TreeNode head) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(head);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node == null)
                System.out.print("null,");
            else {
                System.out.print(node.val + ",");
                queue.offer(node.left);
                queue.offer(node.right);
            }
        }
    }

    @Test
    public void testPrintPre() {
        Integer[] arr = {1, 2, 3, 4, 5};
        TreeNode head = arrayToBTree(arr);
        printBTreePre(head);
    }

    @Test
    public void testPrintByLevel() {
        Integer[] arr = {1, 2, 3, 4, 5};
        TreeNode head = arrayToBTree(arr);
        printBTreeByLevel(head);
    }

    @Test
    public void testArrWithNull() {
        Integer[] arr = {4, 1, 6, 0, 2, 5, 7, null, null, null, 3, null, null, null, 8};
        TreeNode head = arrayToBTree(arr);
        printBTreeByLevel(head);
    }

}
