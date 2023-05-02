//给你一个字符数组 letters，该数组按非递减顺序排序，以及一个字符 target。letters 里至少有两个不同的字符。 
//
// 返回 letters 中大于 target 的最小的字符。如果不存在这样的字符，则返回 letters 的第一个字符。 
//
// 
//
// 示例 1： 
//
// 
//输入: letters = ["c", "f", "j"]，target = "a"
//输出: "c"
//解释：letters 中字典上比 'a' 大的最小字符是 'c'。 
//
// 示例 2: 
//
// 
//输入: letters = ["c","f","j"], target = "c"
//输出: "f"
//解释：letters 中字典顺序上大于 'c' 的最小字符是 'f'。 
//
// 示例 3: 
//
// 
//输入: letters = ["x","x","y","y"], target = "z"
//输出: "x"
//解释：letters 中没有一个字符在字典上大于 'z'，所以我们返回 letters[0]。 
//
// 
//
// 提示： 
//
// 
// 2 <= letters.length <= 10⁴ 
// letters[i] 是一个小写字母 
// letters 按非递减顺序排序 
// letters 最少包含两个不同的字母 
// target 是一个小写字母 
// 
//
// Related Topics 数组 二分查找 👍 254 👎 0

class Q744 {
    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public char nextGreatestLetter(char[] letters, char target) {
            int left = 0, right = letters.length - 1;
            int pick = 0;
            while (left <= right) {
                int cur = left + (right - left)/2;
                if (letters[cur] > target) {
                    pick = cur;
                    right = cur -1;
                } else if (letters[cur] <= target) {
                    left = cur +1;
                }
            }
            return letters[pick];
        }
    }
//leetcode submit region end(Prohibit modification and deletion)
}