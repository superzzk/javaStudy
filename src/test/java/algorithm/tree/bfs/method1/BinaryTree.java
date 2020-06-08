package algorithm.tree.bfs.method1;

/**
 * /*Function to print level order traversal of tree
 * printLevelorder(tree)
 * for d = 1 to height(tree)
 * printGivenLevel(tree, d);
 * <p>
 * /*Function to print all nodes at a given level
 * printGivenLevel(tree, level)
 * if tree is NULL then return;
 * if level is 1, then
 * print(tree->data);
 * else if level greater than 1, then
 * printGivenLevel(tree->left, level-1);
 * printGivenLevel(tree->right, level-1);
 */
public class BinaryTree {
	// Root of the Binary Tree
	Node root;

	public BinaryTree() {
		root = null;
	}

	/* function to print level order traversal of tree*/
	void printLevelOrder() {
		int h = height(root);
		int i;
		for (i = 1; i <= h; i++)
			printGivenLevel(root, i);
	}

	/* Compute the "height" of a tree -- the number of
	nodes along the longest path from the root node
	down to the farthest leaf node.*/
	int height(Node root) {
		if (root == null)
			return 0;
		else {
			/* compute  height of each subtree */
			int lheight = height(root.left);
			int rheight = height(root.right);

			/* use the larger one */
			if (lheight > rheight)
				return (lheight + 1);
			else return (rheight + 1);
		}
	}

	/* Print nodes at the given level */
	void printGivenLevel(Node root, int level) {
		if (root == null)
			return;
		if (level == 1)
			System.out.print(root.data + " ");
		else if (level > 1) {
			printGivenLevel(root.left, level - 1);
			printGivenLevel(root.right, level - 1);
		}
	}

	/* Driver program to test above functions */
	public static void main(String args[]) {
		BinaryTree tree = new BinaryTree();
		tree.root = new Node(1);
		tree.root.left = new Node(2);
		tree.root.right = new Node(3);
		tree.root.left.left = new Node(4);
		tree.root.left.right = new Node(5);

		System.out.println("Level order traversal of binary tree is ");
		tree.printLevelOrder();
	}
}

class Node {
	int data;
	Node left, right;

	public Node(int item) {
		data = item;
		left = right = null;
	}
}