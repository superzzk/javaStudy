//给你一个字符串数组 nums 和一个整数 k 。nums 中的每个字符串都表示一个不含前导零的整数。 
//
// 返回 nums 中表示第 k 大整数的字符串。 
//
// 注意：重复的数字在统计时会视为不同元素考虑。例如，如果 nums 是 ["1","2","2"]，那么 "2" 是最大的整数，"2" 是第二大的整数，"1
//" 是第三大的整数。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = ["3","6","7","10"], k = 4
//输出："3"
//解释：
//nums 中的数字按非递减顺序排列为 ["3","6","7","10"]
//其中第 4 大整数是 "3"
// 
//
// 示例 2： 
//
// 
//输入：nums = ["2","21","12","1"], k = 3
//输出："2"
//解释：
//nums 中的数字按非递减顺序排列为 ["1","2","12","21"]
//其中第 3 大整数是 "2"
// 
//
// 示例 3： 
//
// 
//输入：nums = ["0","0"], k = 2
//输出："0"
//解释：
//nums 中的数字按非递减顺序排列为 ["0","0"]
//其中第 2 大整数是 "0"
// 
//
// 
//
// 提示： 
//
// 
// 1 <= k <= nums.length <= 10⁴ 
// 1 <= nums[i].length <= 100 
// nums[i] 仅由数字组成 
// nums[i] 不含任何前导零 
// 
//
// Related Topics 数组 字符串 分治 快速选择 排序 堆（优先队列） 👍 53 👎 0


import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.TreeSet;
class Q1985 {

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public String kthLargestNumber(String[] nums, int k) {
            PriorityQueue<String> queue = new PriorityQueue<>((o1, o2) -> {
                if (o1.length() != o2.length()) {
                    return o1.length() - o2.length();
                } else {
                    return o1.compareTo(o2);
                }
            });
            for (String num : nums) {
                if (queue.add(num)) {
                    if (queue.size() > k) {
                        queue.poll();
                    }
                }
            }
            return queue.peek();
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

    // 排序
    class Solution2 {
        public String kthLargestNumber(String[] nums, int k) {
            Arrays.sort(nums, Comparator.comparingInt(String::length)
                    .thenComparing(Comparator.naturalOrder()).reversed());
            return nums[k - 1];
        }
    }

    public static void main(String[] args) {
        Solution solution = new Q1985().new Solution();
        String rt = solution.kthLargestNumber(new String[]{"3", "6", "7", "10"}, 4);
//        String rt = solution.kthLargestNumber(new String[]{"0", "1", "1"}, 2);
        System.out.println(rt);
    }
}


