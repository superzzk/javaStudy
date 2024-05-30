//给你链表的头结点 head ，请将其按 升序 排列并返回 排序后的链表 。 
//
// 
// 
//
// 
//
// 示例 1： 
// 
// 
//输入：head = [4,2,1,3]
//输出：[1,2,3,4]
// 
//
// 示例 2： 
// 
// 
//输入：head = [-1,5,3,4,0]
//输出：[-1,0,3,4,5]
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
// 链表中节点的数目在范围 [0, 5 * 10⁴] 内 
// -10⁵ <= Node.val <= 10⁵ 
// 
//
// 
//
// 进阶：你可以在 O(n log n) 时间复杂度和常数级空间复杂度下，对链表进行排序吗？ 
//
// Related Topics 链表 双指针 分治 排序 归并排序 👍 2310 👎 0

import com.zzk.study.leetcode.leetcode.editor.en.ListNode;

import java.util.List;

class Q148 {

//leetcode submit region begin(Prohibit modification and deletion)

    class Solution {
        public ListNode sortList(ListNode head) {
            if(head == null)
                return null;
            if(head.next == null)
                return head;
            if(head.next.next == null){
                if(head.val > head.next.val){
                    ListNode temp = head.next;
                    temp.next = head;
                    head.next = null;
                    return temp;
                }
                return head;
            }

            ListNode mid = mid(head);
            ListNode rightHead = mid.next;
            mid.next = null;
            ListNode left = sortList(head);
            ListNode right = sortList(rightHead);
            return merge(left, right);
        }

        private ListNode merge(ListNode left, ListNode right){
            ListNode dummy = new ListNode();
            ListNode head = dummy;
            while (left != null || right != null) {
                if(left==null){
                    dummy.next = right;
                    break;
                }
                if (right == null) {
                    dummy.next = left;
                    break;
                }
                if(left.val <= right.val){
                    dummy.next = left;
                    left = left.next;
                    dummy = dummy.next;
                }else {
                    dummy.next = right;
                    right = right.next;
                    dummy = dummy.next;
                }
            }
            return head.next;
        }

        private int len(ListNode head) {
            int res = 0;
            while (head != null) {
                res++;
                head = head.next;
            }
            return res;
        }

        private ListNode mid(ListNode head){
            ListNode fast = head;
            while (fast != null && fast.next != null) {
                head = head.next;
                fast = fast.next.next;
            }
            return head;
        }
    }

    //leetcode submit region end(Prohibit modification and deletion)
    // 冒泡，超时
    class Solution1 {
        public ListNode sortList(ListNode head) {
            if (head == null)
                return null;

            int tail = len(head);

            while (tail > 0) {
                ListNode node = head;
                ListNode pre = null;
                ListNode prepre = null;
                while (node != null) {
                    if (pre != null && pre.val > node.val) {
                        swap(prepre, pre, node);
                        if (prepre == null)
                            head = node;
                    }
                    prepre = pre;
                    pre = node;
                    node = node.next;
                }
                tail--;
            }
            return head;
        }

        private void swap(ListNode prepre, ListNode pre, ListNode node) {
            if (prepre != null)
                prepre.next = node;
            pre.next = node.next;
            node.next = pre;
        }

        private int len(ListNode head) {
            int res = 0;
            while (head != null) {
                res++;
                head = head.next;
            }
            return res;
        }
    }
}