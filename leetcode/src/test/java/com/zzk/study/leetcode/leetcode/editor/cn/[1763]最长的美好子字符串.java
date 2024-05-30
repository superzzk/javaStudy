//当一个字符串 s 包含的每一种字母的大写和小写形式 同时 出现在 s 中，就称这个字符串 s 是 美好 字符串。比方说，"abABB" 是美好字符串，因为 
//'A' 和 'a' 同时出现了，且 'B' 和 'b' 也同时出现了。然而，"abA" 不是美好字符串因为 'b' 出现了，而 'B' 没有出现。 
//
// 给你一个字符串 s ，请你返回 s 最长的 美好子字符串 。如果有多个答案，请你返回 最早 出现的一个。如果不存在美好子字符串，请你返回一个空字符串。 
//
// 
//
// 示例 1： 
//
// 
//输入：s = "YazaAay"
//输出："aAa"
//解释："aAa" 是一个美好字符串，因为这个子串中仅含一种字母，其小写形式 'a' 和大写形式 'A' 也同时出现了。
//"aAa" 是最长的美好子字符串。
// 
//
// 示例 2： 
//
// 
//输入：s = "Bb"
//输出："Bb"
//解释："Bb" 是美好字符串，因为 'B' 和 'b' 都出现了。整个字符串也是原字符串的子字符串。 
//
// 示例 3： 
//
// 
//输入：s = "c"
//输出：""
//解释：没有美好子字符串。 
//
// 示例 4： 
//
// 
//输入：s = "dDzeE"
//输出："dD"
//解释："dD" 和 "eE" 都是最长美好子字符串。
//由于有多个美好子字符串，返回 "dD" ，因为它出现得最早。 
//
// 
//
// 提示： 
//
// 
// 1 <= s.length <= 100 
// s 只包含大写和小写英文字母。 
// 
//
// Related Topics 位运算 哈希表 字符串 分治 滑动窗口 👍 232 👎 0


class Q1763LongestNiceSubstring {

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        String res = "";
        public String longestNiceSubstring(String s) {
            char[] chars = s.toCharArray();
            recur(chars, 0, s.length());
            return res;
        }

        private void recur(char[] chars, int start, int end) {
            if (start >= end) {
                return;
            }
            int firstUnNiceChar = firstUnNiceChar(chars, start, end);
            if(firstUnNiceChar == -1){
                if((end-start) > res.length())
                    res = new String(chars, start, end-start);
                return;
            }

            recur(chars, start, firstUnNiceChar);
            recur(chars, firstUnNiceChar+1, end);
        }

        private int firstUnNiceChar(char[] chars, int start, int end) {
            for (int i = start; i < end; i++) {
                if (!isNiceChar(chars, start, end, chars[i])) {
                    return i;
                }
            }
            return -1;
        }

        private boolean isNiceChar(char[] chars, int start, int end, char c) {
            if (Character.isLowerCase(c)) {
                return exist(chars, start, end, Character.toUpperCase(c));
            } else {
                return exist(chars, start, end, Character.toLowerCase(c));
            }
        }

        private boolean exist(char[] chars, int start, int end, char c) {
            for (int i = start; i < end; i++) {
                if (chars[i] == c)
                    return true;
            }
            return false;
        }
    }

    //leetcode submit region end(Prohibit modification and deletion)
    // 暴力
    class Solution1 {
        public String longestNiceSubstring(String s) {
            String res = "";
            char[] chars = s.toCharArray();
            for (int i = 0; i < chars.length - 1; i++) {
                for (int j = chars.length; j >= i + 2; j--) {
                    if (isNice(chars, i, j)) {
                        String candidate = s.substring(i, j);
                        if (candidate.length() > res.length())
                            res = candidate;
                    }

                }
            }
            return res;
        }

        private boolean isNice(char[] chars, int start, int end) {
            for (int i = start; i < end; i++) {
                if (Character.isLowerCase(chars[i])) {
                    if (!exist(chars, start, end, Character.toUpperCase(chars[i])))
                        return false;
                } else {
                    if (!exist(chars, start, end, Character.toLowerCase(chars[i])))
                        return false;
                }
            }
            return true;
        }

        private boolean exist(char[] chars, int start, int end, char c) {
            for (int i = start; i < end; i++) {
                if (chars[i] == c)
                    return true;
            }
            return false;
        }
    }
}