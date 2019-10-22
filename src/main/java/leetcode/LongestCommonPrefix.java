package leetcode;

/**
 *
 * https://leetcode.com/problems/longest-common-prefix
 * Write a function to find the longest common prefix string amongst an array of strings.
 *
 * If there is no common prefix, return an empty string "".
 *
 * Example 1:
 *
 * Input: ["flower","flow","flight"]
 * Output: "fl"
 *
 * Example 2:
 *
 * Input: ["dog","racecar","car"]
 * Output: ""
 * Explanation: There is no common prefix among the input strings.
 *
 * Note:
 *
 * All given inputs are in lowercase letters a-z.
 **/
public class LongestCommonPrefix {
    public static void main(String[] args) {
        Solution solution = new Solution();
        //String[] strs = {"flower","flow","flight"};
        String[] strs = {"dog","racecar","car"};
        System.out.println(solution.longestCommonPrefix(strs));
    }

    static class Solution {
        public String longestCommonPrefix(String[] strs) {
            String pre="";
            if(strs.length==0)
                return "";
            //找出所有字符串中最小长度
            int minLen=strs[0].length();
            for(String str : strs){
                if(str.length()<minLen)
                    minLen=str.length();
            }

            for(int i=0; i<minLen; i++){
                if(strsEquals(strs,i))
                    pre+=strs[0].charAt(i);
                else
                    break;
            }
            return  pre;
        }
        //判断一个数组中的字符串某一位是否全部相同
        private boolean strsEquals(String[] strs, int index){
            char c = strs[0].charAt(index);
            for(String str : strs){
                if(c != str.charAt(index))
                    return false;
            }
            return true;
        }

        public String longestCommonPrefix2(String[] strs) {
            if (strs.length == 0) return "";
            String prefix = strs[0];
            for (int i = 1; i < strs.length; i++)
                while (strs[i].indexOf(prefix) != 0) {
                    prefix = prefix.substring(0, prefix.length() - 1);
                    if (prefix.isEmpty()) return "";
                }
            return prefix;
        }
    }

}
