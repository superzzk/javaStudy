package com.zzk.study.leetcode.leetcode.editor.cn;
//编写一个程序，找出第 n 个丑数。 
//
// 丑数就是质因数只包含 2, 3, 5 的正整数。 
//
// 示例: 
//
// 输入: n = 10
//输出: 12
//解释: 1, 2, 3, 4, 5, 6, 8, 9, 10, 12 是前 10 个丑数。 
//
// 说明: 
//
// 
// 1 是丑数。 
// n 不超过1690。 
// 
// Related Topics 堆 数学 动态规划 
// 👍 407 👎 0


import org.junit.Assert;

import java.util.*;

class UglyNumberIi {
    public static void main(String[] args) {
        Solution solution = new UglyNumberIi().new Solution();
        Assert.assertEquals(5, solution.nthUglyNumber(5));
        Assert.assertEquals(12, solution.nthUglyNumber(10));
        Assert.assertEquals(536870912, solution.nthUglyNumber(1407));
        Assert.assertEquals(1399680000, solution.nthUglyNumber(1600));
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public int nthUglyNumber(int n) {
            int[] primes = {2, 3, 5};

            Set<Long> seen = new HashSet<>();

            List<Long> seq = new ArrayList<>();

            PriorityQueue<Long> queue = new PriorityQueue<>();
            queue.offer(1L);

            while (seq.size() < n) {
                long temp = queue.poll();
                seq.add(temp);
                Arrays.stream(primes)
                        .forEach(i -> {
                            long ugly = temp * i;
                            if (ugly > 0 && !seen.contains(ugly)) {
                                seen.add(ugly);
                                queue.offer(ugly);
                            }
                        });
            }

            return seq.get(n - 1).intValue();
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}