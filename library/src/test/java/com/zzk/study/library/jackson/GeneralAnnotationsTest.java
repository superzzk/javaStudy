package com.zzk.study.library.jackson;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.springframework.http.converter.json.GsonBuilderUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * <ol>
 * <li> @JsonProperty annotation to indicate the property name in JSON.</li>
 * <li> @JsonFormat annotation specifies a format when serializing Date/Time values.</li>
 * <li> @JsonUnwrapped defines values that should be unwrapped/flattened when serialized/deserialized.</li>
 * <li> @JsonView indicates the View in which the property will be included for serialization/deserialization.</li>
 * <li> @JsonManagedReference and @JsonBackReference annotations can handle parent/child relationships and work around loops.</li>
 * <li> @JsonIdentityInfo indicates that Object Identity should be used when serializing/deserializing values
 * – for instance, to deal with infinite recursion type of problems.</li>
 * <li> @JsonFilter annotation specifies a filter to use during serialization.</li>
 * <li>@JsonRawValue</li>
 * </ol>
 */
public class GeneralAnnotationsTest {
	// @JsonRawValue
	@Test
	public void jsonRawValue() throws JsonProcessingException {

		MyBean bean = new MyBean(1, "My bean");
		String a = new ObjectMapper().writeValueAsString(bean);
		System.out.println(a);
	}

	@Data
	public static class BeanWithJsonRawValue {
		@JsonRawValue
		String rawValue;


	}


	// @JsonProperty("name")
	@Test

	public void whenUsingJsonProperty_thenCorrect() throws IOException {
		MyBean bean = new MyBean(1, "My bean");

		String result = new ObjectMapper().writeValueAsString(bean);
		System.out.println(result);

		assertThat(result, containsString("My bean"));
		assertThat(result, containsString("1"));

		MyBean resultBean = new ObjectMapper()
				.readerFor(MyBean.class)
				.readValue(result);
		assertEquals("My bean", resultBean.getTheName());
	}

	// @JsonFormat
	@Test
	public void whenSerializingUsingJsonFormat_thenCorrect() throws IOException, ParseException {
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("UTC"));

		String toParse = "20-12-2014 02:30:00";
		Date date = df.parse(toParse);
		EventWithFormat event = new EventWithFormat("party", date);

		String result = new ObjectMapper().writeValueAsString(event);
		assertThat(result, containsString(toParse));

