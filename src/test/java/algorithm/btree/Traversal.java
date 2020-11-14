package algorithm.btree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * @author zhangzhongkun
 * @since 2019-10-09 19:51
 **/
public class Traversal {
    Traversal.Solution solution = new Traversal.Solution();

    public static void main(String[] args) {
        Traversal traversal = new Traversal();
        Integer[] a = {1, null, 2, 3};
        TreeNode head = traversal.listToTree(Arrays.asList(a));

        //List<Integer> result = traversal.solution.preorderTraversal(head);
        List<Integer> result = traversal.solution.preorderTraversalIter(head);

        System.out.println(result);
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /**
     * 将数字list转化成Tree
     * eg: [1,null,2,3]
     * to:
     *    1
     *     \
     *      2
     *     /
     *    3
     */
    private TreeNode listToTree(List<Integer> list) {
        TreeNode head = null;
        TreeNode parrent = null;
        boolean isRight = false;
        for (Integer i : list) {
            if (head == null) {
                head = new TreeNode(i);
                parrent = head;
                continue;
            }
            if (i != null) {
                TreeNode node = new TreeNode(i);
                if (isRight) {
                    parrent.right = node;
                    isRight = false;
                } else {
                    parrent.left = node;
                }
                parrent = node;
            } else {
                isRight = true;
            }
        }
        return head;
    }

    class Solution {
        //pre order use recursive
        public List<Integer> preorderTraversal(TreeNode root) {
            List<Integer> result = new ArrayList<>();
            recur_travel(root, result);

            return result;
        }

        private void recur_travel(TreeNode node, List<Integer> result){
            if(node==null)
                return;
            result.add(node.val);

            recur_travel(node.left, result);
            recur_travel(node.right, result);
        }

        //pre order use iterative
        public List<Integer> preorderTraversalIter(TreeNode root) {
            List<Integer> result = new ArrayList<>();
            Deque<TreeNode> stack = new ArrayDeque<>();
            if(root == null)
                return result;
            TreeNode node = root;
            while(true){
                if(node==null){
                    node = stack.pollFirst();
                    if(node == null)
                        break;
                    node = node.right;
                    continue;
                }
                result.add(node.val);
                stack.offerFirst(node);
                node = node.left;
            }

            return result;
        }
    }
}
