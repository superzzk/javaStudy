//给定一组非负整数 nums，重新排列每个数的顺序（每个数不可拆分）使之组成一个最大的整数。 
//
// 注意：输出结果可能非常大，所以你需要返回一个字符串而不是整数。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [10,2]
//输出："210" 
//
// 示例 2： 
//
// 
//输入：nums = [3,30,34,5,9]
//输出："9534330"
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 100 
// 0 <= nums[i] <= 10⁹ 
// 
//
// Related Topics 贪心 数组 字符串 排序 👍 1270 👎 0

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class Q179 {

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {

        public String largestNumber(int[] nums) {
            // 使用stream将nums转换为Integer[]
            Integer[] list = Arrays.stream(nums).boxed().toArray(Integer[]::new);

            Arrays.sort(list, (a, b) -> {
                if (Objects.equals(a, b)) return 0;
                String s1 = String.valueOf(a);
                String s2 = String.valueOf(b);
                return (s2 + s1).compareTo(s1 + s2);
            });

            String res = Arrays.stream(list).map(String::valueOf).collect(Collectors.joining());
            // remove res's leading 0
            while (res.length() > 1 && res.charAt(0) == '0') {
                res = res.substring(1);
            }
            return res;
        }

    }
//leetcode submit region end(Prohibit modification and deletion)

    // 暴力，超时
    class Solution2 {

        String rt = "";
        boolean[] used;

        public String largestNumber(int[] nums) {
            used = new boolean[nums.length];
            dfs(nums, new StringBuilder(), 0);
            // remove rt's leading 0
            while (rt.length() > 1 && rt.charAt(0) == '0') {
                rt = rt.substring(1);
            }
            return rt;
        }

        private void dfs(int[] nums, StringBuilder sb, int count) {
            if (count == nums.length) {
                rt = sb.toString().compareTo(rt) > 0 ? sb.toString() : rt;
                return;
            }
            for (int i = 0; i < nums.length; i++) {
                if (used[i])
                    continue;
                used[i] = true;
                int len = sb.length();
                sb.append(nums[i]);
                dfs(nums, sb, count + 1);
                sb.delete(len, sb.length());
                used[i] = false;
            }
        }
    }

    public static void main(String[] args) {
        Solution solution = new Q179().new Solution();
        String rt = solution.largestNumber(new int[]{3, 30, 34, 5, 9});
        System.out.println(rt);
    }
}