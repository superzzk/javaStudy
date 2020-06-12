package library.jackson;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.text.ParseException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

/**
 * <ol>
 *   <li>@JsonIgnoreProperties is a class-level annotation that marks a property or a list of properties that Jackson will ignore.
 *   To ignore any unknown properties in JSON input without exception, we can set ignoreUnknown=true of @JsonIgnoreProperties annotation.
 *   </li>
 *   <li>@JsonIgnore annotation is used to mark a property to be ignored at the field level.</li>
 *   <li>@JsonIgnoreType marks all properties of an annotated type to be ignored.</li>
 *   <li>@JsonInclude to exclude properties with empty/null/default values.</li>
 *   <li>@JsonAutoDetect can override the default semantics of which properties are visible and which are not.</li>
 * </ol>
 * */
public class InclusionAnnotationsTest {

	@Test
	public void whenSerializingUsingJsonIgnoreProperties_thenCorrect() throws JsonProcessingException {

		BeanWithIgnore bean = new BeanWithIgnore(1, "My bean");

		String result = new ObjectMapper().writeValueAsString(bean);

		assertThat(result, containsString("My bean"));
		assertThat(result, not(containsString("id")));
	}

	@Test
	public void whenSerializingUsingJsonIgnore_thenCorrect() throws JsonProcessingException {

		PropertyWithIgnore bean = new PropertyWithIgnore(1, "My bean");

		String result = new ObjectMapper().writeValueAsString(bean);

		assertThat(result, containsString("My bean"));
		assertThat(result, not(containsString("id")));
	}

	@Test
	public void whenSerializingUsingJsonIgnoreType_thenCorrect() throws JsonProcessingException, ParseException {

		User.Name name = new User.Name("John", "Doe");
		User user = new User(1, name);

		String result = new ObjectMapper().writeValueAsString(user);
		System.out.println(result);

		assertThat(result, containsString("1"));
		assertThat(result, not(containsString("name")));
		assertThat(result, not(containsString("John")));
	}

	@Test
	public void whenSerializingUsingJsonInclude_thenCorrect() throws JsonProcessingException {
		MyBean bean = new MyBean(1, null);

		String result = new ObjectMapper().writeValueAsString(bean);
		System.out.println(result);
		assertThat(result, containsString("1"));
		assertThat(result, not(containsString("name")));
	}

	@Test
	public void whenSerializingUsingJsonAutoDetect_thenCorrect() throws JsonProcessingException {
		PrivateBean bean = new PrivateBean(1, "My bean");

		String result = new ObjectMapper().writeValueAsString(bean);
		System.out.println(result);
		assertThat(result, containsString("1"));
		assertThat(result, containsString("My bean"));
	}

	@JsonIgnoreProperties({ "id" })
	public class BeanWithIgnore {
		public int id;
		public String name;

		public BeanWithIgnore(int id, String name) {
			this.id = id;
			this.name = name;
		}
	}

	public class PropertyWithIgnore {
		@JsonIgnore
		public int id;
		public String name;

		public PropertyWithIgnore(int id, String name) {
			this.id = id;
			this.name = name;
		}
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	public class MyBean {
		public int id;
		public String name;

		public MyBean(int id, String name) {
			this.id = id;
			this.name = name;
		}
	}

	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
	public class PrivateBean {
		private int id;
		private String name;

		public PrivateBean(int id, String name) {
			this.id = id;
			this.name = name;
		}
	}

	static class User {
		public int id;
		public Name name;

		@JsonIgnoreType
		public static class Name {
			public String firstName;
			public String lastName;

			public Name(String firstName, String lastName) {
				this.firstName = firstName;
				this.lastName = lastName;
			}
		}

		public User(int id, Name name) {
			this.id = id;
			this.name = name;
		}
	}
}