		EventWithFormat e = new ObjectMapper().readerFor(EventWithFormat.class).readValue(result);
		assertEquals(e.eventDate, date);
	}

	// @JsonUnwrapped
	@Test
	public void whenSerializingUsingJsonUnwrapped_thenCorrect() throws JsonProcessingException, ParseException {
		UnwrappedUser.Name name = new UnwrappedUser.Name("John", "Doe");
		UnwrappedUser user = new UnwrappedUser(1, name);

		String result = new ObjectMapper().writeValueAsString(user);

		assertThat(result, containsString("John"));
		assertThat(result, not(containsString("name")));
	}

	// @JsonView(Views.Public.class)
	@Test
	public void whenSerializingUsingJsonView_thenCorrect() throws JsonProcessingException {
		Item item = new Item(2, "book", "John");

		String result = new ObjectMapper()
				.writerWithView(Views.Public.class)
				.writeValueAsString(item);

		assertThat(result, containsString("book"));
		assertThat(result, containsString("2"));
		assertThat(result, not(containsString("John")));
	}

	// @JsonBackReference and @JsonIgnoreType
	@Test
	public void whenSerializingUsingJacksonReferenceAnnotation_thenCorrect() throws JsonProcessingException {
		UserWithRef user = new UserWithRef(1, "John");
		ItemWithRef item = new ItemWithRef(2, "book", user);
		user.addItem(item);

		String result = new ObjectMapper().writeValueAsString(item);
		System.out.println(result);

		assertThat(result, containsString("book"));
		assertThat(result, containsString("John"));
		assertThat(result, not(containsString("userItems")));

		result = new ObjectMapper().writeValueAsString(user);
		System.out.println(result);

		assertThat(result, not(containsString("userItems")));
	}

	// @JsonIdentityInfo
	@Test
	public void whenSerializingUsingJsonIdentityInfo_thenCorrect() throws JsonProcessingException {
		UserWithIdentity user = new UserWithIdentity(1, "John");
		ItemWithIdentity item = new ItemWithIdentity(2, "book", user);
		user.addItem(item);

		String result = new ObjectMapper().writeValueAsString(item);
		System.out.println(result);

		assertThat(result, containsString("book"));
		assertThat(result, containsString("John"));
		assertThat(result, containsString("userItems"));

		result = new ObjectMapper().writeValueAsString(user);
		System.out.println(result);
	}

	// @JsonFilter("myFilter")
	@Test
	public void whenSerializingUsingJsonFilter_thenCorrect() throws JsonProcessingException {
		BeanWithFilter bean = new BeanWithFilter(1, "My bean");

		FilterProvider filters = new SimpleFilterProvider()
				.addFilter("myFilter", SimpleBeanPropertyFilter.filterOutAllExcept("name"));

		String result = new ObjectMapper()
				.writer(filters)
				.writeValueAsString(bean);

		assertThat(result, containsString("My bean"));
		assertThat(result, not(containsString("id")));
	}

	static class MyBean {
		public int id;
		private String name;

		@JsonProperty("name")
		public void setTheName(String name) {
			this.name = name;
		}

		@JsonProperty("name")
		public String getTheName() {
			return name;
		}

		public MyBean() {
		}

		public MyBean(int id, String name) {
			this.id = id;
			this.name = name;
		}


	}

	public static class EventWithFormat {
		public String name;

		@JsonFormat(
				shape = JsonFormat.Shape.STRING,
				pattern = "dd-MM-yyyy hh:mm:ss")
		public Date eventDate;

		public EventWithFormat(String name, Date eventDate) {
			this.name = name;
			this.eventDate = eventDate;
		}

		public EventWithFormat() {
		}
	}

	@NoArgsConstructor
	public static class UnwrappedUser {
		public int id;

		@JsonUnwrapped
		public Name name;

		@NoArgsConstructor
		public static class Name {
			public String firstName;
			public String lastName;

			public Name(String firstName, String lastName) {
				this.firstName = firstName;
				this.lastName = lastName;
			}
		}

		public UnwrappedUser(int id, Name name) {
			this.id = id;
			this.name = name;
		}
	}

	public static class Views {
		public static class Public {
		}

		public static class Internal extends Public {
		}
	}

	public static class Item {
		@JsonView(Views.Public.class)
		public int id;

		@JsonView(Views.Public.class)
		public String itemName;

		@JsonView(Views.Internal.class)
		public String ownerName;

		public Item(int id, String itemName, String ownerName) {
			this.id = id;
			this.itemName = itemName;
			this.ownerName = ownerName;
		}
	}

	public static class ItemWithRef {
		public int id;
		public String itemName;

		@JsonManagedReference
		public UserWithRef owner;

		public ItemWithRef(int id, String itemName, UserWithRef owner) {
			this.id = id;
			this.itemName = itemName;
			this.owner = owner;
		}
	}

	public static class UserWithRef {
		public int id;
		public String name;

		@JsonBackReference
		public List<ItemWithRef> userItems;

		public UserWithRef(int id, String name) {
			this.id = id;
			this.name = name;
		}

		public void addItem(ItemWithRef item) {
			if (userItems == null)
				userItems = new ArrayList<>();
			userItems.add(item);
		}
	}

	@JsonIdentityInfo(
			generator = ObjectIdGenerators.PropertyGenerator.class,
			property = "id")
	public static class ItemWithIdentity {
		public int id;
		public String itemName;
		public UserWithIdentity owner;

		public ItemWithIdentity(int id, String itemName, UserWithIdentity owner) {
			this.id = id;
			this.itemName = itemName;
			this.owner = owner;
		}
	}

	@JsonIdentityInfo(
			generator = ObjectIdGenerators.PropertyGenerator.class,
			property = "id")
	public class UserWithIdentity {
		public int id;
		public String name;
		public List<ItemWithIdentity> userItems;

		public UserWithIdentity(int id, String name) {
			this.id = id;
			this.name = name;
		}

		public void addItem(ItemWithIdentity item) {
			if (userItems == null)
				userItems = new ArrayList<>();
			userItems.add(item);
		}
	}

	@JsonFilter("myFilter")
	public static class BeanWithFilter {
		public int id;
		public String name;

		public BeanWithFilter(int id, String name) {
			this.id = id;
			this.name = name;
		}
	}
}

