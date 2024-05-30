//给定一棵二叉树的根节点 root ，请找出该二叉树中每一层的最大值。 
//
// 
//
// 示例1： 
//
// 
//
// 
//输入: root = [1,3,2,5,3,null,9]
//输出: [1,3,9]
// 
//
// 示例2： 
//
// 
//输入: root = [1,2,3]
//输出: [1,3]
// 
//
// 
//
// 提示： 
//
// 
// 二叉树的节点个数的范围是 [0,10⁴] 
// 
// -2³¹ <= Node.val <= 2³¹ - 1 
// 
//
// 
//
// Related Topics 树 深度优先搜索 广度优先搜索 二叉树 👍 363 👎 0

import com.zzk.study.leetcode.leetcode.editor.cn.TreeNode;

import java.util.*;
import java.util.stream.IntStream;

class Q515 {
//leetcode submit region begin(Prohibit modification and deletion)

    class Solution {
        public List<Integer> largestValues(TreeNode root) {
            List<Integer> res = new ArrayList<>();
            if(root==null)
                return new ArrayList<>();

            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);

            while (!queue.isEmpty()) {
                int max = Integer.MIN_VALUE;
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    TreeNode node = queue.poll();
                    max = Math.max(max, node.val);
                    if(node.left!=null)
                        queue.offer(node.left);
                    if(node.right!=null)
                        queue.offer(node.right);
                }
                res.add(max);
            }

            return res;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)
}