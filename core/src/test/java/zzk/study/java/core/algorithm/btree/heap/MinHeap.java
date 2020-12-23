package zzk.study.java.core.algorithm.btree.heap;

public class MinHeap {
	int[] harr; // pointer to array of elements in heap
	int capacity; // maximum possible size of min heap
	int heap_size; // Current number of elements in min heap

	public MinHeap(int cap) {
		heap_size = 0;
		capacity = cap;
		harr = new int[cap];
	}

	// Driver program to test above functions
	public static void main(String[] args) {
		MinHeap h = new MinHeap(11);
		h.insertKey(3);
		h.insertKey(2);
		h.deleteKey(1);
		h.insertKey(15);
		h.insertKey(5);
		h.insertKey(4);
		h.insertKey(45);
		System.out.print(h.extractMin() + " " + h.getMin() + " ");
		h.decreaseKey(2, 1);
		System.out.println(h.getMin());
	}

	// A recursive method to heapify a subtree with the root at given index
	// This method assumes that the subtrees are already heapified
	void minHeapify(int i) {
		int l = left(i);
		int r = right(i);
		int smallest = i;
		if (l < heap_size && harr[l] < harr[i])
			smallest = l;
		if (r < heap_size && harr[r] < harr[smallest])
			smallest = r;
		if (smallest != i) {
			swap(harr, i ,smallest);
			minHeapify(smallest);
		}
	}

	// Method to remove minimum element (or root) from min heap
	int extractMin() {
		if (heap_size <= 0)
			return Integer.MAX_VALUE;
		if (heap_size == 1) {
			heap_size--;
			return harr[0];
		}

		// Store the minimum value, and remove it from heap
		int root = harr[0];
		harr[0] = harr[heap_size - 1];
		heap_size--;
		minHeapify(0);

		return root;
	}

	// Decreases key value of key at index i to new_val
	// Decreases value of key at index 'i' to new_val.  It is assumed that
	// new_val is smaller than harr[i].
	void decreaseKey(int i, int new_val) {
		harr[i] = new_val;
		while (i != 0 && harr[parent(i)] > harr[i]) {
			swap(harr, i, parent(i));
			i = parent(i);
		}
	}

	// Returns the minimum key (key at root) from min heap
	int getMin() {
		return harr[0];
	}

	// This function deletes key at index i. It first reduced value to minus
	// infinite, then calls extractMin()
	void deleteKey(int i) {
		decreaseKey(i, Integer.MIN_VALUE);
		extractMin();
	}

	// Inserts a new key 'k'
	void insertKey(int k) {
		if (heap_size == capacity) {
			System.out.println("\nOverflow: Could not insertKey\n");
			return;
		}

		// First insert the new key at the end
		heap_size++;
		int i = heap_size - 1;
		harr[i] = k;

		// Fix the min heap property if it is violated
		while (i != 0 && harr[parent(i)] > harr[i]) {
			swap(harr, i, parent(i) );
			i = parent(i);
		}
	}

	// A utility function to swap two elements
	void swap(int[] data, int x, int y) {
		int temp = data[x];
	    data[x] = data[y];
	    data[y] = temp;
	}

	int parent(int i) {
		return (i - 1) / 2;
	}

	int left(int i) {
		return (2 * i + 1);
	}

	int right(int i) {
		return (2 * i + 2);
	}
}
