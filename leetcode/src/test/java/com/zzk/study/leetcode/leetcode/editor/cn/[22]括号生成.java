//数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。 
//
// 
//
// 示例 1： 
//
// 
//输入：n = 3
//输出：["((()))","(()())","(())()","()(())","()()()"]
// 
//
// 示例 2： 
//
// 
//输入：n = 1
//输出：["()"]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= n <= 8 
// 
//
// Related Topics 字符串 动态规划 回溯 👍 3171 👎 0

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class Q22 {
    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public List<String> generateParenthesis(int n) {
            List<String> res = new ArrayList<>();
            backtrace(new StringBuilder(), res, n);
            return res;
        }

        void backtrace(StringBuilder cache, List<String> res, int count) {
            if (cache.length() == count * 2 && valid(cache)) {
                res.add(cache.toString());
                return;
            } else if (cache.length() > count * 2) {
                return;
            }

            cache.append('(');
            backtrace(cache, res, count);
            cache.deleteCharAt(cache.length() - 1);

            cache.append(')');
            backtrace(cache,res,count);
            cache.deleteCharAt(cache.length() - 1);
        }

        boolean valid(StringBuilder s) {
            Stack<Character> stack = new Stack<>();
            for (char c : s.toString().toCharArray() ){
                if(c == '(') stack.push(c);
                if(c == ')') {
                    if(stack.isEmpty()) return false;
                    stack.pop();
                }
            }
            return stack.isEmpty();
        }
    }
//leetcode submit region end(Prohibit modification and deletion)
}
