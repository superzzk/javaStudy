package zzk.study.java.core.algorithm.btree;

import lombok.val;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

//中序遍历
public class InOrderTraversal {
    List<Integer> result = new ArrayList<>();

    public List<Integer> recurTravel(TreeNode root) {
        recur(root);
        return result;
    }

    private void recur(TreeNode node) {
        if (node == null)
            return;
        recur(node.left);
        result.add(node.val);
        recur(node.right);
    }

    public List<Integer> iterTravel(TreeNode root) {
        List<Integer> result = new ArrayList<>();

        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;

        while(node!=null || !stack.isEmpty()){
            while(node!=null){
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            result.add(node.val);
            node=node.right;
        }

        return result;
    }

    @Test
    public void test(){
        InOrderTraversal traversal = new InOrderTraversal();
        Integer[] a = {1, null, 2, 3};
        TreeNode head = TreeNode.fromList(Arrays.asList(a));

        System.out.println(traversal.recurTravel(head));
        System.out.println(traversal.iterTravel(head));
    }
}
