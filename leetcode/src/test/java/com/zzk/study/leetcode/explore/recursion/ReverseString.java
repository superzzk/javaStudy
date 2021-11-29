package com.zzk.study.leetcode.explore.recursion;

import org.junit.Test;

/**
 * Write a function that reverses a string. The input string is given as an array of characters char[].
 *
 * Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.
 *
 * You may assume all the characters consist of printable ascii characters.
 *
 *
 *
 * Example 1:
 *
 * Input: ["h","e","l","l","o"]
 * Output: ["o","l","l","e","h"]
 *
 * Example 2:
 *
 * Input: ["H","a","n","n","a","h"]
 * Output: ["h","a","n","n","a","H"]
 **/
public class ReverseString {

    @Test
    public void reverseString(char[] s) {
        if(s.length == 0)
            return;
        reverseString_helper(s, 0, s.length-1);
    }
    private void reverseString_helper(char[] s, int start, int end){
        if(start>=end)
            return;
        swap(s,start,end);
        reverseString_helper(s, ++start, --end);
    }
    private void swap(char[] s, int a, int b){
        char temp = s[a];
        s[a] = s[b];
        s[b] = temp;
    }
}
