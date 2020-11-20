package util;

import java.util.Spliterator;
import java.util.concurrent.CountedCompleter;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Consumer;

/**
 * @author lichaobao
 * @date 2019/5/20
 * @QQ 1527563274
 */
public class SpliteratorExample {

	public static void main(String[] args){
		Object[] a = new  Object[] {1,2,3,4,5,6,7,8,9};
		Object[] tag = new Object[]{"a","b","c","d","e","f","g","h","i"};
		TaggedArray taggedArray = new TaggedArray(a,tag);
		parEach(taggedArray,value ->{
			System.out.print(value+",");
		});
	}

	static <T> void parEach(TaggedArray<T> a,Consumer<T> action){
		Spliterator<T> s = a.spliterator();
		long targetBatchSize = s.estimateSize() / (ForkJoinPool.getCommonPoolParallelism() * 8);
		new ParEach(null, s, action, targetBatchSize).invoke();
	}

	static class ParEach<T> extends CountedCompleter<Void>{
		final Spliterator<T> spliterator;
		final Consumer<T> action;
		final long targetBatchSize;

		ParEach(ParEach<T> parent,Spliterator<T> spliterator,Consumer<T> action,long targetBatchSize){
			super(parent);
			this.spliterator = spliterator; this.action = action;
			this.targetBatchSize = targetBatchSize;
		}

		@Override
		public void compute() {
			Spliterator<T> sub;
			while (spliterator.estimateSize() > targetBatchSize &&
					(sub = spliterator.trySplit()) != null) {
				addToPendingCount(1);
				new ParEach<>(this, sub, action, targetBatchSize).fork();
			}
			spliterator.forEachRemaining(action);
			propagateCompletion();
		}
	}

	static class TaggedArray<T>{
		private final Object[] elements;

		TaggedArray(T[] data,Object[] tags){
			int size = data.length;
			if(tags.length!=size) throw new IllegalArgumentException();
			this.elements = new Object[2*size];
			for(int i =0,j=0;i<size;++i){
				elements[j++] = data[i];
				elements[j++] = tags[i];
			}
		}
		public Spliterator<T> spliterator(){
			return new TaggedArraySpliterator<>(elements,0,elements.length);
		}

		static class TaggedArraySpliterator<T> implements Spliterator<T>{
			private final Object[] array;
			private int origin;
			private final int fence;

			public TaggedArraySpliterator(Object[] array, int origin, int fence) {
				this.array = array;
				this.origin = origin;
				this.fence = fence;
			}

			@Override
			public void forEachRemaining(Consumer<? super T> action) {
				for (; origin < fence; origin += 2)
					action.accept((T) array[origin]);
			}

			@Override
			public boolean tryAdvance(Consumer<? super T> action) {
				if(origin <fence){
					action.accept((T)array[origin]);
					origin+=2;
					return true;
				}else
					return false;
			}

			@Override
			public Spliterator<T> trySplit() {
				int lo = origin; // divide range in half
				int mid = ((lo + fence) >>> 1) & ~1; // force midpoint to be even
				if (lo < mid) { // split out left half
					origin = mid; // reset this Spliterator's origin
					return new TaggedArraySpliterator<>(array, lo, mid);
				}else       // too small to split
					return null;
			}
			@Override
			public long estimateSize() {
				return (long)((fence-origin)/2);
			}

			@Override
			public int characteristics() {
				return ORDERED|SIZED|IMMUTABLE|SUBSIZED;
			}
		}
	}
}

