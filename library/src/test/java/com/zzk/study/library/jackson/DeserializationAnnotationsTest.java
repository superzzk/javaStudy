package com.zzk.study.library.jackson;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzk.study.library.jackson.deserializer.EventWithDeserializer;
import org.junit.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * <ol>
 * <li> @JsonCreator </li>
 * <li> @JacksonInject indicates that a property will get its value from the injection and not from the JSON data </li>
 * <li> @JsonAnySetter </li>
 * <li> @JsonSetter is an alternative to @JsonProperty – that marks the method as a setter method. </li>
 * <li> @JsonDeserialize </li>
 * <li> @JsonAlias defines one or more alternative names for a property during deserialization. </li>
 * </ol>
 * */
public class DeserializationAnnotationsTest {

	// @JsonCreator
	@Test
	public void whenDeserializingUsingJsonCreator_thenCorrect() throws IOException {

		String json = "{\"id\":1,\"theName\":\"My bean\"}";
		System.out.println(json);

		BeanWithCreator bean = new ObjectMapper()
				.readerFor(BeanWithCreator.class)
				.readValue(json);
		assertEquals("My bean", bean.name);
	}

	@Test
	public void whenDeserializingUsingJsonInject_thenCorrect() throws IOException {
		String json = "{\"name\":\"My bean\"}";
		System.out.println(json);

		InjectableValues inject = new InjectableValues.Std().addValue(int.class, 1);
		BeanWithInject bean = new ObjectMapper().reader(inject)
				.forType(BeanWithInject.class)
				.readValue(json);

		assertEquals("My bean", bean.name);
		assertEquals(1, bean.id);
	}

	@Test
	public void whenDeserializingUsingJsonAnySetter_thenCorrect() throws IOException {
		String json = "{\"name\":\"My bean\",\"attr2\":\"val2\",\"attr1\":\"val1\"}";

		ExtendableBean bean = new ObjectMapper()
				.readerFor(ExtendableBean.class)
				.readValue(json);

		assertEquals("My bean", bean.name);
		assertEquals("val2", bean.getProperties().get("attr2"));
	}

	@Test
	public void whenDeserializingUsingJsonSetter_thenCorrect() throws IOException {
		String json = "{\"id\":1,\"name\":\"My bean\"}";

		MyBean bean = new ObjectMapper()
				.readerFor(MyBean.class)
				.readValue(json);
		assertEquals("My bean", bean.getTheName());
	}

	@Test
	public void whenDeserializingUsingJsonDeserialize_thenCorrect() throws IOException {

		String json = "{\"name\":\"party\",\"eventDate\":\"20-12-2014 02:30:00\"}";

		SimpleDateFormat df
				= new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		EventWithDeserializer event = new ObjectMapper()
				.readerFor(EventWithDeserializer.class)
				.readValue(json);

		assertEquals(
				"20-12-2014 02:30:00", df.format(event.eventDate));
	}

	// @JsonAlias({ "fName", "f_name" })
	@Test
	public void whenDeserializingUsingJsonAlias_thenCorrect() throws IOException {
		String json = "{\"fName\": \"John\", \"lastName\": \"Green\"}";
		AliasBean aliasBean = new ObjectMapper().readerFor(AliasBean.class).readValue(json);
		assertEquals("John", aliasBean.getFirstName());
	}

	static class BeanWithCreator {
		public int id;
		public String name;

		@JsonCreator
		public BeanWithCreator(
				@JsonProperty("id") int id,
				@JsonProperty("theName") String name) {
			this.id = id;
			this.name = name;
		}
	}

	static class BeanWithInject {
		@JacksonInject
		public int id;

		public String name;
	}

	public static class ExtendableBean {
		public String name;
		private Map<String, String> properties;

		@JsonAnySetter
		public void add(String key, String value) {
			properties.put(key, value);
		}

		public Map<String, String> getProperties() {
			return properties;
		}
	}

	class MyBean {
		public int id;
		private String name;

		@JsonSetter("name")
		public void setTheName(String name) {
			this.name = name;
		}

		public String getTheName() {
			return name;
		}
	}

	class AliasBean {
		@JsonAlias({ "fName", "f_name" })
		public String firstName;
		public String lastName;

		public String getFirstName() {
			return firstName;
		}
	}
}