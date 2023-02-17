package com.zzk.study.leetcode.leetcode.editor.en;//You are given two non-empty linked lists representing two non-negative
//integers. The digits are stored in reverse order, and each of their nodes contains a 
//single digit. Add the two numbers and return the sum as a linked list. 
//
// You may assume the two numbers do not contain any leading zero, except the 
//number 0 itself.
//
// Example 1: 
//
//Input: l1 = [2,4,3], l2 = [5,6,4]
//Output: [7,0,8]
//Explanation: 342 + 465 = 807.
//
// Example 2: 
//
//Input: l1 = [0], l2 = [0]
//Output: [0]
//
// Example 3: 
//
//Input: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
//Output: [8,9,9,9,0,0,0,1]
//
// Constraints: 
//
// The number of nodes in each linked list is in the range [1, 100]. 
// 0 <= Node.val <= 9 
// It is guaranteed that the list represents a number that does not have leading zeros.
// 
// Related Topics Linked List Math Recursion 👍 22558 👎 4372

import com.zzk.study.leetcode.leetcode.editor.en.ListNode;
import lombok.val;

class AddTowNumbers {

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
            ListNode node = null;
            ListNode head = null;
            boolean jinwei = false;
            while (l1 != null && l2 != null) {
                int sum = l1.val + l2.val;
                if (jinwei)
                    sum++;
                jinwei = sum >= 10;
                final int digit = sum % 10;

                ListNode tail = new ListNode(digit);
                if (head == null) {
                    head = tail;
                    node = head;
                }

                node.next = tail;
                node = tail;

                l1 = l1.next;
                l2 = l2.next;
            }
            if (jinwei) {
                node.next = new ListNode(1);
            }
            while (l1 != null) {
                node.next = new ListNode(l1.val);
                node = node.next;
            }
            while (l2 != null) {
                node.next = new ListNode(l2.val);
                node = node.next;
            }
            return head;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}