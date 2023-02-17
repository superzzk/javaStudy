package com.zzk.study.leetcode.leetcode.editor.en;//////Given a string containing digits from 2-9 inclusive, return all possible
//////letter combinations that the number could represent. Return the answer in 
//
////any order. 
//////
//////
////// A mapping of digit to letters (just like on the telephone buttons) is 
////given 
//////below. Note that 1 does not map to any letters. 
//////
////// 
//////
////// 
////// Example 1: 
//////
////// 
//////Input: digits = "23"
//////Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
////// 
//////
////// Example 2: 
//////
////// 
//////Input: digits = ""
//////Output: []
////// 
//////
////// Example 3: 
//////
////// 
//////Input: digits = "2"
//////Output: ["a","b","c"]
////// 
//////
////// 
////// Constraints: 
//////
////// 
////// 0 <= digits.length <= 4 
////// digits[i] is a digit in the range ['2', '9']. 
////// 
////// Related Topics Hash Table String Backtracking 👍 9531 👎 664
////
//


import org.junit.Test;

import javax.security.auth.login.CredentialNotFoundException;
import java.util.ArrayList;
import java.util.List;
public class Q17 {
    //leetcode submit region begin(Prohibit modification and deletion)
    static class Solution {


        public List<String> letterCombinations(String digits) {
            List<String> res = new ArrayList<>();

            recur(res, new StringBuilder(), digits, 0);

            return res;
        }

        private void recur(List<String> result, StringBuilder cur, String digits, int digitIndex){
            if (digits.length() == cur.length()) {
                result.add(cur.toString());
                return;
            }
            if (digitIndex == digits.length()) {
                return;
            }
            final String[] letters = getLetters(Integer.parseInt(String.valueOf(digits.charAt(digitIndex))));

            for (int i = 0; i < letters.length; i++) {
                cur.append(letters[i]);
                recur(result, cur, digits, digitIndex + 1);
                cur.deleteCharAt(cur.length()-1);
            }
        }

        private String[] getLetters(int num) {
            if(2==num) return new String[]{"a","b","c"};
            if(3==num) return new String[]{"d","e","f"};
            return new String[]{};
        }
    }

    //leetcode submit region end(Prohibit modification and deletion)

    @Test
    public void test(){
        Solution solution = new Q17.Solution();
        System.out.println(solution.letterCombinations("23"));
    }
}