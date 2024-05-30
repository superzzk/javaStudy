//给定一个单链表 L 的头节点 head ，单链表 L 表示为： 
//
// 
//L0 → L1 → … → Ln - 1 → Ln
// 
//
// 请将其重新排列后变为： 
//
// 
//L0 → Ln → L1 → Ln - 1 → L2 → Ln - 2 → … 
//
// 不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。 
//
// 
//
// 示例 1： 
//
// 
//
// 
//输入：head = [1,2,3,4]
//输出：[1,4,2,3] 
//
// 示例 2： 
//
// 
//
// 
//输入：head = [1,2,3,4,5]
//输出：[1,5,2,4,3] 
//
// 
//
// 提示： 
//
// 
// 链表的长度范围为 [1, 5 * 10⁴] 
// 1 <= node.val <= 1000 
// 
//
// Related Topics 栈 递归 链表 双指针 👍 1471 👎 0

import java.util.Stack;

class ReorderChain {

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

//leetcode submit region begin(Prohibit modification and deletion)

    class Solution {
        public void reorderList(ListNode head) {
            Stack<ListNode> stack = new Stack<>();
            ListNode node = head;
            while (node != null) {
                stack.push(node);
                node = node.next;
            }
            int count = stack.size() / 2;

            node = head;
            ListNode next = node.next;
            while (count-- > 0) {
                next = node.next;
                ListNode pop = stack.pop();

                node.next = pop;
                pop.next = next;
                node = next;
            }

            node.next = null;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

    class Solution1 {
        public void reorderList(ListNode head) {
            if (head == null)
                return;
            if (head.next == null)
                return;

            ListNode tail = tail(head);

            tail.next = head.next;
            head.next = tail;

            reorderList(tail.next);
        }

        ListNode tail(ListNode head) {
            if (head == null) return null;
            ListNode temp = head;
            while (head.next != null) {
                temp = head;
                head = head.next;
            }
            temp.next = null;
            return head;
        }
    }
}