//给你单链表的头节点 head ，请你反转链表，并返回反转后的链表。
// 
// 
// 
//
// 示例 1： 
//
// 
//输入：head = [1,2,3,4,5]
//输出：[5,4,3,2,1]
// 
//
// 示例 2： 
//
// 
//输入：head = [1,2]
//输出：[2,1]
// 
//
// 示例 3： 
//
// 
//输入：head = []
//输出：[]
// 
//
// 
//
// 提示： 
//
// 
// 链表中节点的数目范围是 [0, 5000] 
// -5000 <= Node.val <= 5000 
// 
//
// 
//
// 进阶：链表可以选用迭代或递归方式完成反转。你能否用两种方法解决这道题？ 
// 
// 
// Related Topics 递归 链表 👍 2834 👎 0

import com.zzk.study.leetcode.leetcode.editor.en.ListNode;

class ReverseChain {
    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public ListNode reverseList(ListNode head) {
            if (head == null || head.next == null)
                return head;
            ListNode nxt = head.next;
            ListNode tail = reverseList(head.next);
            nxt.next = head;
            head.next = null;
            return tail;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

    // 递归，不好，下面的递归更好
    class Solution1 {
        private ListNode tail;

        public ListNode reverseList(ListNode head) {
            recurse(head);
            return tail;
        }

        private ListNode recurse(ListNode node) {
            if (node == null)
                return null;
            if (node.next == null) {
                tail = node;
                return node;
            }
            ListNode next = recurse(node.next);
            next.next = node;
            node.next = null;
            return node;
        }
    }

    // 双指针
    class Solution2 {
        public ListNode reverseList(ListNode head) {
            ListNode cur = head;
            ListNode pre = null;
            while (cur != null) {
                ListNode next = cur.next;
                cur.next = pre;

                pre = cur;
                cur = next;
            }
            return pre;
        }
    }

    // 递归
    class Solution3 {
        public ListNode reverseList(ListNode head) {
            return recur(head, null);    // 调用递归并返回
        }

        private ListNode recur(ListNode cur, ListNode pre) {
            if (cur == null) return pre; // 终止条件
            ListNode res = recur(cur.next, cur);  // 递归后继节点
            cur.next = pre;              // 修改节点引用指向
            return res;                  // 返回反转链表的头节点
        }
    }

    // 递归
    class Solution4 {
        public ListNode reverseList(ListNode head) {
            if (head == null || head.next == null)
                return head;
            ListNode nxt = head.next;
            ListNode tail = reverseList(head.next);
            nxt.next = head;
            head.next = null;
            return tail;
        }
    }
}