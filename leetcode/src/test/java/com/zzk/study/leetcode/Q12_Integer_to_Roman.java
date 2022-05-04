package com.zzk.study.leetcode;//Roman numerals are represented by seven different symbols: I, V, X, L, C, D
//and M. 
//
// 
//Symbol       Value
//I             1
//V             5
//X             10
//L             50
//C             100
//D             500
//M             1000 
//
// For example, 2 is written as II in Roman numeral, just two one's added 
//together. 12 is written as XII, which is simply X + II. The number 27 is written as 
//XXVII, which is XX + V + II. 
//
// Roman numerals are usually written largest to smallest from left to right. 
//However, the numeral for four is not IIII. Instead, the number four is written as 
//IV. Because the one is before the five we subtract it making four. The same 
//principle applies to the number nine, which is written as IX. There are six 
//instances where subtraction is used: 
//
// 
// I can be placed before V (5) and X (10) to make 4 and 9. 
// X can be placed before L (50) and C (100) to make 40 and 90. 
// C can be placed before D (500) and M (1000) to make 400 and 900. 
// 
//
// Given an integer, convert it to a roman numeral. 
//
// 
// Example 1: 
//
// 
//Input: num = 3
//Output: "III"
//Explanation: 3 is represented as 3 ones.
// 
//
// Example 2: 
//
// 
//Input: num = 58
//Output: "LVIII"
//Explanation: L = 50, V = 5, III = 3.
// 
//
// Example 3: 
//
// 
//Input: num = 1994
//Output: "MCMXCIV"
//Explanation: M = 1000, CM = 900, XC = 90 and IV = 4.
// 
//
// 
// Constraints: 
//
// 
// 1 <= num <= 3999 
// 
// Related Topics Hash Table Math String 👍 3005 👎 3942


import org.junit.Test;

import java.util.HashMap;

//leetcode submit region begin(Prohibit modification and deletion)
public class Q12_Integer_to_Roman {
    int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    public String intToRoman(int num) {
        StringBuilder res = new StringBuilder();
        while(num>0){
            for (int i = 0; i < values.length; i++) {
                if(num>values[i]){
                    res.append(symbols[i]);
                    num -= values[i];
                }
            }
        }

        return res.toString();
    }

    private void iterate(StringBuilder res, int num){

        if(num>=1000) {
            res.append("M");
            iterate(res, num-1000);
        }
        if(num>=900){
            res.append("CM");
            iterate(res, num-900);
        }
        if(num>=500){
            res.append("D");
            iterate(res, num-500);
        }
        if(num>=400){
            res.append("CD");
            iterate(res, num-400);
        }
        if(num>=100){
            res.append("C");
            iterate(res, num-100);
        }
        if(num>=90){
            res.append("XC");
            iterate(res, num-90);
        }
        if(num>=50){
            res.append("L");
            iterate(res, num-50);
        }
        if(num>=40){
            res.append("XL");
            iterate(res, num-40);
        }
        if(num>=10){
            res.append("X");
            iterate(res, num-10);
        }
        if(num>=9){
            res.append("IX");
            iterate(res, num-9);
        }
        if(num>=5){
            res.append("V");
            iterate(res,num-5);
        }
        if(num==4){
            res.append("IV");
            iterate(res,num-4);
        }
        if(num>=1){
            res.append("I");
            iterate(res,num-1);
        }

    }

}
//leetcode submit region end(Prohibit modification and deletion)
