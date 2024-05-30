//给定一个单链表的头节点 head ，其中的元素 按升序排序 ，将其转换为 平衡 二叉搜索树。 
//
// 
//
// 示例 1: 
//
// 
//
// 
//输入: head = [-10,-3,0,5,9]
//输出: [0,-3,9,-10,null,5]
//解释: 一个可能的答案是[0，-3,9，-10,null,5]，它表示所示的高度平衡的二叉搜索树。
// 
//
// 示例 2: 
//
// 
//输入: head = []
//输出: []
// 
//
// 
//
// 提示: 
//
// 
// head 中的节点数在[0, 2 * 10⁴] 范围内 
// -10⁵ <= Node.val <= 10⁵ 
// 
//
// Related Topics 树 二叉搜索树 链表 分治 二叉树 👍 898 👎 0

import com.zzk.study.leetcode.leetcode.editor.cn.TreeNode;
import com.zzk.study.leetcode.leetcode.editor.en.ListNode;

import java.util.ArrayList;
import java.util.List;
class Q109 {
    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public TreeNode sortedListToBST(ListNode head) {
            List<Integer> nums = new ArrayList<>();
            while (head != null) {
                nums.add(head.val);
                head = head.next;
            }

            return buildTree(nums, 0, nums.size());
        }

        private TreeNode buildTree(List<Integer> nums, int start, int end) {
            if (start == end)
                return null;

            int rootPos = start + (end - start) / 2;
            TreeNode root = new TreeNode(nums.get(rootPos));

            root.left = buildTree(nums, start, rootPos);
            root.right = buildTree(nums, rootPos + 1, end);

            return root;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)
}