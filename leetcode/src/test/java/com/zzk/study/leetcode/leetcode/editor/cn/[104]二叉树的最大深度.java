//给定一个二叉树 root ，返回其最大深度。 
//
// 二叉树的 最大深度 是指从根节点到最远叶子节点的最长路径上的节点数。 
//
// 
//
// 示例 1： 
//
// 
//
// 
//
// 
//输入：root = [3,9,20,null,null,15,7]
//输出：3
// 
//
// 示例 2： 
//
// 
//输入：root = [1,null,2]
//输出：2
// 
//
// 
//
// 提示： 
//
// 
// 树中节点的数量在 [0, 10⁴] 区间内。 
// -100 <= Node.val <= 100 
// 
//
// Related Topics 树 深度优先搜索 广度优先搜索 二叉树 👍 1823 👎 0

import com.zzk.study.leetcode.leetcode.editor.cn.TreeNode;

class MaxDepthOfBinaryTree {
    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        private Integer res=0;

        public int maxDepth(TreeNode root) {
            if(root == null)
                return 0;
            backtrack(root, 1);
            return res;
        }

        private void backtrack(TreeNode node, int level) {
            if (node == null)
                return;
            if (node.left == null && node.right == null)
                res = Math.max(level, res);
            backtrack(node.left, level + 1);
            backtrack(node.right, level + 1);
        }
    }


//leetcode submit region end(Prohibit modification and deletion)

    class SolutionRecur {
        public int maxDepth(TreeNode root) {
            if (root == null)
                return 0;
            int leftDepth = maxDepth(root.left);
            int rightDepth = maxDepth(root.right);
            return Math.max(leftDepth, rightDepth) + 1;
        }
    }
}