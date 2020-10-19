package leetcode.btree;

/**
 * 98 验证二叉搜索树
 * 给定一个二叉树，判断其是否是一个有效的二叉搜索树。
 *
 * 假设一个二叉搜索树具有如下特征：
 *
 *     节点的左子树只包含小于当前节点的数。
 *     节点的右子树只包含大于当前节点的数。
 *     所有左子树和右子树自身必须也是二叉搜索树。
 *
 * 示例 1:
 *
 * 输入:
 *     2
 *    / \
 *   1   3
 * 输出: true
 *
 * 示例 2:
 *
 * 输入:
 *     5
 *    / \
 *   1   4
 *      / \
 *     3   6
 * 输出: false
 * 解释: 输入为: [5,1,4,null,null,3,6]。
 *      根节点的值为 5 ，但是其右子节点值为 4 。
 *
 **/
public class Q98 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public boolean isValidBST(TreeNode root) {
        if(root==null)
            return true;
        return isValid(root);
    }

    private boolean isValid(TreeNode node){
        if(node==null)
            return true;
        if(!isAllLess(node.left, node.val) || !isAllLarger(node.right,node.val))
            return false;
        return isValid(node.left) && isValid(node.right);
    }

    private boolean isAllLess(TreeNode node, int max){
        if(node == null)
            return true;
        if(node.val>=max)
            return false;
        return isAllLess(node.left, max) && isAllLess(node.right, max);
    }

    private boolean isAllLarger(TreeNode node, int min){
        if(node == null)
            return true;
        if(node.val <=min )
            return false;

        return isAllLarger(node.left, min) && isAllLarger(node.right, min);
    }

    private int getMinOfBST(TreeNode root) {
        int min = root.val;
        while (root != null) {
            if (root.val <= min) {
                min = root.val;
            }
            root = root.left;
        }
        return min;
    }
    private int getMaxOfBST(TreeNode root) {
        int max = root.val;
        while (root != null) {
            if (root.val >= max) {
                max = root.val;
            }
            root = root.right;
        }
        return max;
    }
}
