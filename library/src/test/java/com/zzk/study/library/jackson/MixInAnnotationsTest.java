package com.zzk.study.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class MixInAnnotationsTest {

	static class User {
		public int id;
		public String name;
	}
	public class Item {
		public int id;
		public String itemName;
		public User owner;

		public Item(int id, String itemName, User owner) {
			this.id = id;
			this.itemName = itemName;
			this.owner = owner;
		}
	}

	@JsonIgnoreType
	public static class MyMixInForIgnoreType {}

	@Test
	public void whenSerializingUsingMixInAnnotation_thenCorrect() throws JsonProcessingException {
		Item item = new Item(1, "book", null);

		String result = new ObjectMapper().writeValueAsString(item);
		System.out.println(result);
		assertThat(result, containsString("owner"));

		ObjectMapper mapper = new ObjectMapper();
		mapper.addMixIn(User.class, MyMixInForIgnoreType.class);

		result = mapper.writeValueAsString(item);
		System.out.println(result);
		assertThat(result, not(containsString("owner")));
	}

}
