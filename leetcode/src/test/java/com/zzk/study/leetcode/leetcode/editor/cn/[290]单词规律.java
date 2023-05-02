//给定一种规律 pattern 和一个字符串 s ，判断 s 是否遵循相同的规律。 
//
// 这里的 遵循 指完全匹配，例如， pattern 里的每个字母和字符串 s 中的每个非空单词之间存在着双向连接的对应规律。 
//
// 
//
// 示例1: 
//
// 
//输入: pattern = "abba", s = "dog cat cat dog"
//输出: true 
//
// 示例 2: 
//
// 
//输入:pattern = "abba", s = "dog cat cat fish"
//输出: false 
//
// 示例 3: 
//
// 
//输入: pattern = "aaaa", s = "dog cat cat dog"
//输出: false 
//
// 
//
// 提示: 
//
// 
// 1 <= pattern.length <= 300 
// pattern 只包含小写英文字母 
// 1 <= s.length <= 3000 
// s 只包含小写英文字母和 ' ' 
// s 不包含 任何前导或尾随对空格 
// s 中每个单词都被 单个空格 分隔 
// 
//
// Related Topics 哈希表 字符串 👍 539 👎 0

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class Q290 {
    public static void main(String args[]) {
        Q290.Solution solution = new Q290().new Solution();
        final boolean rt = solution.wordPattern("abba", "dog cat cat dog");
        System.out.println(rt);
    }
    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public boolean wordPattern(String pattern, String s) {
            final String[] words = s.split(" ");
            if(pattern.toCharArray().length != words.length) return false;

            Map<Character, String> map = new HashMap<>();
            Set<String> allWords = new HashSet<>();
            int i = 0;
            for (Character c : pattern.toCharArray()) {
                String word = words[i++];
                if (map.containsKey(c)) {
                    if(!map.get(c).equals(word))
                        return false;
                }else{
                    if(allWords.contains(word)) return false;
                    map.put(c, word);
                    allWords.add(word);
                }
            }
            return true;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)
}