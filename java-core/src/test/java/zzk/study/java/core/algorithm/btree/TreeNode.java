package zzk.study.java.core.algorithm.btree;

import java.util.List;

/**
 * TODO:
 *
 * @author zhangzhongkun02
 * @date 2022/6/19 12:12 PM
 */
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    public TreeNode(){}

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public TreeNode(int x) {
        val = x;
    }


    /**
     * 将数字list转化成Tree
     * eg: [1,null,2,3]
     * to:
     * 1
     *  \
     *   2
     *  /
     * 3
     */
    public static TreeNode fromList(List<Integer> list) {
        TreeNode head = null;
        TreeNode parrent = null;
        boolean isRight = false;
        for (Integer i : list) {
            if (head == null) {
                head = new TreeNode(i);
                parrent = head;
                continue;
            }
            if (i != null) {
                TreeNode node = new TreeNode(i);
                if (isRight) {
                    parrent.right = node;
                    isRight = false;
                } else {
                    parrent.left = node;
                }
                parrent = node;
            } else {
                isRight = true;
            }
        }
        return head;
    }
}
