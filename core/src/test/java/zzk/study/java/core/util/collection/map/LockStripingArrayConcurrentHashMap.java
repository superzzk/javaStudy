package zzk.study.java.core.util.collection.map;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 一个简单的concurrent hash map实现，使用lock
 * block thread access only to the same bucket where bucket array index = key.hashCode() % array.length
 * 不支持 resize
 *
 * https://github.com/kuptservol/how-it-works
 */
public class LockStripingArrayConcurrentHashMap<K, V> extends BaseMap<K, V> implements Map<K, V> {

	private final AtomicInteger count = new AtomicInteger(0);
	private final Node<K, V>[] buckets;
	private final Object[] locks;

	@SuppressWarnings({"rawtypes", "unchecked"})
	public LockStripingArrayConcurrentHashMap(int capacity) {
		locks = new Object[capacity];
		for (int i = 0; i < locks.length; i++) {
			locks[i] = new Object();
		}

		buckets = (Node<K, V>[]) new Node[capacity];
	}

	@Override
	public int size() {
		return count.get();
	}

	@Override
	public V get(Object key) {
		if (key == null) throw new IllegalArgumentException();
		int hash = hash(key);
		synchronized (getLockFor(hash)) {
			Node<K, V> node = buckets[getBucketIndex(hash)];

			while (node != null) {
				if (isKeyEquals(key, hash, node)) {
					return node.value;
				}

				node = node.next;
			}

			return null;
		}
	}

	@Override
	public V put(K key, V value) {
		if (key == null || value == null) throw new IllegalArgumentException();
		int hash = hash(key);
		synchronized (getLockFor(hash)) {
			int bucketIndex = getBucketIndex(hash);
			Node<K, V> node = buckets[bucketIndex];

			if (node == null) {
				buckets[bucketIndex] = new Node<>(hash, key, value, null);
				count.incrementAndGet();
				return null;
			} else {
				Node<K, V> prevNode = node;
				while (node != null) {
					if (isKeyEquals(key, hash, node)) {
						V prevValue = node.value;
						node.value = value;

						return prevValue;
					}

					prevNode = node;
					node = node.next;
				}

				prevNode.next = new Node<>(hash, key, value, null);
				count.incrementAndGet();
				return null;
			}
		}
	}

	private boolean isKeyEquals(Object key, int hash, Node<K, V> node) {
		return node.hash == hash &&
				node.key == key ||
				(node.key != null && node.key.equals(key));
	}

	private int hash(Object key) {
		return key.hashCode();
	}

	private int getBucketIndex(int hash) {
		return hash % buckets.length;
	}

	private Object getLockFor(int hash) {
		return locks[hash % locks.length];
	}

	private static class Node<K, V> {
		final int hash;
		K key;
		V value;
		Node<K, V> next;

		Node(int hash, K key, V value, Node<K, V> next) {
			this.hash = hash;
			this.key = key;
			this.value = value;
			this.next = next;
		}
	}
}
