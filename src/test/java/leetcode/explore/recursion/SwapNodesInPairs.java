package leetcode.explore.recursion;

/**
 * Given a linked list, swap every two adjacent nodes and return its head.
 *
 * You may not modify the values in the list's nodes, only nodes itself may be changed.
 *
 * Example:
 *
 * Given 1->2->3->4, you should return the list as 2->1->4->3.
 **/
public class SwapNodesInPairs {

    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public ListNode swapPairs(ListNode head) {
        if(head==null || head.next==null)
            return head;
        ListNode newHead = head.next;
        helper(head,null);
        return newHead;
    }

    private void helper(ListNode node, ListNode prev){
        if(node==null || node.next==null)
            return;
        ListNode nextNode = node.next.next;
        swap(node,prev);
        prev = node;
        helper(nextNode,prev);
    }

    private void swap(ListNode node, ListNode prev){
        ListNode a = node;
        ListNode b = node.next;
        ListNode tail = b.next;
        if(prev!=null)
            prev.next = b;
        b.next = a;
        a.next = tail;
    }
}
