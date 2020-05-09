package leecode;

import org.apache.http.util.Asserts;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * 692. 前K个高频单词
 * 给一非空的单词列表，返回前 k 个出现次数最多的单词。
 *
 * 返回的答案应该按单词出现频率由高到低排序。如果不同的单词有相同出现频率，按字母顺序排序。
 *
 * 示例 1：
 *
 * 输入: ["i", "love", "leetcode", "i", "love", "coding"], k = 2
 * 输出: ["i", "love"]
 * 解析: "i" 和 "love" 为出现次数最多的两个单词，均为2次。
 *     注意，按字母顺序 "i" 在 "love" 之前。
 *
 *
 *
 * 示例 2：
 *
 * 输入: ["the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"], k = 4
 * 输出: ["the", "is", "sunny", "day"]
 * 解析: "the", "is", "sunny" 和 "day" 是出现次数最多的四个单词，
 *     出现次数依次为 4, 3, 2 和 1 次。
 *
 *
 *
 * 注意：
 *
 *     假定 k 总为有效值， 1 ≤ k ≤ 集合元素数。
 *     输入的单词均由小写字母组成。
 **/
public class Q692 {
    public List<String> topKFrequent(String[] words, int k) {
        Map<String,Entry> map = new HashMap<>();
        Queue<Entry> queue = new PriorityQueue<>(k,(a,b)-> a.count.equals(b.count) ? b.str.compareTo(a.str) : a.count-b.count);

        for(String s : words){
            if(map.containsKey(s))
                map.get(s).count++;
            else
                map.put(s, new Entry(s,1));
        }

        for(Map.Entry<String,Entry> item : map.entrySet()){
            if(queue.size()==k){
                Entry entry = item.getValue();
                if(queue.peek().count < entry.count){
                    queue.poll();
                    queue.offer(entry);
                }
                else if(queue.peek().count.equals(entry.count)
                        && queue.peek().str.compareTo(entry.str)>0){
                    queue.poll();
                    queue.offer(entry);
                }
            }else{
                queue.offer( item.getValue() );
            }
        }

        LinkedList<String> result = new LinkedList<>();

        Entry ent = queue.poll();
        while(ent!=null){
            result.addFirst(ent.str);
            ent = queue.poll();
        }
        return result;
    }
    class Entry{
        String str;
        Integer count;

        public Entry(String str, Integer count) {
            this.str = str;
            this.count = count;
        }
    }

    @Test
    public void test1(){
        String[] str = {"the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"};
        List<String> result = topKFrequent(str, 4);
        for (String s : result)
            System.out.println(s);

        System.out.println("-------------------------------------------------");
        String[] str2 = {"i", "love", "leetcode", "i", "love", "coding"};
        result = topKFrequent(str2, 2);
        result.forEach(System.out::println);

        System.out.println("-------------------------------------------------");
        String[] str3 = {"glarko","zlfiwwb","nsfspyox","pwqvwmlgri","qggx","qrkgmliewc","zskaqzwo",
          "zskaqzwo","ijy","htpvnmozay","jqrlad","ccjel","qrkgmliewc","qkjzgws","fqizrrnmif",
          "jqrlad","nbuorw","qrkgmliewc","htpvnmozay","nftk","glarko","fr","axyak","hdemkfr",
          "nsfspyox","nsfspyox","qrkgmliewc","nftk","nftk","ccjel","qrkgmliewc","ocgjsu","ijy",
          "glarko","nbuorw","nsfspyox","qkjzgws","qkjzgws","fqizrrnmif","pwqvwmlgri","nftk",
          "qrkgmliewc","jqrlad","nftk","zskaqzwo","glarko","nsfspyox","zlfiwwb","hwlvqgkdbo",
          "htpvnmozay","nsfspyox","zskaqzwo","htpvnmozay","zskaqzwo","nbuorw","qkjzgws","zlfiwwb",
          "pwqvwmlgri","zskaqzwo","qengse","glarko","qkjzgws","pwqvwmlgri","fqizrrnmif","nbuorw",
          "nftk","ijy","hdemkfr","nftk","qkjzgws","jqrlad","nftk","ccjel","qggx","ijy","qengse","nftk",
          "htpvnmozay","qengse","eonrg","qengse","fqizrrnmif","hwlvqgkdbo","qengse","qengse","qggx",
          "qkjzgws","qggx","pwqvwmlgri","htpvnmozay","qrkgmliewc","qengse","fqizrrnmif","qkjzgws",
          "qengse","nftk","htpvnmozay","qggx","zlfiwwb","bwp","ocgjsu","qrkgmliewc","ccjel","hdemkfr",
          "nsfspyox","hdemkfr","qggx","zlfiwwb","nsfspyox","ijy","qkjzgws","fqizrrnmif","qkjzgws",
          "qrkgmliewc","glarko","hdemkfr","pwqvwmlgri"};

        result = topKFrequent(str3, 14);
        result.forEach(System.out::println);
    }

