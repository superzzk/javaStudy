package com.wrox.algorithms.tstrees;
import com.wrox.algorithms.lists.List;

public class TernarySearchTree {
    public static final char WILDCARD = '?';
    private Node _root;
    public void add(CharSequence word) {
        assert word != null : "word can't be null";
        assert word.length() > 0 : "word can't be empty";
        Node node = insert(_root, word, 0);
        if (_root == null) {
            _root = node;
        }
    }
    public boolean contains(CharSequence word) {
        assert word != null : "word can't be null";
        assert word.length() > 0 : "word can't be empty";
        Node node = search(_root, word, 0);
        return node != null && node.isEndOfWord();
    }
    public void patternMatch(CharSequence pattern, List results) {
        assert pattern != null : "pattern can't be null";
        assert pattern.length() > 0 : "pattern can't be empty";
        assert results != null : "results can't be null";
        patternMatch(_root, pattern, 0, results);
    }
    public void prefixSearch(CharSequence prefix, List results) {
        assert prefix != null : "prefix can't be null";
        assert prefix.length() > 0 : "prefix can't be empty";
        inOrderTraversal(search(_root, prefix, 0), results);
    }
    private Node search(Node node, CharSequence word, int index) {
        assert word != null : "word can't be null";
        if (node == null) {
            return null;
        }
        char c = word.charAt(index);
        if (c == node.getChar()) {
            if (index + 1 < word.length()) {
                node = search(node.getChild(), word, index + 1);
            }
        } else if (c < node.getChar()) {
            node = search(node.getSmaller(), word, index);
        } else {
            node = search(node.getLarger(), word, index);
        }
        return node;
    }
    private Node insert(Node node, CharSequence word, int index) {
        assert word != null : "word can't be null";
        char c = word.charAt(index);
        if (node == null) {
            node = new Node(c);
        }
        if (c == node.getChar()) {
            if (index + 1 < word.length()) {
                node.setChild(insert(node.getChild(), word, index + 1));
            } else {
                node.setWord(word.toString());
            }
        } else if (c < node.getChar()) {
            node.setSmaller(insert(node.getSmaller(), word, index));
        } else {
            node.setLarger(insert(node.getLarger(), word, index));
        }
        return node;
    }
    private void patternMatch(Node node, CharSequence pattern, int index, List
            results) {
        assert pattern != null : "pattern can't be null";
        assert results != null : "results can't be null";
        if (node == null) {
            return;
        }
        char c = pattern.charAt(index);
        if (c == WILDCARD || c < node.getChar()) {
            patternMatch(node.getSmaller(), pattern, index, results);
        }
        if (c == WILDCARD || c == node.getChar()) {
            if (index + 1 < pattern.length()) {
                patternMatch(node.getChild(), pattern, index + 1, results);
            } else if (node.isEndOfWord()) {
                results.add(node.getWord());
            }
        }
        if (c == WILDCARD || c > node.getChar()) {
            patternMatch(node.getLarger(), pattern, index, results);
        }
    }
    private void inOrderTraversal(Node node, List results) {
        assert results != null : "results can't be null";
        if (node == null) {
            return;
        }
        inOrderTraversal(node.getSmaller(), results);
        if (node.isEndOfWord()) {
            results.add(node.getWord());
        }
        inOrderTraversal(node.getChild(), results);
        inOrderTraversal(node.getLarger(), results);
    }
    private static final class Node {
        private final char _c;
        private Node _smaller;
        private Node _larger;
        private Node _child;
        private String _word;
        public Node(char c) {
            _c = c;
        }
        public char getChar() {
            return _c;
        }
        public Node getSmaller() {
            return _smaller;
        }
        public void setSmaller(Node smaller) {
            _smaller = smaller;
        }
        public Node getLarger() {
            return _larger;
        }
        public void setLarger(Node larger) {
            _larger = larger;
        }
        public Node getChild() {
            return _child;
        }
        public void setChild(Node child) {
            _child = child;
        }
        public String getWord() {
            return _word;
        }
        public void setWord(String word) {
            _word = word;
        }
        public boolean isEndOfWord() {
            return getWord() != null;
        }
    }
}