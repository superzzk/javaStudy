package leecode.btree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * 1022. 从根到叶的二进制数之和
 * 给出一棵二叉树，其上每个结点的值都是 0 或 1 。
 * 每一条从根到叶的路径都代表一个从最高有效位开始的二进制数。
 * 例如，如果路径为 0 -> 1 -> 1 -> 0 -> 1，那么它表示二进制数 01101，也就是 13 。
 *
 * 对树上的每一片叶子，我们都要找出从根到该叶子的路径所表示的数字。
 *
 * 以 10^9 + 7 为模，返回这些数字之和。
 *
 * 示例：
 *
 * 输入：[1,0,1,0,1,0,1]
 * 输出：22
 * 解释：(100) + (101) + (110) + (111) = 4 + 5 + 6 + 7 = 22
 *
 *
 *
 * 提示：
 *
 *     树中的结点数介于 1 和 1000 之间。
 *     node.val 为 0 或 1 。
 **/
public class Q1022 {
    private int result;
    /**
     * 记录沿途经过的每个值，遇到叶子节点时计算一次值
     */
    public int sumRootToLeaf(TreeNode root) {
        List<Integer> stack = new ArrayList<>();
        helper_recur(root,stack);
        return result;
    }
    private void helper_recur(TreeNode node,List<Integer> list){
        if(node.left==null && node.right==null) {
            int val = 0;
            for (int i : list) {
                val = val <<1 | i;
            }
            val = val << 1 | node.val;
            result += val;
            return;
        }
        list.add(node.val);
        if(node.left!=null)
            helper_recur(node.left, list);

        if(node.right!=null)
            helper_recur(node.right, list) ;

        list.remove(list.size() - 1);
    }

    /**
     * 每个节点将当前计算的结果作为中间值向下传递，到叶子节点时直接利用中间值计算
     */
    public int sumRootToLeaf2(TreeNode root) {
        helper_recur2(root,0);
        return result;
    }
    private void helper_recur2(TreeNode node, int intermediate){
        if(node.left==null && node.right==null) {
            int val = intermediate <<1 | node.val;
            result += val;
            return;
        }

        intermediate = intermediate <<1 | node.val;
        if(node.left!=null)
            helper_recur2(node.left, intermediate);
        if(node.right!=null)
            helper_recur2(node.right, intermediate) ;
    }

    /**
     * 和上面方法思路相同
     * 注意区别是传入中间值的方式
     */
    public int sumRootToLeaf3(TreeNode root) {
        int[] num = new int[2];     //  num[0]当前数，num[1]总和
        preorder(root, num);
        return num[1];
    }

    private void preorder(TreeNode root, int[] num){
        if(root==null)
            return;
        num[0] = (num[0]<<1)|root.val;

        if(root.left==null && root.right==null)
            num[1] = (num[1]+num[0])%1000000007;

        if(root.left!=null)
            preorder(root.left, num);
        if(root.right!=null)
            preorder(root.right, num);
        num[0] >>= 1;
    }

}
