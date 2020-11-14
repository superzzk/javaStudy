package leetcode.leetcode.editor.cn;
//给定一个二叉搜索树的根节点 root，返回树中任意两节点的差的最小值。 
//
// 
//
// 示例： 
//
// 输入: root = [4,2,6,1,3,null,null]
//输出: 1
//解释:
//注意，root是树节点对象(TreeNode object)，而不是数组。
//
//给定的树 [4,2,6,1,3,null,null] 可表示为下图:
//
//          4
//        /   \
//      2      6
//     / \    
//    1   3  
//
//最小的差值是 1, 它是节点1和节点2的差值, 也是节点3和节点2的差值。 
//
// 
//
// 注意： 
//
// 
// 二叉树的大小范围在 2 到 100。 
// 二叉树总是有效的，每个节点的值都是整数，且不重复。 
// 本题与 530：https://leetcode-cn.com/problems/minimum-absolute-difference-in-bst/ 
//相同 
// 
// Related Topics 树 递归 
// 👍 93 👎 0

import leetcode.leetcode.editor.cn.util.TreeUtils;

class MinimumDistanceBetweenBstNodes{
    public static void main(String[] args) {
        Solution solution = new MinimumDistanceBetweenBstNodes().new Solution();
        TreeNode root = TreeUtils.arrayToBTree(new Integer[]{4, 2, 6, 1, 3, null, null});
        int i = solution.minDiffInBST(root);
        System.out.println(i);
    }

//leetcode submit region begin(Prohibit modification and deletion)
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    Integer pre, ans;
    public int minDiffInBST(TreeNode root) {
        ans = Integer.MAX_VALUE;
        dfs(root);
        return ans;
    }

    private void dfs(TreeNode node){
        if(node==null) return;
        dfs(node.left);
        if (pre != null) {
            ans = Math.min(ans, Math.abs(node.val - pre));
        }
        pre = node.val;
        dfs(node.right);
    }
}
//leetcode submit region end(Prohibit modification and deletion)

}