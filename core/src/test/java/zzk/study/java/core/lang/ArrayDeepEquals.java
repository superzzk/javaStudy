package zzk.study.java.core.lang;

import org.junit.Test;

import java.util.Arrays;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class ArrayDeepEquals {
	public static void main(String[] args) {
		String[] string1 = new String[]{"first","second"};
		String[] string2 = new String[]{"first","second"};

		Integer[] num1 = new Integer[]{1};
		Integer[] num2 = new Integer[]{2};

		Object[] object1 = new Object[]{"first", "second", new String[]{"third"}};
		Object[] object2 = new Object[]{"first", "second", new String[]{"third"}};

		/* Same Behavior when there are no nested array type child elements */
		System.out.println("Compare string1 and string2 Using HashCode and Equals: " + Arrays.equals(string1, string2));
		System.out.println("Compare string1 and string2 Using DeepHashCode and DeepEquals: " + Arrays.deepEquals(string1, string2));

		// Marking two arrays as null
		num1 = null;
		num2 = null;
		/* Same Behavior when arrays are null */
		System.out.println("Compare (null arrays) num1 and num2 Using HashCode and Equals: " + Arrays.equals(num1, num2));
		System.out.println("Compare (null arrays) num1 and num2 Using DeepHashCode and DeepEquals: " + Arrays.deepEquals(num1, num2));

		/* With nested objects, Behavior is totally different  */
		System.out.println("Compare object1 and object2 Using HashCode and Equals: " + Arrays.equals(object1, object2));
		System.out.println("Compare object1 and object2 Using DeepHashCode and DeepEquals: " + Arrays.deepEquals(object1, object2));

	}

	@Test
	public void givenSameContents_whenEquals_thenTrue() {
		final String[] planes1 = new String[] { "A320", "B738", "A321", "A319", "B77W", "B737", "A333", "A332" };
		final String[] planes2 = new String[] { "A320", "B738", "A321", "A319", "B77W", "B737", "A333", "A332" };

		assertThat(Arrays.equals(planes1, planes2)).isTrue();
	}

	@Test
	public void givenSameContentsDifferentOrder_whenEquals_thenFalse() {
		final String[] planes1 = new String[] { "A320", "B738", "A321", "A319", "B77W", "B737", "A333", "A332" };
		final String[] planes2 = new String[] { "B738", "A320", "A321", "A319", "B77W", "B737", "A333", "A332" };

		assertThat(Arrays.equals(planes1, planes2)).isFalse();
	}

	@Test
	public void givenSameContents_whenEquals_thenFalse() {
		final Plane[][] planes1 = new Plane[][] { new Plane[] { new Plane("Plane 1", "A320") },
				new Plane[] { new Plane("Plane 2", "B738") } };
		final Plane[][] planes2 = new Plane[][] { new Plane[] { new Plane("Plane 1", "A320") },
				new Plane[] { new Plane("Plane 2", "B738") } };

		assertThat(Arrays.equals(planes1, planes2)).isFalse();
	}

	@Test
	public void givenSameContents_whenDeepEquals_thenTrue() {
		final Plane[][] planes1 = new Plane[][] { new Plane[] { new Plane("Plane 1", "A320") },
				new Plane[] { new Plane("Plane 2", "B738") } };
		final Plane[][] planes2 = new Plane[][] { new Plane[] { new Plane("Plane 1", "A320") },
				new Plane[] { new Plane("Plane 2", "B738") } };

		assertThat(Arrays.deepEquals(planes1, planes2)).isTrue();
	}

	@Test
	public void givenSameContentsWithDifferentOrder_whenDeepEquals_thenFalse() {
		final Plane[][] planes1 = new Plane[][] { new Plane[] { new Plane("Plane 1", "A320") },
				new Plane[] { new Plane("Plane 2", "B738") } };
		final Plane[][] planes2 = new Plane[][] { new Plane[] { new Plane("Plane 2", "B738") },
				new Plane[] { new Plane("Plane 1", "A320") } };

		assertThat(Arrays.deepEquals(planes1, planes2)).isFalse();
	}

	public static class Plane {

		private final String name;

		private final String model;

		public Plane(String name, String model) {

			this.name = name;
			this.model = model;
		}

		public String getName() {
			return name;
		}

		public String getModel() {
			return model;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			Plane plane = (Plane) o;
			return Objects.equals(name, plane.name) && Objects.equals(model, plane.model);
		}

		@Override
		public int hashCode() {
			return Objects.hash(name, model);
		}
	}
}
