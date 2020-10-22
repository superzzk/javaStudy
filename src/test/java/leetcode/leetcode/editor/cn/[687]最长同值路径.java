//给定一个二叉树，找到最长的路径，这个路径中的每个节点具有相同值。 这条路径可以经过也可以不经过根节点。 
//
// 注意：两个节点之间的路径长度由它们之间的边数表示。 
//
// 示例 1: 
//
// 输入: 
//
// 
//              5
//             / \
//            4   5
//           / \   \
//          1   1   5
// 
//
// 输出: 
//
// 
//2
// 
//
// 示例 2: 
//
// 输入: 
//
// 
//              1
//             / \
//            4   5
//           / \   \
//          4   4   5
// 
//
// 输出: 
//
// 
//2
// 
//
// 注意: 给定的二叉树不超过10000个结点。 树的高度不超过1000。 
// Related Topics 树 递归

package leetcode.leetcode.editor.cn;

import leetcode.leetcode.editor.cn.util.TreeUtils;
import org.junit.Assert;

class P687LongestUnivaluePath {
	public static void main(String[] args) {
        Solution solution = new P687LongestUnivaluePath().new Solution();
        TreeNode root = TreeUtils.arrayToBTree(new Integer[]{5, 4, 5, 1, 1, 5});
		Assert.assertEquals(2,solution.longestUnivaluePath(root));
		Assert.assertEquals(2,solution.longestUnivaluePath(
				TreeUtils.arrayToBTree(new Integer[]{1, 4, 5, 4, 4, 5})
		));
		Assert.assertEquals(4,solution.longestUnivaluePath(
				TreeUtils.arrayToBTree(new Integer[]{1, null,1,1,1,1,1,1})
		));
    }
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
	    int max = 0;
		public int longestUnivaluePath(TreeNode root) {
			if (root == null) return 0;
			recur(root);
			return max-1;
		}

		public void recur(TreeNode node) {
			if (node != null) {
				int t = countSameValNumber(node, node.val);
				max = Math.max(max, t);
				recur(node.left);
				recur(node.right);
			}
		}

		public int countSameValNumber(TreeNode node, int val) {
			if (node == null)
				return 0;
			if(node.val!=val)
			    return 0;

			int left = countSameValNumber(node.left, node.val);
			int right = countSameValNumber(node.right, node.val);

			int count = Math.max(left, right) + 1;
            max = Math.max(max, count);
			return count;
		}
	}
//leetcode submit region end(Prohibit modification and deletion)
}