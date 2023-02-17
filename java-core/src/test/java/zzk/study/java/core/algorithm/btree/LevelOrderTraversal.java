package zzk.study.java.core.algorithm.btree;

import org.junit.Test;

import java.util.*;

//中序遍历
public class LevelOrderTraversal {

    public List<List<Integer>> recurTravel(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();

        recur(root);
        return result;
    }

    private void recur(TreeNode node) {
        if (node == null)
            return;
        recur(node.left);
        recur(node.right);
    }

    public List<List<Integer>> iterTravel(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();

        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode node = root;
        queue.offer(node);
        while (!queue.isEmpty()) {
            List<Integer> line = new ArrayList<>();
            final int size = queue.size();
            for (int i = 0; i < size; i++) {
                node = queue.poll();
                line.add(node.val);
                if(node.left!=null)
                    queue.offer(node.left);
                if(node.right!=null)
                    queue.offer(node.right);
            }
            result.add(line);
        }

        return result;
    }

    @Test
    public void test(){
        LevelOrderTraversal traversal = new LevelOrderTraversal();
        Integer[] a = {3,9,20,null,null,15,7};
        TreeNode head = TreeNode.fromList(Arrays.asList(a));

//        System.out.println(traversal.recurTravel(head));
        System.out.println(traversal.iterTravel(head));//[[3],[9,20],[15,7]]
    }
}
