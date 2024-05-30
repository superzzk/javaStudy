//给定一个二叉树的根节点 root ，返回 它的 中序 遍历 。 
//
// 
//
// 示例 1： 
// 
// 
//输入：root = [1,null,2,3]
//输出：[1,3,2]
// 
//
// 示例 2： 
//
// 
//输入：root = []
//输出：[]
// 
//
// 示例 3： 
//
// 
//输入：root = [1]
//输出：[1]
// 
//
// 
//
// 提示： 
//
// 
// 树中节点数目在范围 [0, 100] 内 
// -100 <= Node.val <= 100 
// 
//
// 
//
// 进阶: 递归算法很简单，你可以通过迭代算法完成吗？ 
//
// Related Topics 栈 树 深度优先搜索 二叉树 👍 2078 👎 0

import com.zzk.study.leetcode.leetcode.editor.cn.TreeNode;

import java.util.*;

class BinaryTreeInorderTraversal {

//leetcode submit region begin(Prohibit modification and deletion)

    class Solution {
        public List<Integer> inorderTraversal(TreeNode root) {
            List<Integer> res = new ArrayList<>();
            Stack<TreeNode> stack = new Stack<>();

            TreeNode node = root;
            while (!stack.isEmpty() || node != null) {
                while (node != null) {
                    stack.push(node);
                    node = node.left;
                }
                node = stack.pop();
                res.add(node.val);
                node = node.right;
            }
            return  res;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

    class SolutionRecursive {
        public List<Integer> inorderTraversal(TreeNode root) {
            List<Integer> res = new ArrayList<>();
            recur(root, res);
            return  res;
        }

        private void recur(TreeNode node, List<Integer> res) {
            if(node == null)
                return;

            recur(node.left, res);
            res.add(node.val);
            recur(node.right, res);
        }
    }

    private List<Integer> it2(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        if(root==null)
            return result;
        TreeNode node = root;
        while(node!=null){
            stack.offer(node);
            node = node.left;
        }
        node = stack.pollLast();
        while(node!=null){
            result.add(node.val);
            node = node.right;
            while(node!=null){
                stack.offer(node);
                node = node.left;
            }
            node = stack.pollLast();
        }

        return result;
    }
}