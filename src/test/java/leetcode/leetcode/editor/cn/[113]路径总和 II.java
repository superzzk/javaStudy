//给定一个二叉树和一个目标和，找到所有从根节点到叶子节点路径总和等于给定目标和的路径。 
//
// 说明: 叶子节点是指没有子节点的节点。 
//
// 示例: 
//给定如下二叉树，以及目标和 sum = 22， 
//
//               5
//             / \
//            4   8
//           /   / \
//          11  13  4
//         /  \    / \
//        7    2  5   1
// 
//
// 返回: 
//
// [
//   [5,4,11,2],
//   [5,8,4,5]
//]
// 
// Related Topics 树 深度优先搜索


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
class Solution113 {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> result = new ArrayList();
        recur(root, sum, result);
        return result;
    }
    private void recur(TreeNode node, int sum , List<List<Integer>> result){
        List<Integer> intermediate = new ArrayList();
        recur_helper(node, sum, result, intermediate);
    }
    private void recur_helper(TreeNode node, int sum , List<List<Integer>> result, List<Integer> intermediate){
        if(node==null)
            return;
        if(node.val == sum && node.left == null && node.right == null) {
            List<Integer> t = new ArrayList(intermediate);
            t.add(node.val);
            result.add(t);
        }
        intermediate.add(node.val);
        recur_helper(node.left, sum - node.val, result, intermediate);
        recur_helper(node.right, sum - node.val, result, intermediate);
        intermediate.remove(intermediate.size() - 1);
    }


}
//leetcode submit region end(Prohibit modification and deletion)
