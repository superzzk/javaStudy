//给定一个字符串 s ，请你找出其中不含有重复字符的 最长 子串 的长度。 
//
// 
//
// 示例 1: 
//
// 
//输入: s = "abcabcbb"
//输出: 3 
//解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
// 
//
// 示例 2: 
//
// 
//输入: s = "bbbbb"
//输出: 1
//解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
// 
//
// 示例 3: 
//
// 
//输入: s = "pwwkew"
//输出: 3
//解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
//     请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
// 
//
// 
//
// 提示： 
//
//
// 0 <= s.length <= 5 * 10⁴
// s 由英文字母、数字、符号和空格组成
//
//
// Related Topics 哈希表 字符串 滑动窗口 👍 10150 👎 0

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

class Q3 {
    //leetcode submit region begin(Prohibit modification and deletion)

    class Solution {
        public int lengthOfLongestSubstring(String s) {
            int res = 0;
            Set<Character> set = new HashSet<>();
            int right = -1;
            for (int left = 0; left < s.length(); left++) {
                if (left != 0) {
                    // 说明左边界前进了一步，删除最左字符
                    set.remove(s.charAt(left - 1));
                }
                // 只要右边满足条键，向右滑动
                while (right < s.length() - 1 && !set.contains(s.charAt(right + 1))) {
                    set.add(s.charAt(++right));
                }
                res = Math.max(res, right - left + 1);
                // 有边界已扩展完毕，左边界前进一步(这里也是一个优化空间，可以前进多步)
            }
            return res;
        }
    }

//leetcode submit region end(Prohibit modification and deletion)


    // 暴力查找
    class Solution1 {
        public int lengthOfLongestSubstring(String s) {
            int res = 0;
            Set<Character> set = new HashSet<>();
            for (int i = 0; i < s.length(); i++) {
                set.clear();
                set.add(s.charAt(i));
                for (int j = i + 1; j < s.length(); j++) {
                    if (set.contains(s.charAt(j))) {
                        break;
                    } else {
                        set.add(s.charAt(j));
                    }
                }
                res = Math.max(res, set.size());
            }

            return res;
        }
    }

    class SolutionSlideWindow {
        public int lengthOfLongestSubstring(String s) {
            int res = 0;
            int left = 0;
            for (int right = 0; right < s.length(); right++) {
                char c = s.charAt(right);
                while(s.indexOf(c, left) != right)
                    left++;
                res = Math.max(res, right - left + 1);
            }
            return res;
        }
    }

}