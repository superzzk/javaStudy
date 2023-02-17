//编写一个函数来查找字符串数组中的最长公共前缀。 
//
// 如果不存在公共前缀，返回空字符串 ""。 
//
// 
//
// 示例 1： 
//
// 
//输入：strs = ["flower","flow","flight"]
//输出："fl"
// 
//
// 示例 2： 
//
// 
//输入：strs = ["dog","racecar","car"]
//输出：""
//解释：输入不存在公共前缀。 
//
// 
//
// 提示： 
//
// 
// 1 <= strs.length <= 200 
// 0 <= strs[i].length <= 200 
// strs[i] 仅由小写英文字母组成 
// 
//
// Related Topics 字典树 字符串 👍 2636 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public String longestCommonPrefix(String[] strs) {
        String first = strs[0];
        int index =0;
        boolean same = true;
        while(index < first.length()){
            for (String str : strs) {
                if (str.length()<=index || str.charAt(index) != first.charAt(index)) {
                    same=false;
                    break;
                }
            }
            if(same)
                index++;
            else
                break;
        }
        return first.substring(0, index);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
