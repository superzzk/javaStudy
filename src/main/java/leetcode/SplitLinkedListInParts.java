package leetcode;

/**
 * Given a (singly) linked list with head node root,
 * write a function to split the linked list into k consecutive linked list "parts".
 *
 * The length of each part should be as equal as possible:
 * no two parts should have a size differing by more than 1. This may lead to some parts being null.
 *
 * The parts should be in order of occurrence in the input list,
 * and parts occurring earlier should always have a size greater than or equal parts occurring later.
 *
 * Return a List of ListNode's representing the linked list parts that are formed.
 * Examples 1->2->3->4, k = 5 // 5 equal parts [ [1], [2], [3], [4], null ]
 *
 * Example 1:
 *
 * Input:
 * root = [1, 2, 3], k = 5
 * Output: [[1],[2],[3],[],[]]
 * Explanation:
 * The input and each element of the output are ListNodes, not arrays.
 * For example, the input root has root.val = 1, root.next.val = 2, \root.next.next.val = 3,
 * and root.next.next.next = null.
 * The first element output[0] has output[0].val = 1, output[0].next = null.
 * The last element output[4] is null, but it's string representation as a ListNode is [].
 *
 * Example 2:
 *
 * Input:
 * root = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], k = 3
 * Output: [[1, 2, 3, 4], [5, 6, 7], [8, 9, 10]]
 * Explanation:
 * The input has been split into consecutive parts with size difference at most 1,
 * and earlier parts are a larger size than the later parts.
 *
 * Note:
 * The length of root will be in the range [0, 1000].
 * Each value of a node in the input will be an integer in the range [0, 999].
 * k will be an integer in the range [1, 50].
 **/
public class SplitLinkedListInParts {

    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
    public static void printListNode(ListNode head){
        while (head != null) {
            System.out.print(head.val+",");
            head = head.next;
        }
        System.out.println();
    }
    private ListNode arrayToListNode(int[] nums){
        ListNode head = null;
        ListNode tail = head;
        for (int i : nums) {
            ListNode n = new ListNode(i);
            if(head==null) {
                head = n;
                tail = head;
                continue;
            }
            tail.next = n;
            tail = n;
        }
        return head;
    }
    public static int getListLength(ListNode head){
        if(head == null)
            return 0;
        //calculate linklist length
        int len = 1;
        ListNode temp=head;
        while(temp.next!=null){
            temp=temp.next;
            len++;
        }
        return len;
    }

    SplitLinkedListInParts.Solution s = new SplitLinkedListInParts.Solution();

    public static void main(String[] args) {
        SplitLinkedListInParts ooo = new SplitLinkedListInParts();

        int[] nums = {1,2,3,4,5,6,7,8,9};
        ListNode head = ooo.arrayToListNode(nums);
        ooo.printListNode(head);

        ListNode[] rt = ooo.s.splitListToParts(head,5);
        for(ListNode n : rt){
            printListNode(n);
        }
//        rt = new ListNode[0];
//        System.out.println(rt);
    }

    class Solution {
        public ListNode[] splitListToParts(ListNode root, int k) {

            ListNode[] result = new ListNode[k];
            int index = 0;

            int len = getListLength(root);

            ListNode pos = root;
            while(k>0){
                if(pos==null) {
                    result[index++] = null;
                    k--;
                    continue;
                }

                System.out.println("current list length:" + len);
                int partLen = len % k == 0 ? len / k : len / k + 1;
                len -= partLen;

                ListNode partHead = new ListNode(pos.val);
                pos=pos.next;
                ListNode partTemp = partHead;

                for(int j=0;j<partLen-1;j++){
                    ListNode node = new ListNode(pos.val);
                    partTemp.next = node;
                    partTemp=partTemp.next;
                    pos = pos.next;
                }
                result[index++] = partHead;

                printListNode(partHead);
                k--;
            }

            return result;
        }
    }
}
