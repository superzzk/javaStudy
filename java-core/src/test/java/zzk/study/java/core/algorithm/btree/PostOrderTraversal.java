package zzk.study.java.core.algorithm.btree;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

//中序遍历
public class PostOrderTraversal {
    List<Integer> result = new ArrayList<>();

    public List<Integer> recurTravel(TreeNode root) {
        recur(root);
        return result;
    }

    private void recur(TreeNode node) {
        if (node == null)
            return;
        recur(node.left);
        recur(node.right);
        result.add(node.val);
    }

    public List<Integer> iterTravel(TreeNode root) {
        List<Integer> result = new ArrayList<>();

        Stack<TreeNode> stack = new Stack<>();
        Stack<TreeNode> res = new Stack<>();

        TreeNode node = root;
        stack.push(node);
        while(!stack.isEmpty()){
            node = stack.pop();
            res.add(node);
            if(node.left!=null)
                stack.add(node.left);
            if(node.right!=null)
                stack.add(node.right);
        }

        while(!res.empty())
            result.add(res.pop().val);
        return result;
    }

    @Test
    public void test(){
        PostOrderTraversal traversal = new PostOrderTraversal();
        Integer[] a = {1, null, 2, 3};
        TreeNode head = TreeNode.fromList(Arrays.asList(a));

        System.out.println(traversal.recurTravel(head));
        System.out.println(traversal.iterTravel(head));
    }
}
