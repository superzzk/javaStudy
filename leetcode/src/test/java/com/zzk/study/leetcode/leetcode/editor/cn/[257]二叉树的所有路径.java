//给你一个二叉树的根节点 root ，按 任意顺序 ，返回所有从根节点到叶子节点的路径。 
//
// 叶子节点 是指没有子节点的节点。 
//
// 示例 1： 
// 
// 
//输入：root = [1,2,3,null,5]
//输出：["1->2->5","1->3"]
// 
//
// 示例 2： 
//
// 
//输入：root = [1]
//输出：["1"]
// 
//
// 
//
// 提示： 
//
// 
// 树中节点的数目在范围 [1, 100] 内 
// -100 <= Node.val <= 100 
// 
//
// Related Topics 树 深度优先搜索 字符串 回溯 二叉树 👍 1136 👎 0

import com.zzk.study.leetcode.explore.basic.algorithm.Tree;
import com.zzk.study.leetcode.leetcode.editor.cn.TreeNode;
import org.assertj.core.util.introspection.Introspection;

import java.util.*;
import java.util.stream.Collectors;

class AllPathsOfBianryTree {
//leetcode submit region begin(Prohibit modification and deletion)

    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     * int val;
     * TreeNode left;
     * TreeNode right;
     * TreeNode() {}
     * TreeNode(int val) { this.val = val; }
     * TreeNode(int val, TreeNode left, TreeNode right) {
     * this.val = val;
     * this.left = left;
     * this.right = right;
     * }
     * }
     */
    class Solution {
        public List<String> binaryTreePaths(TreeNode root) {
            List<String> res = new ArrayList<>();

            if (root == null)
                return res;

            TreeNode node = root;
            LinkedList<TreeNode> intermediate = new LinkedList<>();
            intermediate.addFirst(root);

            while (!intermediate.isEmpty()) {
                node = intermediate.getFirst();
                while (node.left != null) {
                    if(node.right != null)
                        intermediate.addFirst(node.right);
                    node = node.left;
                }

                if (node.right == null) {
                    // add to result
                    String candidate = intermediate.stream().map(String::valueOf)
                            .collect(Collectors.joining("->"));
                    res.add(candidate);
                    // remove current node
                    intermediate.removeLast();
                }
            }

            return res;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

    class SolutionRecur {
        public List<String> binaryTreePaths(TreeNode root) {
            List<String> res = new ArrayList<>();
            recur(res, root, new StringBuilder());
            return res;
        }

        private void recur(List<String> res, TreeNode node, StringBuilder intermediate) {
            if (node.left == null && node.right == null) {
                //记录结果
                String append = intermediate.length() == 0 ? String.valueOf(node.val) : "->" + node.val;
                String s = new String(intermediate);
                res.add(s + append);
                return;
            }

            String append = intermediate.length() == 0 ? String.valueOf(node.val) : "->" + node.val;
            intermediate.append(append);
            if (node.left != null)
                recur(res, node.left, intermediate);
            if (node.right != null)
                recur(res, node.right, intermediate);

            intermediate.delete(intermediate.lastIndexOf(append), intermediate.length());
        }
    }
}