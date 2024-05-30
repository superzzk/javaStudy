//给你一个含重复值的二叉搜索树（BST）的根节点 root ，找出并返回 BST 中的所有 众数（即，出现频率最高的元素）。 
//
// 如果树中有不止一个众数，可以按 任意顺序 返回。 
//
// 假定 BST 满足如下定义： 
//
// 
// 结点左子树中所含节点的值 小于等于 当前节点的值 
// 结点右子树中所含节点的值 大于等于 当前节点的值 
// 左子树和右子树都是二叉搜索树 
// 
//
// 
//
// 示例 1： 
// 
// 
//输入：root = [1,null,2,2]
//输出：[2]
// 
//
// 示例 2： 
//
// 
//输入：root = [0]
//输出：[0]
// 
//
// 
//
// 提示： 
//
// 
// 树中节点的数目在范围 [1, 10⁴] 内 
// -10⁵ <= Node.val <= 10⁵ 
// 
//
// 
//
// 进阶：你可以不使用额外的空间吗？（假设由递归产生的隐式调用栈的开销不被计算在内） 
//
// Related Topics 树 深度优先搜索 二叉搜索树 二叉树 👍 749 👎 0

import com.zzk.study.leetcode.leetcode.editor.cn.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Q501 {

//leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        ArrayList<Integer> res = new ArrayList<>();
        Integer pre = null;
        int count = 0;
        int maxCount = 0;

        public int[] findMode(TreeNode root) {
            dfs(root);
            if (count > maxCount) {
                res = new ArrayList<>();
                res.add(pre);
                maxCount = count;
            } else if (count == maxCount) {
                res.add(pre);
            }
            return res.stream().mapToInt(Integer::intValue).toArray();
        }

        private void dfs(TreeNode node) {
            if (node == null)
                return;
            dfs(node.left);
            if (pre != null) {
                if (pre == node.val) {
                    count++;
                } else {
                    if (count > maxCount) {
                        res.clear();
                        res.add(pre);
                        maxCount = count;
                    } else if (count == maxCount) {
                        res.add(pre);
                    }
                    count = 1;
                }
            } else {
                count = 1;
            }
            pre = node.val;
            dfs(node.right);
        }

    }
//leetcode submit region end(Prohibit modification and deletion)


    class Solution2 {
        public String[] findWords(String[] words) {
            List<String> res = new ArrayList<>();
            String[] keyboard = {
                    "qwertyuiop",
                    "asdfghjkl",
                    "zxcvbnm"
            };

            for(String word : words){
                word = word.toLowerCase();
                for(String line : keyboard){
                    boolean flag = true;
                    for(char c : line.toCharArray()){
                        if(word.indexOf(Character.toLowerCase(c)) == -1){
                            flag = false;
                            break;
                        }
                    }
                    if(flag){
                        res.add(word);
                        continue;
                    }
                }
            }

            return res.toArray(new String[0]);
        }
    }


}