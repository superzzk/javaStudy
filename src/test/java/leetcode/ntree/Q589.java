package leetcode.ntree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * 589. N叉树的前序遍历
 * 给定一个 N 叉树，返回其节点值的前序遍历。
 *
 * 说明: 递归法很简单，你可以使用迭代法完成此题吗?
 *
 **/
public class Q589 {
    public List<Integer> preorder(Node root) {

        List<Integer> result = new ArrayList<>();
        helper_recur(result, root);
        return result;
    }

    private void helper_recur(List<Integer> result, Node node){
        if(node==null)
            return;
        result.add(node.val);
        for(Node child : node.children){
            helper_recur(result,child);
        }
    }

    public List<Integer> preorder_iter(Node root){
        Node node = root;
        List<Integer> result = new ArrayList<>();
        if(root==null)
            return result;
        Deque<Node> stack = new ArrayDeque<>();

        stack.offerFirst(root);
        while(!stack.isEmpty()){
           node = stack.pollFirst();
           result.add(node.val);

           for(int i=node.children.size()-1; i>=0; i--){
               stack.offerFirst(node.children.get(i));
           }
        }

        return result;
    }
}


