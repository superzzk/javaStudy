package com.zzk.study.leetcode.leetcode.editor.cn;
//给定二叉搜索树的根结点 root，返回 L 和 R（含）之间的所有结点的值的和。 
//
// 二叉搜索树保证具有唯一的值。 
//
// 
//
// 示例 1： 
//
// 输入：root = [10,5,15,3,7,null,18], L = 7, R = 15
//输出：32
//       10
//    5      15
//  3   7       18
//
// 示例 2： 
//
// 输入：root = [10,5,15,3,7,13,18,1,null,6], L = 6, R = 10
//输出：23
//            10
//        5         15
//     3     7   13     18
//   1     6
//
// 
//
// 提示： 
//
// 
// 树中的结点数量最多为 10000 个。 
// 最终的答案保证小于 2^31。 
// 
// Related Topics 树 递归 
// 👍 140 👎 0

import com.zzk.study.leetcode.leetcode.editor.cn.util.TreeUtils;
import org.junit.Assert;

class RangeSumOfBst {
    public static void main(String[] args) {
        Solution solution = new RangeSumOfBst().new Solution();
        TreeNode root = TreeUtils.arrayToBTree(new Integer[]{10, 5, 15, 3, 7, null, 18});
        Assert.assertEquals(32, solution.rangeSumBST(root,7,15));

        root = TreeUtils.arrayToBTree(new Integer[]{10,5,15,3,7,13,18,1,null,6});
        Assert.assertEquals(23, solution.rangeSumBST(root,6,10));
    }

//leetcode submit region begin(Prohibit modification and deletion)

    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     * int val;
     * TreeNode left;
     * TreeNode right;
     * TreeNode(int x) { val = x; }
     * }
     */
    class Solution {
        boolean flag;
        int left, right;
        int sum;
        public int rangeSumBST(TreeNode root, int L, int R) {
            left = L;
            right = R;
            flag = false;
            sum = 0;
            bst(root);
            return sum;
        }

        private void bst(TreeNode node) {
            if(node==null) return;
            bst(node.left);
            if(node.val == left)
                flag=true;
            if(flag){
                sum += node.val;
            }
            if(node.val==right)
                return;
            bst(node.right);
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}