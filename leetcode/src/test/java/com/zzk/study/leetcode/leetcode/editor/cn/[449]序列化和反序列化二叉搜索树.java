//序列化是将数据结构或对象转换为一系列位的过程，以便它可以存储在文件或内存缓冲区中，或通过网络连接链路传输，以便稍后在同一个或另一个计算机环境中重建。 
//
// 设计一个算法来序列化和反序列化 二叉搜索树 。 对序列化/反序列化算法的工作方式没有限制。 您只需确保二叉搜索树可以序列化为字符串，并且可以将该字符串反序
//列化为最初的二叉搜索树。 
//
// 编码的字符串应尽可能紧凑。 
//
// 
//
// 示例 1： 
//
// 
//输入：root = [2,1,3]
//输出：[2,1,3]
// 
//
// 示例 2： 
//
// 
//输入：root = []
//输出：[]
// 
//
// 
//
// 提示： 
//
// 
// 树中节点数范围是 [0, 10⁴] 
// 0 <= Node.val <= 10⁴ 
// 题目数据 保证 输入的树是一棵二叉搜索树。 
// 
//
// Related Topics 树 深度优先搜索 广度优先搜索 设计 二叉搜索树 字符串 二叉树 👍 549 👎 0


import com.zzk.study.leetcode.leetcode.editor.cn.TreeNode;
import com.zzk.study.leetcode.leetcode.editor.cn.util.TreeUtils;

import java.util.LinkedList;
import java.util.Queue;

class Q449 {
    //leetcode submit region begin(Prohibit modification and deletion)
    public class Codec {

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            if (root == null)
                return "";
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);

            StringBuilder sb = new StringBuilder();
            TreeNode empty = new TreeNode();
            while (!queue.isEmpty()) {
                int size = queue.size();
                boolean existElement = false;
                for (int i = 0; i < size; i++) {
                    TreeNode poll = queue.poll();
                    if (poll == empty)
                        sb.append(":n");
                    else {
                        existElement = true;
                        sb.append(":").append(poll.val);
                    }
                    if (poll.left != null)
                        queue.offer(poll.left);
                    else
                        queue.offer(empty);
                    if (poll.right != null)
                        queue.offer(poll.right);
                    else
                        queue.offer(empty);
                }
                if (!existElement)
                    break;
            }

            return sb.deleteCharAt(0).toString();
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            if (data == null)
                return null;
            if (data.isEmpty())
                return null;
            String[] split = data.split(":");

            TreeNode root = new TreeNode(Integer.parseInt(split[0]));
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);

            int row = 1;
            int start = 1;
            int i = 1;
            while (i < split.length) {
                while (i < start + row * 2) {
                    TreeNode poll = queue.poll();
                    if(poll == null)
                        break;
                    poll.left = cnode(split[i++]);
                    poll.right = cnode(split[i++]);
                    queue.offer(poll.left);
                    queue.offer(poll.right);
                }
                if(queue.isEmpty())
                    break;
                start = i;
                row++;
            }
            return root;
        }

        private TreeNode cnode(String str) {
            if (str.equals("n"))
                return null;
            return new TreeNode(Integer.parseInt(str));
        }
    }


//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
//        Q449 q449 = new Q449();
        Q449.Codec codec = new Q449().new Codec();
        // build test data
        TreeNode root = new TreeNode(1);
        root.right = new TreeNode(2);

        String serialize = codec.serialize(root);
        System.out.println(serialize);

        TreeNode deserialize = codec.deserialize(serialize);
        TreeUtils.printBTreeInorder(deserialize);
    }
}