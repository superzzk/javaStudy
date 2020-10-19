package leetcode.explore.basic.algorithm;

/**
 * @author zhangzhongkun
 * @since 2019-10-14 14:17
 **/
public class Tree {
    //Definition for a binary tree node.
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    /**
     * 对称二叉树
     *
     * 给定一个二叉树，检查它是否是镜像对称的。
     *
     * 例如，二叉树 [1,2,2,3,4,4,3] 是对称的。
     *
     *     1
     *    / \
     *   2   2
     *  / \ / \
     * 3  4 4  3
     *
     * 但是下面这个 [1,2,2,null,3,null,3] 则不是镜像对称的:
     *
     *     1
     *    / \
     *   2   2
     *    \   \
     *    3    3
     *
     * 说明:
     *
     * 如果你可以运用递归和迭代两种方法解决这个问题，会很加分。
     */
    public boolean isSymmetric(TreeNode root) {
        return helper_recur(root, root);
    }
    private boolean helper_recur(TreeNode node1, TreeNode node2){
        if(node1==null && node2 ==null)
            return true;
        if(node1==null || node2 ==null)
            return false;
        if(node1.val != node2.val)
            return false;
        return helper_recur(node1.left, node2.right) && helper_recur(node1.right, node1.left);
    }

}
