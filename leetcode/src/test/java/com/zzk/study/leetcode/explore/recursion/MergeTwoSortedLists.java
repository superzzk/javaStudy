package com.zzk.study.leetcode.explore.recursion;

/**
 * Merge two sorted linked lists and return it as a new list.
 * The new list should be made by splicing together the nodes of the first two lists.
 * <p>
 * Example:
 * <p>
 * Input: 1->2->4, 1->3->4
 * Output: 1->1->2->3->4->4
 **/
public class MergeTwoSortedLists {
    private ListNode resultHead;
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        helper(l1,l2,null);
        return resultHead;
    }

    private void helper(ListNode l1, ListNode l2, ListNode result){
        if (l1==null) {
            helper2(l2, result);
            return;
        }
        if (l2==null) {
            helper2(l1, result);
            return;
        }

        ListNode n;
        if (l1.val < l2.val){
            n=addTailNode(l1, result);
            l1=l1.next;
        }else{
            n=addTailNode(l2, result);
            l2=l2.next;
        }
        helper(l1,l2,n);
    }
    //将l全部添加到result
    private void helper2(ListNode l, ListNode result){
        while(l!=null){
            ListNode node = new ListNode(l.val);
            result.next = node;
            l = l.next;
            result = result.next;
        }
    }

    private ListNode addTailNode(ListNode node, ListNode result){
        if(result==null){
            result = new ListNode(node.val);
            resultHead = result;
            return result;
        }else{
            ListNode newNode = new ListNode(node.val);
            result.next = newNode;
            return newNode;
        }
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