    /**
     * 直接排序
     */
    public List<String> topKFrequent2(String[] words, int k) {
        Map<String, Integer> count = new HashMap();
        for (String word : words)
            count.put(word, count.getOrDefault(word, 0) + 1);

        List<String> candidates = new ArrayList(count.keySet());
        Collections.sort(candidates, (w1, w2) -> count.get(w1).equals(count.get(w2)) ?
                w1.compareTo(w2) : count.get(w2) - count.get(w1));

        return candidates.subList(0, k);
    }

    /**
     * 堆写法精简
     */
    public List<String> topKFrequent3(String[] words, int k) {
        Map<String, Integer> count = new HashMap();
        for (String word: words)
            count.put(word, count.getOrDefault(word, 0) + 1);

        PriorityQueue<String> heap = new PriorityQueue<>(
                (w1, w2) -> count.get(w1).equals(count.get(w2)) ?
                        w2.compareTo(w1) : count.get(w1) - count.get(w2) );

        for (String word: count.keySet()) {
            heap.offer(word);
            if (heap.size() > k) heap.poll();
        }

        List<String> ans = new ArrayList();
        while (!heap.isEmpty()) ans.add(heap.poll());
        Collections.reverse(ans);
        return ans;
    }


    /**
     * 手工堆写法
     */
    public List<String> topKFrequent4(String[] words, int k) {

        Map<String, Integer> map = new HashMap<>();
        // 第一步：计算每个重复元素的个数
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            Integer count = map.get(word);
            if (count != null) {
                count = count + 1;
                map.put(word, count);
            } else {
                map.put(word, 1);
            }
        }
        Set<Map.Entry<String, Integer>> set = map.entrySet();
        int size = set.size();
        Node[] nodes = new Node[k];
        int i = 0;
        Heap heap = null;
        // 第二步：最小堆解题
        for (Map.Entry<String, Integer> entry : set) {
            if (i < k) {
                nodes[i++] = new Node(entry.getKey(), entry.getValue());
                if (i >= size) {
                    // 构建最小堆
                    heap = new Heap(nodes);
                }
                continue;
            } else if (i == k) {
                // 构建最小堆
                heap = new Heap(nodes);
            }
            // topk
            Node first = heap.getFirstNode();
            Node newNode = new Node(entry.getKey(), entry.getValue());
            // 新的节点和小顶堆的首元素比较,替换
            if (first.compareTo(newNode) == -1) {
                // 把大的值保留下来，替换小顶堆的顶部
                heap.replace(newNode, 1, k);
            }
            i++;
        }
        // 第三步：排序输出
        List<String> list = new ArrayList<>();
        Node[] ns = heap.sort(k);

        for (int j = 0; j < k; j++) {
            list.add(ns[j].getValue());
        }
        return list;
    }

}
