package com.zzk.study.leetcode;

/**
 * 61
 * Given a linked list, rotate the list to the right by k places, where k is non-negative.
 *
 * Example 1:
 *
 * Input: 1->2->3->4->5->NULL, k = 2
 * Output: 4->5->1->2->3->NULL
 * Explanation:
 * rotate 1 steps to the right: 5->1->2->3->4->NULL
 * rotate 2 steps to the right: 4->5->1->2->3->NULL
 *
 * Example 2:
 *
 * Input: 0->1->2->NULL, k = 4
 * Output: 2->0->1->NULL
 * Explanation:
 * rotate 1 steps to the right: 2->0->1->NULL
 * rotate 2 steps to the right: 1->2->0->NULL
 * rotate 3 steps to the right: 0->1->2->NULL
 * rotate 4 steps to the right: 2->0->1->NULL
 **/
public class Q61_RotateList {
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
    private void printListNode(ListNode head){
        while (head != null) {
            System.out.print(head.val+",");
            head = head.next;
        }
        System.out.println();
    }

    Q61_RotateList.Solution s = new Q61_RotateList.Solution();
    public static void main(String[] args) {
        Q61_RotateList ooo = new Q61_RotateList();

        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = null;

        ooo.printListNode(node1);
        ListNode rt = ooo.s.rotateRight(node1, 2);
        ooo.printListNode(rt);
    }

    /*
    * 根据偏移量先算出最终的头和尾，在变更指向
    * */
    class Solution {
        public ListNode rotateRight(ListNode head, int k) {
            if(head==null)
                return null;
            if(k==0 || head.next==null)
                return head;
            //calculate linklist length
            int len = 1;
            ListNode temp=head;
            while(temp.next!=null){
                temp=temp.next;
                len++;
            }

            int offset = k%len;
            if(offset==0)
                return head;
            //temp当前指向tail
            temp.next = head;//链表成为环状

            //calculate offset
            int headPos = len - offset;

            ListNode headNode = null;
            ListNode tailNode = null;
            ListNode pre = null;
            ListNode node = head;
            for(int i=0;i<len;i++){
                if(i==headPos) {
                    headNode = node;
                    tailNode = pre;
                    break;
                }
                pre = node;
                node=node.next;
            }
            tailNode.next = null;

            return headNode;
        }
    }

    /**
    * 一次一次右移
    */
    class Solution2 {
        public ListNode rotateRight(ListNode head, int k) {
            if(head==null)
                return null;
            while(k-->0){
                head = rotateRight(head);
            }
            return head;
        }
        //向右循环一次
        private ListNode rotateRight(ListNode head){
            ListNode node = head;
            ListNode pre = head;
            while (node.next!=null){
                pre = node;
                node = node.next;
            }
            node.next = head;
            pre.next=null;
            return node;
        }
    }

    /**
    * Approach 1:
     *
     * Intuition
     *
     * The nodes in the list are already linked, and hence the rotation basically means
     *
     *     To close the linked list into the ring.     *
     *     To break the ring after the new tail and just in front of the new head.
     *
     * Algorithm
     *
     * The algorithm is quite straightforward :     *
     *     Find the old tail and connect it with the head old_tail.next = head to close the ring. Compute the length of the list n at the same time.     *
     *     Find the new tail, which is (n - k % n - 1)th node from the head and the new head, which is (n - k % n)th node.     *
     *     Break the ring new_tail.next = None and return new_head.
    */
    class Solution3 {
        public ListNode rotateRight(ListNode head, int k) {
            // base cases
            if (head == null) return null;
            if (head.next == null) return head;

            // close the linked list into the ring
            ListNode old_tail = head;
            int n;
            for(n = 1; old_tail.next != null; n++)
                old_tail = old_tail.next;
            old_tail.next = head;

            // find new tail : (n - k % n - 1)th node
            // and new head : (n - k % n)th node
            ListNode new_tail = head;
            for (int i = 0; i < n - k % n - 1; i++)
                new_tail = new_tail.next;
            ListNode new_head = new_tail.next;

            // break the ring
            new_tail.next = null;

            return new_head;
        }
    }
}
