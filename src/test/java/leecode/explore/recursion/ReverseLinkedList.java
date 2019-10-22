package leecode.explore.recursion;

/**
 * Reverse a singly linked list.
 *
 * Example:
 *
 * Input: 1->2->3->4->5->NULL
 * Output: 5->4->3->2->1->NULL
 *
 * Follow up:
 *
 * A linked list can be reversed either iteratively or recursively. Could you implement both?
 **/
public class ReverseLinkedList {
    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
    private ListNode newHead;
    public ListNode reverseList(ListNode head) {
        reverseRecurse(head, null);
        return newHead;
    }

    private void reverseRecurse(ListNode node, ListNode prev){
        if(node==null) {
            newHead = prev;
            return;
        }
        reverseRecurse(node.next, node);
        node.next = prev;
    }
}
