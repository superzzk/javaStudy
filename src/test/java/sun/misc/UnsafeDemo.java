package sun.misc;

import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UnsafeDemo {
	@Test
	public void demo_get_an_instance() {
		Unsafe unsafe = get_an_instance();
		System.out.println(unsafe);
	}

	private Unsafe get_an_instance() {
		try {
			Field f = Unsafe.class.getDeclaredField("theUnsafe");
			f.setAccessible(true);
			return (Unsafe) f.get(null);
		} catch (NoSuchFieldException | IllegalAccessException ex) {
			System.out.println(ex.getMessage());
		}
		return null;
	}

	@Test
	public void demo_instantiating_a_class_using_Unsafe() throws InstantiationException {
		//When we initialize that object using the constructor, the getA() method will return a value of 1:
		InitializationOrdering o1 = new InitializationOrdering();
		assertEquals(o1.getA(), 1);

		//we can use the allocateInstance() method using Unsafe.
		// It will only allocate the memory for our class, and will not invoke a constructor:
		InitializationOrdering o3
				= (InitializationOrdering) get_an_instance().allocateInstance(InitializationOrdering.class);

		assertEquals(o3.getA(), 0);
	}
	class InitializationOrdering {
		private long a;

		public InitializationOrdering() {
			this.a = 1;
		}

		public long getA() {
			return this.a;
		}
	}

	@Test
	public void altering_private_fields() throws NoSuchFieldException {
		Unsafe unsafe = get_an_instance();
		SecretHolder secretHolder = new SecretHolder();

		Field f = secretHolder.getClass().getDeclaredField("SECRET_VALUE");
		unsafe.putInt(secretHolder, unsafe.objectFieldOffset(f), 1);

		assertTrue(secretHolder.secretIsDisclosed());
	}
	class SecretHolder {
		private int SECRET_VALUE = 0;

		public boolean secretIsDisclosed() {
			return SECRET_VALUE == 1;
		}
	}

	@Test(expected = IOException.class)
	public void givenUnsafeThrowException_whenThrowCheckedException_thenNotNeedToCatchIt() {
		Unsafe unsafe = get_an_instance();
		unsafe.throwException(new IOException());
	}

	/**
	 * The allocateMemory() method from the Unsafe class gives us the ability to allocate huge objects off the heap,
	 * meaning that this memory will not be seen and taken into account by the GC and the JVM.
	 * */
	@Test
	public void allocate_off_heap_memory() throws NoSuchFieldException, IllegalAccessException {
		long SUPER_SIZE = (long) Integer.MAX_VALUE * 2;
		OffHeapArray array = new OffHeapArray(SUPER_SIZE);

		//put N numbers of byte values into this array and then retrieve those values, summing them up to test if our addressing works correctly
		int sum = 0;
		for (int i = 0; i < 100; i++) {
			array.set((long) Integer.MAX_VALUE + i, (byte) 3);
			sum += array.get((long) Integer.MAX_VALUE + i);
		}

		assertEquals(array.size(), SUPER_SIZE);
		assertEquals(sum, 300);
	}
	class OffHeapArray {
		private final static int BYTE = 1;
		private long size;
		private long address;

		public OffHeapArray(long size) throws NoSuchFieldException, IllegalAccessException {
			this.size = size;
			address = getUnsafe().allocateMemory(size * BYTE);
		}

		private Unsafe getUnsafe() throws IllegalAccessException, NoSuchFieldException {
			Field f = Unsafe.class.getDeclaredField("theUnsafe");
			f.setAccessible(true);
			return (Unsafe) f.get(null);
		}

		//The set() method is taking the index and the given value that will be stored in the array.
		public void set(long i, byte value) throws NoSuchFieldException, IllegalAccessException {
			getUnsafe().putByte(address + i * BYTE, value);
		}

		// The get() method is retrieving the byte value using its index that is an offset from the start address of the array.
		public int get(long idx) throws NoSuchFieldException, IllegalAccessException {
			return getUnsafe().getByte(address + idx * BYTE);
		}

		public long size() {
			return size;
		}

		public void freeMemory() throws NoSuchFieldException, IllegalAccessException {
			getUnsafe().freeMemory(address);
		}
	}

	@Test
	public void demo_compare_and_swap() throws Exception {
		int NUM_OF_THREADS = 1_000;
		int NUM_OF_INCREMENTS = 10_000;
		ExecutorService service = Executors.newFixedThreadPool(NUM_OF_THREADS);
		CASCounter casCounter = new CASCounter();

		IntStream.rangeClosed(0, NUM_OF_THREADS - 1)
				.forEach(i -> service.submit(
						() ->IntStream.rangeClosed(0, NUM_OF_INCREMENTS - 1)
						.forEach(j -> casCounter.increment())
				));
		assertEquals(NUM_OF_INCREMENTS * NUM_OF_THREADS, casCounter.getCounter());
	}
	/**
	 * lock free counter
	 * */
	class CASCounter {
		private Unsafe unsafe;
		// visible to all threads that are writing and reading this value
		private volatile long counter = 0;
		private long offset;

		private Unsafe getUnsafe() throws IllegalAccessException, NoSuchFieldException {
			Field f = Unsafe.class.getDeclaredField("theUnsafe");
			f.setAccessible(true);
			return (Unsafe) f.get(null);
		}

		public CASCounter() throws Exception {
			unsafe = getUnsafe();
			// get the memory address of the offset field
			offset = unsafe.objectFieldOffset(CASCounter.class.getDeclaredField("counter"));
		}

		public void increment() {
			long before = counter;
			// There is no blocking here, which is why this is called a lock-free algorithm.
			while (!unsafe.compareAndSwapLong(this, offset, before, before + 1)) {
				before = counter;
			}
		}

		public long getCounter() {
			return counter;
		}
	}

}
