package leetcode.array;

import org.junit.Assert;
import org.junit.Test;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 703. 数据流中的第K大元素
 * 设计一个找到数据流中第K大元素的类（class）。注意是排序后的第K大元素，不是第K个不同的元素。
 *
 * 你的 KthLargest 类需要一个同时接收整数 k 和整数数组nums 的构造器，它包含数据流中的初始元素。
 * 每次调用 KthLargest.add，返回当前数据流中第K大的元素。
 *
 * 示例:
 *
 * int k = 3;
 * int[] arr = [4,5,8,2];
 * KthLargest kthLargest = new KthLargest(3, arr);
 * kthLargest.add(3);   // returns 4
 * kthLargest.add(5);   // returns 5
 * kthLargest.add(10);  // returns 5
 * kthLargest.add(9);   // returns 8
 * kthLargest.add(4);   // returns 8
 *
 * 说明:
 * 你可以假设 nums 的长度≥ k-1 且k ≥ 1。
 **/
public class Q703 {
    /**
     * Your KthLargest object will be instantiated and called as such:
     * KthLargest obj = new KthLargest(k, nums);
     * int param_1 = obj.add(val);
     */
    class KthLargest {
        private Queue<Integer> queue;
        private boolean init;
        public KthLargest(int k, int[] nums) {
            queue = new PriorityQueue<Integer>(k);
            if(nums.length>=k) {
                init = true;
                for (int i = 0; i < k; i++) {
                    queue.offer(nums[i]);
                }
                for (int i = k; i < nums.length; i++) {
                    if (queue.peek() < nums[i]) {
                        queue.poll();
                        queue.offer(nums[i]);
                    }
                }
            }else{
                for (int i = 0; i < nums.length; i++) {
                    queue.offer(nums[i]);
                }
            }
        }

        public int add(int val) {
            if(init) {
                if (queue.peek() < val) {
                    queue.poll();
                    queue.offer(val);
                }
            }else{
                init = true;
                queue.offer(val);
            }
            return queue.peek();
        }
    }
    @Test
    public void  test1(){
        KthLargest obj = new KthLargest(3, new int[]{4,5,8,2});
        int param_1 = obj.add(3);
        Assert.assertTrue(param_1 == 4);
    }

    /**
     * ["KthLargest","add","add","add","add","add"]
     * [[3,[4,5,8,2]],[3],[5],[10],[9],[4]]
     */

}
