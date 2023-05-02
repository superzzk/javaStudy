package com.zzk.study.leetcode.leetcode.editor.cn;//给你一个链表数组，每个链表都已经按升序排列。
//
// 请你将所有链表合并到一个升序链表中，返回合并后的链表。 
//
// 
//
// 示例 1： 
//
// 输入：lists = [[1,4,5],[1,3,4],[2,6]]
//输出：[1,1,2,3,4,4,5,6]
//解释：链表数组如下：
//[
//  1->4->5,
//  1->3->4,
//  2->6
//]
//将它们合并到一个有序链表中得到。
//1->1->2->3->4->4->5->6
// 
//
// 示例 2： 
//
// 输入：lists = []
//输出：[]
// 
//
// 示例 3： 
//
// 输入：lists = [[]]
//输出：[]
// 
//
// 
//
// 提示： 
//
// 
// k == lists.length 
// 0 <= k <= 10^4 
// 0 <= lists[i].length <= 500 
// -10^4 <= lists[i][j] <= 10^4 
// lists[i] 按 升序 排列 
// lists[i].length 的总和不超过 10^4 
// 
//
// Related Topics 链表 分治 堆（优先队列） 归并排序 👍 2390 👎 0



import com.zzk.study.leetcode.leetcode.editor.en.ListNode;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Q23 {
    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public ListNode mergeKLists(ListNode[] lists) {
            if (lists.length == 0) {
                return null;
            }
            return recurMerge(lists, 0, lists.length-1);
        }

        public ListNode recurMerge(ListNode[] lists, int start, int end) {
            if (start == end) {
                return lists[start];
            }
            int mid = start + (end-start)/2;
            final ListNode l1 = recurMerge(lists, start, mid);
            final ListNode l2 = recurMerge(lists, mid+1, end);
            return merge2List(l1, l2);
        }

        private ListNode merge2List(ListNode node1, ListNode node2) {
            ListNode head = new ListNode();
            ListNode tail = head;
            while (node1 != null || node2 != null) {
                if (node1 == null) {
                    tail.next = node2;
                    break;
                }
                if (node2 == null) {
                    tail.next = node1;
                    break;
                }
                if (node1.val <= node2.val) {
                    tail.next = node1;
                    tail = node1;
                    node1 = node1.next;
                } else {
                    tail.next = node2;
                    tail = node2;
                    node2 = node2.next;
                }
            }
            return head.next;
        }
    }

    //leetcode submit region end(Prohibit modification and deletion)
}
