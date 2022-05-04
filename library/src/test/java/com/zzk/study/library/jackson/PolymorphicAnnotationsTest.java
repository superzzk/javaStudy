package com.zzk.study.library.jackson;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * <ol>
 *     <li>@JsonTypeInfo – indicates details of what type information to include in serialization</li>
 *     <li>@JsonSubTypes – indicates sub-types of the annotated type</li>
 *     <li>@JsonTypeName – defines a logical type name to use for annotated class</li>
 * </ol>
 * */
public class PolymorphicAnnotationsTest {
	@Test
	public void whenSerializingPolymorphic_thenCorrect() throws JsonProcessingException {
		Zoo.Dog dog = new Zoo.Dog("lacy");
		Zoo zoo = new Zoo(dog);

		String result = new ObjectMapper().writeValueAsString(zoo);
		System.out.println(result);
		assertThat(result, containsString("type"));
		assertThat(result, containsString("dog"));
	}

	/**
	 * deserialize json:
	 * {
	 *     "animal":{
	 *         "name":"lacy",
	 *         "type":"cat"
	 *     }
	 * }
	 * */
	@Test
	public void whenDeserializingPolymorphic_thenCorrect() throws IOException {
		String json = "{\"animal\":{\"name\":\"lacy\",\"type\":\"cat\"}}";

		Zoo zoo = new ObjectMapper()
				.readerFor(Zoo.class)
				.readValue(json);

		assertEquals("lacy", zoo.animal.name);
		assertEquals(Zoo.Cat.class, zoo.animal.getClass());
	}

}

class Zoo {
	public Animal animal;

	@JsonTypeInfo(
			use = JsonTypeInfo.Id.NAME,
			include = JsonTypeInfo.As.PROPERTY,
			property = "type")
	@JsonSubTypes({
			@JsonSubTypes.Type(value = Dog.class, name = "dog"),
			@JsonSubTypes.Type(value = Cat.class, name = "cat")
	})
	public static class Animal {
		public String name;

		public Animal(String name) {
			this.name = name;
		}
		public Animal() {
		}
	}

	@JsonTypeName("dog")
	public static class Dog extends Animal {
		public double barkVolume;

		public Dog(String name) {
			super(name);
		}
		public Dog() {
		}
	}

	@JsonTypeName("cat")
	public static class Cat extends Animal {
		boolean likesCream;
		public int lives;

		public Cat(String name) {
			super(name);
		}
		public Cat() {
		}
	}

	public Zoo(Animal animal) {
		this.animal = animal;
	}

	public Zoo() {
	}
}