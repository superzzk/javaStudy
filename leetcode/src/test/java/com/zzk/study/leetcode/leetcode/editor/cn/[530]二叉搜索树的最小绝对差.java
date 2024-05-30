//给你一个二叉搜索树的根节点 root ，返回 树中任意两不同节点值之间的最小差值 。 
//
// 差值是一个正数，其数值等于两值之差的绝对值。 
//
// 
//
// 示例 1： 
// 
// 
//输入：root = [4,2,6,1,3]
//输出：1
// 
//
// 示例 2： 
// 
// 
//输入：root = [1,0,48,null,null,12,49]
//输出：1
// 
//
// 
//
// 提示： 
//
// 
// 树中节点的数目范围是 [2, 10⁴] 
// 0 <= Node.val <= 10⁵ 
// 
//
// 
//
// 注意：本题与 783 https://leetcode-cn.com/problems/minimum-distance-between-bst-
//nodes/ 相同 
//
// Related Topics 树 深度优先搜索 广度优先搜索 二叉搜索树 二叉树 👍 559 👎 0


import com.zzk.study.leetcode.explore.basic.algorithm.Tree;
import com.zzk.study.leetcode.leetcode.editor.cn.TreeNode;

import java.util.Stack;

class Q530 {
//leetcode submit region begin(Prohibit modification and deletion)

    class Solution {
        Integer pre = null;
        int min = Integer.MAX_VALUE;

        public int getMinimumDifference(TreeNode root) {
            if(root == null)
                return min;

            dfs(root);
            return min;
        }

        private void dfs(TreeNode node) {
            if(node ==null)
                return;
            dfs(node.left);
            if(pre != null)
                min = Math.min(min, Math.abs(pre - node.val));

            pre = node.val;
            dfs(node.right);
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

    class SolutionIter {
        public int getMinimumDifference(TreeNode root) {
            Stack<TreeNode> stack = new Stack<>();
            Integer pre = null;
            int min = Integer.MAX_VALUE;
            while (!stack.isEmpty() || root != null) {
                while (root != null) {
                    stack.push(root);
                    root = root.left;
                }

                root = stack.pop();
                if (pre != null)
                    min = Math.min(min, Math.abs(pre - root.val));

                pre = root.val;
                root = root.right;
            }

            return min;
        }
    }
}