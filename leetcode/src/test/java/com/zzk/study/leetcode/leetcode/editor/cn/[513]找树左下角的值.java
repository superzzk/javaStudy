//给定一个二叉树的 根节点 root，请找出该二叉树的 最底层 最左边 节点的值。 
//
// 假设二叉树中至少有一个节点。 
//
// 
//
// 示例 1: 
//
// 
//
// 
//输入: root = [2,1,3]
//输出: 1
// 
//
// 示例 2: 
//
// 
//
// 
//输入: [1,2,3,4,null,5,6,null,null,7]
//输出: 7
// 
//
// 
//
// 提示: 
//
// 
// 二叉树的节点个数的范围是 [1,10⁴] 
// 
// -2³¹ <= Node.val <= 2³¹ - 1 
// 
//
// Related Topics 树 深度优先搜索 广度优先搜索 二叉树 👍 575 👎 0

import com.zzk.study.leetcode.leetcode.editor.cn.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

class Q513 {
//leetcode submit region begin(Prohibit modification and deletion)

    class Solution {
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode res;
        int maxLevel;
        public int findBottomLeftValue(TreeNode root) {
            recur(root, 1);
            return res.val;
        }

        private void recur(TreeNode node, int level) {
            if(node == null)
                return;
            if (level > maxLevel) {
                maxLevel = level;
                res = node;
            }
            recur(node.left, level+1);
            recur(node.right, level+1);
        }
    }
//leetcode submit region end(Prohibit modification and deletion)
}