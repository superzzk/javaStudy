package com.zzk.study.leetcode.leetcode.editor.cn;//给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
//
// 有效字符串需满足： 
//
// 左括号必须用相同类型的右括号闭合。 
// 左括号必须以正确的顺序闭合。 
// 每个右括号都有一个对应的相同类型的左括号。 
//
// 示例 1： 
//
//输入：s = "()"
//输出：true
//
// 示例 2： 
//
//输入：s = "()[]{}"
//输出：true
//
// 示例 3： 
//
//输入：s = "(]"
//输出：false
//
// 提示： 
//
// 1 <= s.length <= 10⁴ 
// s 仅由括号 '()[]{}' 组成 
// 
// Related Topics 栈 字符串 👍 3591 👎 0


import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
class ValidParenthesis {
    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public boolean isValid(String s) {
            Map<Character, Character> map = new HashMap<>();
            map.put('(', ')');
            map.put('[', ']');
            map.put('{', '}');
            Stack<Character> stack = new Stack<>();
            for (char c : s.toCharArray()) {
                if (c == '(' || c == '[' || c == '{') {
                    stack.push(c);
                }
                if (c == ')' || c == ']' || c == '}') {
                    if (stack.empty())
                        return false;
                    if (map.get(stack.peek()).equals(c)) {
                        stack.pop();
                    } else
                        return false;
                }
            }
            if (stack.empty())
                return true;
            return false;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)
}