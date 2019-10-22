package leecode.btree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * 144. 二叉树的前序遍历
 * 给定一个二叉树，返回它的 前序 遍历。
 *
 *  示例:
 *
 * 输入: [1,null,2,3]
 *    1
 *     \
 *      2
 *     /
 *    3
 *
 * 输出: [1,2,3]
 *
 * 进阶: 递归算法很简单，你可以通过迭代算法完成吗？
 **/
public class Q144 {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        helper_recur(result, root);
        return result;
    }

    private void helper_recur(List<Integer> result, TreeNode node){
        if(node==null)
            return;
        result.add(node.val);
        helper_recur(result,node.left);
        helper_recur(result,node.right);
    }

    public List<Integer> preorderTraversal_iter(TreeNode root){
        TreeNode node = root;
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();

        while(node!=null || !stack.isEmpty()){
            while(node!=null){
                result.add(node.val);
                stack.offerFirst(node);
                node = node.left;
            }
            node = stack.pollFirst();
            node = node.right;
        }

        return result;
    }
}
