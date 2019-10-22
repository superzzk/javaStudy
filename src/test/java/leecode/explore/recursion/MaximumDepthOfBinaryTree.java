package leecode.explore.recursion;

/**
 * Given a binary tree, find its maximum depth.
 * <p>
 * The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.
 * <p>
 * Note: A leaf is a node with no children.
 * <p>
 * Example:
 * <p>
 * Given binary tree [3,9,20,null,null,15,7],
 * <p>
 * 3
 * / \
 * 9  20
 * /  \
 * 15   7
 * <p>
 * return its depth = 3.
 **/
public class MaximumDepthOfBinaryTree {

    public int maxDepth(TreeNode root) {
        return helper(root);
    }

    private int helper(TreeNode node){
        int maxDepth=0;
        int leftDepth=0;
        int rightDepth=0;
        if(node==null)
            return 0;

        leftDepth = maxDepth(node.left);
        rightDepth = maxDepth(node.left);
        maxDepth = leftDepth > rightDepth ? leftDepth : rightDepth;
        return maxDepth+1;
    }


    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
