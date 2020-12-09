package zzk.study.java.core.util.collection.map;

import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.atomic.LongAdder;

/**
 * 继续改进，增加需求:
 * 1. If we 2 threads that work with different keys(write or read) we do not want any kind of synchronization between them
 * (cause word tearing is impossible in java — and access to two diff array fields is safe)
 * 2. If multiple threads work on the same key(write and read) we do not want cache interleave(more about cache structure)
 * and need safe happens-before guarantees for access between threads otherwise one thread might not see changed value by other thread.
 * But we do not want to block read thread and wait for write thread to complete .
 * 3. We do not want to block multiple readers to the same key if there is no one writer among them.
 *
 * Let’s concetrate on item 2 and 3. Actually we can make map read operation fully lock-free if we can make
 * (1) volatile read array of buckets and then traverse inside bucket by linked list with
 * (2) volatile read of next Node and value of Node itself which.
 *
 * For (2) we can just mark Node’s next and value fields volatile.
 * For (1) there is no such thing as volatile array, even if it is declared as volatile,
 * it not provides volatile semantics when reading or writing elements,
 * concurrent accessing the k-th element of the array requires an explicit volatile read,
 * volatile is only link to array. We can use AtomicReferenceArray for this purpose but it accepts only Object[] arrays.
 * As alternative we can use Unsafe for volatile array read and lock-free write.
 * The same technique is used in AtomicReferenceArray and ConcurrentHashMap.
 *
 * @author Sergey Kuptsov
 * @since 03/04/2017
 * https://github.com/kuptservol/how-it-works
 */
public class LockFreeArrayConcurrentHashMap<K, V> extends BaseMap<K, V> implements Map<K, V> {
	// long adder as counter
	private final LongAdder count = new LongAdder();
	// no lock pool
	private final Node<K, V>[] buckets;

	@SuppressWarnings({"rawtypes", "unchecked"})
	public LockFreeArrayConcurrentHashMap(int capacity) {
		buckets = (Node<K, V>[]) new Node[capacity];
	}

	@Override
	public int size() {
		return count.intValue();
	}

	public V get(Object key) {
		if (key == null) throw new IllegalArgumentException();
		int hash = hash(key);
		Node<K, V> node;

		// volatile read of bucket head at hash index
		if ((node = volatileGetNode(getBucketIndex(hash))) != null) {
			// check first node
			if (isKeyEquals(key, hash, node)) {
				return node.value;
			}

			// walk through the rest to find target node
			while ((node = node.next) != null) {
				if (isKeyEquals(key, hash, node))
					return node.value;
			}
		}

		return null;
	}

	@Override
	public V put(K key, V value) {
		if (key == null || value == null) throw new IllegalArgumentException();
		int hash = hash(key);
		// no resize in this implementation - so the index will not change
		int bucketIndex = getBucketIndex(hash);

		// cas loop trying not to miss
		while (true) {
			Node<K, V> node;
			// if bucket is empty try to set new head with cas
			if ((node = volatileGetNode(bucketIndex)) == null) {
				if (compareAndSwapNode(bucketIndex, null,
						new Node<>(hash, key, value, null))) {
					// if we succeed to set head - then break and return null

					count.increment();
					break;
				}
			} else {
				// head is not null - try to find place to insert or update under lock
				synchronized (node) {
					// check if node have not been changed since we got it
					// otherwise let's go to another loop iteration
					if (volatileGetNode(bucketIndex) == node) {
						V prevValue = null;
						Node<K, V> n = node;
						while (true) {
							if (isKeyEquals(key, hash, n)) {
								prevValue = n.value;
								n.value = value;
								break;
							}

							Node<K, V> prevNode = n;
							if ((n = n.next) == null) {
								prevNode.next = new Node<>(hash, key, value, null);
								count.increment();
								break;
							}
						}

						return prevValue;
					}
				}
			}
		}

		return null;
	}

	@Override
	public V remove(Object key) {
		if (key == null) throw new IllegalArgumentException();
		int hash = hash(key);

		return null;
	}

	private int hash(Object key) {
		return key.hashCode();
	}

	private int getBucketIndex(int hash) {
		return hash % buckets.length;
	}

	private boolean isKeyEquals(Object key, int hash, Node<K, V> node) {
		return node.hash == hash &&
				node.key == key ||
				(node.key != null && node.key.equals(key));
	}

	private static class Node<K, V> {
		final int hash;
		K key;
		// now volatile
		volatile V value;
		// now volatile
		volatile Node<K, V> next;

		Node(int hash, K key, V value, Node<K, V> next) {
			this.hash = hash;
			this.key = key;
			this.value = value;
			this.next = next;
		}
	}

	/* ---------------- Volatile bucket array access -------------- */

	@SuppressWarnings("unchecked")
	// read array header node value by index
	private <K, V> Node<K, V> volatileGetNode(int i) {
		return (Node<K, V>) U.getObjectVolatile(buckets, ((long) i << ASHIFT) + ABASE);
	}

	// cas array header node value by index
	private <K, V> boolean compareAndSwapNode(int i, Node<K, V> expectedNode, Node<K, V> setNode) {
		return U.compareAndSwapObject(buckets, ((long) i << ASHIFT) + ABASE, expectedNode, setNode);
	}

	private static final Unsafe U;
	// Node[] header shift
	private static final long ABASE;
	// Node.class size shift
	private static final int ASHIFT;

	static {
		// get unsafe by reflection - it is illegal to use not in java lib
		try {
			Constructor<Unsafe> unsafeConstructor = Unsafe.class.getDeclaredConstructor();
			unsafeConstructor.setAccessible(true);
			U = unsafeConstructor.newInstance();
		} catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}

		Class<?> ak = Node[].class;

		ABASE = U.arrayBaseOffset(ak);
		int scale = U.arrayIndexScale(ak);
		ASHIFT = 31 - Integer.numberOfLeadingZeros(scale);
	}

	/* ---------------- Volatile bucket array access -------------- */


	public static void main(String[] args) {
		LockFreeArrayConcurrentHashMap<String,String> map = new LockFreeArrayConcurrentHashMap<>(10);
		map.put("a","a");
		map.get("a");
	}
}