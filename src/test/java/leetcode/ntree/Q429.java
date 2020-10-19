package leetcode.ntree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 429. N叉树的层序遍历
 * 给定一个 N 叉树，返回其节点值的层序遍历。 (即从左到右，逐层遍历)。
 *
 * 说明:
 *
 *     树的深度不会超过 1000。
 *     树的节点总数不会超过 5000。
 *
 **/
public class Q429 {
    public List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> result = new ArrayList<>();
        if(root == null)
            return result;
        helper_recur(result, Collections.singletonList(root));
        return result;
    }

    private void helper_recur(List<List<Integer>> result, List<Node> nodes){
        if(nodes.isEmpty())
            return;
        List<Integer> line = new ArrayList<>();
        List<Node> nextLineNodes = new ArrayList<>();
        for(Node node : nodes){
            line.add(node.val);
            nextLineNodes.addAll(node.children);
        }
        result.add(line);
        helper_recur(result,nextLineNodes);
    }
}
