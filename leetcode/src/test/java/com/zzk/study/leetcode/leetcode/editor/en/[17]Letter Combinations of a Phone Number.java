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

import java.util.ArrayList;
import java.util.List;

class Q17 {
    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {

        public List<String> letterCombinations(String digits) {
            List<String> res = new ArrayList<>();


            return res;
        }
    }
    //leetcode submit region end(Prohibit modification and deletion)

    @Test
    public void test(){
        Solution solution = new Q17.Solution();
        System.out.println(solution.letterCombinations("23"));
    }
}