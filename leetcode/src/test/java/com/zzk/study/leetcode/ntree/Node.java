package com.zzk.study.leetcode.ntree;


import java.util.List;

/**
 * @author zhangzhongkun
 * @since 2019-10-19 10:27
 **/
public class Node {
    public int val;
    public List<Node> children;

    public Node() {}

    public Node(int _val,List<Node> _children) {
        val = _val;
        children = _children;
    }
}
