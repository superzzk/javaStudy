//给定二叉树的根节点 root ，返回所有左叶子之和。 
//
// 
//
// 示例 1： 
//
// 
//
// 
//输入: root = [3,9,20,null,null,15,7] 
//输出: 24 
//解释: 在这个二叉树中，有两个左叶子，分别是 9 和 15，所以返回 24
// 
//
// 示例 2: 
//
// 
//输入: root = [1]
//输出: 0
// 
//
// 
//
// 提示: 
//
// 
// 节点数在 [1, 1000] 范围内 
// -1000 <= Node.val <= 1000 
// 
//
// 
//
// Related Topics 树 深度优先搜索 广度优先搜索 二叉树 👍 710 👎 0

import com.zzk.study.leetcode.leetcode.editor.cn.TreeNode;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

class SumOfLeftLeaves_404 {
    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public int sumOfLeftLeaves(TreeNode root) {
            int res=0;
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);

            while (!queue.isEmpty()) {
                root = queue.poll();
                if (root.left != null) {
                    if (isLeaf(root.left))
                        res += root.left.val;
                    queue.offer(root.left);
                }
                if (root.right != null) {
                    queue.offer(root.right);
                }
            }
            return  res;
        }

        private boolean isLeaf(TreeNode node) {
            return node.left == null && node.right == null;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

    class SolutionRecur {
        public int sumOfLeftLeaves(TreeNode root) {
            return recur(root, false);
        }

        private int recur(TreeNode node, boolean isLeft) {
            if (node == null)
                return 0;

            int left = recur(node.left, true);
            int right = recur(node.right, false);
            boolean isLeaf = node.left == null && node.right == null;
            return isLeft && isLeaf ? node.val + left + right : left + right;
        }
    }

    class SolutionDFS {
        public int sumOfLeftLeaves(TreeNode root) {
            Stack<TreeNode> stack = new Stack<>();
            int res = 0;
            while (!stack.isEmpty() || root != null) {
                boolean flag = root != null && root.left != null;
                while (root != null) {
                    stack.push(root);
                    root = root.left;
                }

                root = stack.pop();

                if (flag && root.left == null && root.right == null)
                    res += root.val;

                root = root.right;
            }
            return res;
        }
    }

    class SolutionBFS {
        public int sumOfLeftLeaves(TreeNode root) {
            int res=0;
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);

            while (!queue.isEmpty()) {
                root = queue.poll();
                if (root.left != null) {
                    if (isLeaf(root.left))
                        res += root.left.val;
                    queue.offer(root.left);
                }
                if (root.right != null) {
                    queue.offer(root.right);
                }
            }
            return  res;
        }

        private boolean isLeaf(TreeNode node) {
            return node.left == null && node.right == null;
        }
    }
}