package com.zzk.study.leetcode.leetcode.editor.cn;//给定一个二叉树，返回它的 后序 遍历。
//
// 示例: 
//
// 输入: [1,null,2,3]  
//   1
//    \
//     2
//    /
//   3 
//
//输出: [3,2,1] 
//
// 进阶: 递归算法很简单，你可以通过迭代算法完成吗？ 
// Related Topics 栈 树


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.ArrayList;
import java.util.List;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution145 {

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }


    public List<Integer> postorderTraversal(TreeNode root) {
        return recur_help(root);
    }

    private List<Integer> recur_help(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        recur(root, result);
        return result;
    }

    private void recur(TreeNode node, List<Integer> result){
        if(node==null)
            return;
        recur(node.left,result);
        recur(node.right,result);
        result.add(node.val);
    }
}
//leetcode submit region end(Prohibit modification and deletion)

