package leetcode.btree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class 后序遍历 {
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> rt = new ArrayList<>();
        if(root==null)
            return rt;

        Stack<TreeNode> res = new Stack<>();
        Stack<TreeNode> stack = new Stack<>();

        TreeNode node = root;
        stack.push(node);
        while(!stack.empty()){
            TreeNode n = stack.pop();
            res.push(n);
            if(n.left!=null)
                stack.push(n.left);
            if(n.right!=null)
                stack.push(n.right);
        }

        while(!res.empty()){
            TreeNode n = res.pop();
            rt.add(n.val);
        }

        return rt;
    }

}
