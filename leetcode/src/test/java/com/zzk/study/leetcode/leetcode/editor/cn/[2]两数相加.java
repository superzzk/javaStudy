//给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。 
//
// 请你将两个数相加，并以相同形式返回一个表示和的链表。 
//
// 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。 
//
// 
//
// 示例 1： 
// 
// 
//输入：l1 = [2,4,3], l2 = [5,6,4]
//输出：[7,0,8]
//解释：342 + 465 = 807.
// 
//
// 示例 2： 
//
// 
//输入：l1 = [0], l2 = [0]
//输出：[0]
// 
//
// 示例 3： 
//
// 
//输入：l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
//输出：[8,9,9,9,0,0,0,1]
// 
//
// 
//
// 提示： 
//
// 
// 每个链表中的节点数在范围 [1, 100] 内 
// 0 <= Node.val <= 9 
// 题目数据保证列表表示的数字不含前导零 
// 
//
// Related Topics 递归 链表 数学 👍 10569 👎 0

class Q2 {

    class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
//leetcode submit region begin(Prohibit modification and deletion)

    /**
     * Definition for singly-linked list.
     * public class ListNode {
     * int val;
     * ListNode next;
     * ListNode() {}
     * ListNode(int val) { this.val = val; }
     * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     * }
     */
    class Solution {
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            return recur(l1, l2, false);
        }

        private ListNode recur(ListNode l1, ListNode l2, boolean carry) {
            int sum = 0;
            ListNode res = null;
            if(l1 == null && l2 == null) {
                return carry ? new ListNode(1) : null;
            }
            if(l1 == null) {
                sum = l2.val;
            } else if (l2 == null) {
                sum = l1.val;
            } else {
                sum = l1.val + l2.val;
            }

            if(carry) sum++;
            carry = false;

            if (sum > 9) {
                carry = true;
                sum = sum % 10;
            }
            ListNode node = new ListNode(sum);
            node.next = recur(l1 == null ? null : l1.next,
                    l2 == null ? null : l2.next, carry);
            return node;
        }
    }

    //leetcode submit region end(Prohibit modification and deletion)
    class Solution1 {
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            ListNode head = null;
            ListNode tail = null;

            boolean carry = false;
            while (l1 != null || l2 != null) {
                int sum = 0;
                if (l1 == null) {
                    sum = l2.val;
                    l2 = l2.next;
                } else if (l2 == null) {
                    sum = l1.val;
                    l1 = l1.next;
                } else {
                    sum = l1.val + l2.val;
                    l1 = l1.next;
                    l2 = l2.next;
                }

                if (carry)
                    sum++;

                if (sum > 9) {
                    carry = true;
                    sum = sum % 10;
                } else {
                    carry = false;
                }

                if (tail == null) {
                    tail = new ListNode(sum);
                    head = tail;
                } else {
                    ListNode node = new ListNode(sum);
                    tail.next = node;
                    tail = node;
                }
            }
            if (carry) {
                ListNode node = new ListNode(1);
                tail.next = node;
                tail = node;
            }

            return head;
        }
    }

    class Solution2 {
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            return recur(l1, l2, false);
        }

        private ListNode recur(ListNode l1, ListNode l2, boolean carry) {
            int sum = 0;
            ListNode res = null;
            if(l1 == null && l2 == null) {
                return carry ? new ListNode(1) : null;
            }
            if(l1 == null) {
                sum = l2.val;
            } else if (l2 == null) {
                sum = l1.val;
            } else {
                sum = l1.val + l2.val;
            }

            if(carry) sum++;
            carry = false;

            if (sum > 9) {
                carry = true;
                sum = sum % 10;
            }
            ListNode node = new ListNode(sum);
            node.next = recur(l1 == null ? null : l1.next,
                    l2 == null ? null : l2.next, carry);
            return node;
        }
    }
}