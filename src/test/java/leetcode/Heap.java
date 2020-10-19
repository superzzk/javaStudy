package leetcode;

/**
 * 堆的构建,替换
 **/
public class Heap {
    private Node[] heap;

    public Heap(Node[] heap) {
        this.heap = heap;
        buildHeap(heap.length / 2);
    }

    /**
     *最小堆的构建,重要规则
     * left = 2 * n
     * right = 2 * n + 1
     * 从最后一个非叶子节点开始，逐步向前开始构建
     * @param node 需要比对的节点，第一次是最后一个非叶子节点
     */
    public void buildHeap(int nodeOffset) {
        // 递归终止条件
        if (nodeOffset < 1) {
            return;
        }

        Node node = heap[nodeOffset - 1];

        // 左子树
        Node left = heap[nodeOffset * 2 - 1];
        Node right = null;
        if (nodeOffset * 2 + 1 <= heap.length) {
            right = heap[nodeOffset * 2];
        }

        // 当前节点与左子树和右子树比较，需要交换则进入判断
        if (left.compareTo(node) == -1 || (right != null && right.compareTo(node) == -1)) {
            if (left.compareTo(right) == -1) {
                // 左子树最小，和父节点进行交换
                swap(nodeOffset - 1, nodeOffset * 2 - 1);
                // 左子树的值变大，会影响该节点以下的树的顺序，需要重新调换顺序，类似插入
                replace(node, nodeOffset * 2, heap.length);
            } else {
                // 右子树最小，和父节点调换顺序
                swap(nodeOffset - 1, nodeOffset * 2 + 1 - 1);
                // 右子树的值变大，会影响该节点以下的树的顺序，需要重新调整顺序，类似插入
                replace(node, nodeOffset * 2 + 1, heap.length);
            }
        }

        // 该节点和左右子节点比较完毕，指针向前移动一位
        buildHeap(nodeOffset - 1);
    }

    public void swap(int i, int j) {
        Node temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    /**
     * 替换指定位置的元素，需要向下遍历
     * @ param node 新元素
     * @ param pos  需要替换的位置
     */
    public void replace(Node node, int pos, int endPos) {
        heap[pos - 1] = node;
        // 递归结束条件，直到该子树的叶子节点结束
        if (pos * 2 > endPos) {
            return;
        }

        Node left = heap[pos * 2 - 1];
        Node right = null;
        if (pos * 2 + 1 <= endPos) {
            right = heap[pos * 2];
        }


        if (left.compareTo(node) == -1 || (right != null && right.compareTo(node) == -1)) {
            if (left.compareTo(right) == -1) {
                // 左子树最小
                swap(pos - 1, pos * 2 - 1);
                // 左子树发生变化
                replace(node, pos * 2, endPos);
            } else {
                swap(pos - 1, pos * 2);
                // 右子树发生变化
                replace(node, pos * 2 + 1, endPos);
            }
        }

    }

    public Node getFirstNode() {
        return heap[0];
    }

    public Node getNode(int i) {
        return heap[i];
    }

    public void iterator() {
        for (int i = 0; i < heap.length; i++) {
            System.out.print(heap[i].toString() + ", ");
        }
        System.out.println();
    }


    /**
     * 思路：由于是小顶堆，所以，根节点是最大值，每次取出来，放到尾部，下一个最大值就又冒到顶部了，依次遍历n变即可
     * 时间复杂度：O(nlgn)
     * 空间复杂度：O(n)
     */
    public Node[] sort(int n) {
        if (n < 1) {
            return heap;
        }
        // 交换根节点和最后一个节点
        swap(0, n - 1);
        // 根节点变化了，向下遍历
        replace(heap[0], 1, --n);
        sort(n);
        return heap;
    }
}

class Node {
    // 数量
    private int count;
    // 单词的值
    private String value;
    public Node(String value, int count) {
        this.count = count;
        this.value = value;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getValue() {
        return this.value;
    }

    public int hashCode() {
        return value.hashCode();
    }

    public boolean equals(Node node) {
        return hashCode() == node.hashCode();
    }

    public int compareTo(Node node) {
        // 堆是一个完全二叉树，右子节点可能不存在
        if (node == null) {
            return -1;
        }


        if (this.count < node.getCount()) {
            return -1;
        } else if (this.count == node.getCount()) {
            if (this.value.compareTo(node.getValue()) < 0) {
                // 数字是由大到小排序，相同的数字字符串是从小到大
                return 1;
            } else {
                return -1;
            }
        } else {
            return 1;
        }
    }
    public String toString() {
        return value + "-" + count;
    }
}
