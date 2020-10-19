package leetcode.array;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 347. 前 K 个高频元素
 * 给定一个非空的整数数组，返回其中出现频率前 k 高的元素。
 *
 * 示例 1:
 *
 * 输入: nums = [1,1,1,2,2,3], k = 2
 * 输出: [1,2]
 *
 * 示例 2:
 *
 * 输入: nums = [1], k = 1
 * 输出: [1]
 *
 * 说明：
 *     你可以假设给定的 k 总是合理的，且 1 ≤ k ≤ 数组中不相同的元素的个数。
 *     你的算法的时间复杂度必须优于 O(n log n) , n 是数组的大小。
 **/
public class Q347 {

    /**
     * [1,1,1,2,2,3]
     * 2
     */
    @Test
    public void test1(){
        int[] nums = {1,1,1,2,2,3};
        List<Integer> r = topKFrequent(nums,2);
        System.out.println(r);
    }

    public List<Integer> topKFrequent(int[] nums, int k) {
        Map<Integer, Item> map = new HashMap<>();
        LinkedList<Integer> result = new LinkedList<>();
        Queue<Item> queue = new PriorityQueue<>(k,(a,b)-> {return a.cnt-b.cnt;});

        for(int i =0; i< nums.length;i++){
            Item item = map.get(nums[i]);
            if(item==null){
                item = new Item(nums[i], 1);
                map.put(nums[i], item);
            }else
                item.cnt++;
        }
        for(Map.Entry<Integer,Item> entry : map.entrySet()){
            if(queue.size()<k)
                queue.offer(entry.getValue());
            else{
                if(queue.peek().cnt < entry.getValue().cnt){
                    queue.poll();
                    queue.offer(entry.getValue());
                }
            }
        }
        for(Item item : queue){
             result.addFirst(item.val);
        }
        return result;
    }
    class Item{
        public int val;
        public int cnt;
        public Item(int val,int cnt){
            this.val=val;this.cnt=cnt;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "val=" + val +
                    '}';
        }
    }


    /**
     * //基于桶排序求解「前 K 个高频元素」
     */
    public List<Integer> topKFrequent2(int[] nums, int k) {
        List<Integer> res = new ArrayList();
        // 使用字典，统计每个元素出现的次数，元素为键，元素出现的次数为值
        HashMap<Integer,Integer> map = new HashMap();
        for(int num : nums){
            if (map.containsKey(num)) {
                map.put(num, map.get(num) + 1);
            } else {
                map.put(num, 1);
            }
        }

        //桶排序
        //将频率作为数组下标，对于出现频率不同的数字集合，存入对应的数组下标
        List<Integer>[] list = new List[nums.length+1];
        for(int key : map.keySet()){
            // 获取出现的次数作为下标
            int i = map.get(key);
            if(list[i] == null){
                list[i] = new ArrayList();
            }
            list[i].add(key);
        }

        // 倒序遍历数组获取出现顺序从大到小的排列
        for(int i = list.length - 1;i >= 0 && res.size() < k;i--){
            if(list[i] == null) continue;
            res.addAll(list[i]);
        }
        return res;
    }
}
