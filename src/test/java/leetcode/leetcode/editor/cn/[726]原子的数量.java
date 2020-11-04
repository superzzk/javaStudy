package leetcode.leetcode.editor.cn;
//给定一个化学式formula（作为字符串），返回每种原子的数量。 
//
// 原子总是以一个大写字母开始，接着跟随0个或任意个小写字母，表示原子的名字。 
//
// 如果数量大于 1，原子后会跟着数字表示原子的数量。如果数量等于 1 则不会跟数字。
// 例如，H2O 和 H2O2 是可行的，但 H1O2 这个表达是不可行的。
// 
//
// 两个化学式连在一起是新的化学式。例如 H2O2He3Mg4 也是化学式。 
//
// 一个括号中的化学式和数字（可选择性添加）也是化学式。例如 (H2O2) 和 (H2O2)3 是化学式。 
//
// 给定一个化学式，输出所有原子的数量。
// 格式为：第一个（按字典序）原子的名子，跟着它的数量（如果数量大于 1），
// 然后是第二个原子的名字（按字典序），跟着它的数量（如果数量大于 1），以此类推。
//
// 示例 1:
// 
//输入: 
//formula = "H2O"
//输出: "H2O"
//解释: 
//原子的数量是 {'H': 2, 'O': 1}。
// 
//
// 示例 2:
// 
//输入: 
//formula = "Mg(OH)2"
//输出: "H2MgO2"
//解释: 
//原子的数量是 {'H': 2, 'Mg': 1, 'O': 2}。
// 
//
// 示例 3:
// 
//输入: 
//formula = "K4(ON(SO3)2)2"
//输出: "K4N2O14S4"
//解释: 
//原子的数量是 {'K': 4, 'N': 2, 'O': 14, 'S': 4}。
//
// 注意: 
//
// 所有原子的第一个字母为大写，剩余字母都是小写。 
// formula的长度在[1, 1000]之间。 
// formula只包含字母、数字和圆括号，并且题目中给定的是合法的化学式。 
// 
// Related Topics 栈 递归 哈希表 
// 👍 95 👎 0

import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

class NumberOfAtoms {
    public static void main(String[] args) {
        Solution solution = new NumberOfAtoms().new Solution();
//        solution.countOfAtoms("H2O");
//        solution.countOfAtoms("Mg(OH)2");
//        solution.countOfAtoms("Mg(T2(OH)2)2");
        Assert.assertEquals("K4N2O14S4",solution.countOfAtoms("K4(ON(SO3)2)2"));
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public String countOfAtoms(String formula) {
            Map<String, Integer> res = process(formula);
            System.out.println(res);
            StringBuilder builder = new StringBuilder();
            res.forEach((k,v)->{
                builder.append(k)
                        .append(v == 1 ? "" : v);
            });
            return builder.toString();
        }

        int index = 0;

        Map<String, Integer> process(String formula) {
            Map<String, Integer> map = new TreeMap<>();
            String symbol = "";
            int cnt = 0;
            for (; index < formula.length() && formula.charAt(index)!=')';) {
                char c = formula.charAt(index);
                if (Character.isUpperCase(c)) {
                    symbol = String.valueOf(c);
                    while(index+1<formula.length() && Character.isLowerCase(formula.charAt(index+1))){
                        symbol += String.valueOf(formula.charAt(++index));
                    }
                    int num=0;
                    while(index+1<formula.length() && Character.isDigit(formula.charAt(index+1))){
                        num = num * 10 + Character.getNumericValue(formula.charAt(++index));
                    }
                    int finalCnt = num == 0 ? 1 : num;
                    map.compute(symbol, (k, v) -> v == null ? finalCnt : finalCnt + v);
                    symbol = "";
                    index++;
                } else if (c == '(') {
                    index++;
                    Map<String, Integer> inner = process(formula);
                    int multiply = 0;
                    while (index < formula.length() && Character.isDigit(formula.charAt(index))) {
                        multiply = multiply * 10 + Character.getNumericValue(formula.charAt(index++));
                    }
                    int finalMultiply = multiply == 0 ? 1 : multiply;
                    inner.replaceAll((k, v) -> v * finalMultiply);

                    inner.forEach((k, v) -> {
                        if (map.containsKey(k))
                            map.put(k, v + map.get(k));
                        else
                            map.put(k, v);
                    });
                }
            }
            index++;
//            cnt = cnt == 0 ? 1 : cnt;
//            int finalCnt1 = cnt;
//            map.compute(symbol, (k, v) -> v == null ? finalCnt1 : finalCnt1 + v);
            return map;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}