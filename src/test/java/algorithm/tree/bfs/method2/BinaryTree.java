package algorithm.tree.bfs.method2;

/**
printLevelorder(tree)
		1) Create an empty queue q
		2) temp_node = root //start from root
		3) Loop while temp_node is not NULL
		a) print temp_node->data.
		b) Enqueue temp_node’s children (first left then right children) to q
		c) Dequeue a node from q and assign it’s value to temp_node
*/
import java.util.Queue;
import java.util.LinkedList;

/* Class to represent Tree node */
class Node {
	int data;
	Node left, right;

	public Node(int item) {
		data = item;
		left = null;
		right = null;
	}
}

/* Class to print Level Order Traversal */
public class BinaryTree {

	Node root;

	/* Given a binary tree. Print its nodes in level order
	 using array for implementing queue  */
	void printLevelOrder()
	{
		Queue<Node> queue = new LinkedList<Node>();
		queue.add(root);
		while (!queue.isEmpty())
		{
			Node tempNode = queue.poll();
			System.out.print(tempNode.data + " ");

			/*Enqueue left child */
			if (tempNode.left != null) {
				queue.add(tempNode.left);
			}

			/*Enqueue right child */
			if (tempNode.right != null) {
				queue.add(tempNode.right);
			}
		}
	}

	public static void main(String args[])
	{
        /* creating a binary tree and entering
         the nodes */
		BinaryTree tree_level = new BinaryTree();
		tree_level.root = new Node(1);
		tree_level.root.left = new Node(2);
		tree_level.root.right = new Node(3);
		tree_level.root.left.left = new Node(4);
		tree_level.root.left.right = new Node(5);

		System.out.println("Level order traversal of binary tree is - ");
		tree_level.printLevelOrder();
	}
}