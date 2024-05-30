import java.util.Stack;

//给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
//
// 
//
// 示例 1： 
//
// 
//
// 
//输入：height = [0,1,0,2,1,0,1,3,2,1,2,1]
//输出：6
//解释：上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。 
// 
//
// 示例 2： 
//
// 
//输入：height = [4,2,0,3,2,5]
//输出：9
// 
//
// 
//
// 提示： 
//
// 
// n == height.length 
// 1 <= n <= 2 * 10⁴ 
// 0 <= height[i] <= 10⁵ 
// 
//
// Related Topics 栈 数组 双指针 动态规划 单调栈 👍 5163 👎 0
class Q42 {

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public int trap(int[] height) {
            Stack<Integer> stack = new Stack<>();
            int res = 0;
            int pre = height[0];
            if (height[0] != 0) {
                stack.push(0);
            }
            for (int i = 1; i < height.length; i++) {
                if (height[i] > pre) {
                    while (!stack.isEmpty()) {
                        Integer pop = stack.pop();
                        int level = Math.min(height[i], height[pop]);
                        int water = (i - 1 - pop) * (level - pre);
//                        System.out.println("pop:" + pop + " level:" + level + " water:" + water);
                        pre = level;
                        res += water;
                        if(height[i] < height[pop]){
                            stack.push(pop);
                            break;
                        }
                    }
                    stack.push(i);
                } else {
                    if (height[i] > 0) {
                        stack.push(i);
                    }
                }
                pre = height[i];
            }
            return res;
        }
    }

    //leetcode submit region end(Prohibit modification and deletion)
// 一行一行计算，超时
    class Solution2 {
        public int trap(int[] height) {
            int res = 0;
            int level = 0;
            int max = max(height);

            while (true) {
                // each level
                int water = 0;
                int left = 0;
                int right = 0;
                int i = 0;

                // skip base
                while (i < height.length && height[i] == level) {
                    i++;
                    continue;
                }
                if (i == height.length) break;

                while (i < height.length) {
                    // find next base
                    while (i < height.length && height[i] > level) {
                        i++;
                        continue;
                    }
                    if (i == height.length) break;
                    left = i;
                    // count base
                    while (i < height.length && height[i] == level) {
                        i++;
                        continue;
                    }
                    if (i == height.length) break;
                    right = i;
                    water += right - left;
                }

                res += water;
                if (level == max)
                    break;

                levelUp(height, level++);
            }
            return res;
        }

        private void levelUp(int[] height, int level) {
            for (int i = 0; i < height.length; i++) {
                if (height[i] == level)
                    height[i] = height[i] + 1;
            }
        }

        private int max(int[] height) {
            int res = height[0];
            for (int i = 0; i < height.length; i++) {
                res = Math.max(res, height[i]);
            }
            return res;
        }
    }

    public static void main(String[] args) {
        int[] height = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        System.out.println(new Q42().new Solution().trap(height));
    }
}