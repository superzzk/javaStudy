package com.zzk.study.library.jackson;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion
 */
public class RecursionTest {

    /*
     *  User and Item is many-to-many relation
     * */
    @Data
    public class User {
        public int id;
        public String name;
        public List<Item> userItems;

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public void addItem(Item item) {
            userItems = userItems == null ? new ArrayList<>() : userItems;
            userItems.add(item);
        }
    }

    @Data
    @AllArgsConstructor
    public class Item {
        public int id;
        public String itemName;
        public User owner;
    }

    /*
     * when serialize then exception
     * */
    @Test
    public void givenBidirectionRelation_whenSerializing_thenException() {

        User user = new User(1, "John");
        Item item = new Item(2, "book", user);
        user.addItem(item);

        Assertions.assertThrows(JsonMappingException.class, () -> {
            new ObjectMapper().writeValueAsString(item);
        });
    }

    /*
     * @JsonManagedReference is the forward part of reference, the one that gets serialized normally.
     * @JsonBackReference is the back part of reference; it'll be omitted from serialization.
     * @JsonBackReference can't be used on a collection, 反序列化的时候会有问题
     * */
    @Data
    public class User2 {
        public int id;
        public String name;
        @JsonManagedReference
        public List<Item2> userItems;

        public User2(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public void addItem(Item2 item) {
            userItems = userItems == null ? new ArrayList<>() : userItems;
            userItems.add(item);
        }
    }

    @AllArgsConstructor
    public class Item2 {
        public int id;
        public String itemName;

        @JsonBackReference
        public User2 owner;
    }

    @Test
    public void givenBidirectionRelation_whenUsingJacksonReferenceAnnotationWithSerialization_thenCorrect() throws JsonProcessingException {
        final User2 user = new User2(1, "John");
        final Item2 item = new Item2(2, "book", user);
        user.addItem(item);

        final String itemJson = new ObjectMapper().writeValueAsString(item);
        final String userJson = new ObjectMapper().writeValueAsString(user);

        // itemJson不包含user内容
        assertThat(itemJson, containsString("book"));
        assertThat(itemJson, not(containsString("John")));

        assertThat(userJson, containsString("John"));
        assertThat(userJson, containsString("userItems"));
        assertThat(userJson, containsString("book"));
    }


    /*
     * serialization of entities with bidirectional relationships using @JsonIdentityInfo
     * */
    @Data
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @NoArgsConstructor
    public static class User3 {
        public int id;
        public String name;
        public List<Item3> userItems;

        public User3(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public void addItem(Item3 item) {
            userItems = userItems == null ? new ArrayList<>() : userItems;
            userItems.add(item);
        }
    }

    @AllArgsConstructor
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @NoArgsConstructor
    public static class Item3 {
        public int id;
        public String itemName;

        public User3 owner;
    }

    @Test
    public void givenBidirectionRelation_whenUsingJsonIdentityInfo_thenCorrect() throws JsonProcessingException {
        User3 user = new User3(1, "John");
        Item3 item = new Item3(2, "book", user);
        user.addItem(item);

        String result = new ObjectMapper().writeValueAsString(item);
        System.out.println(result);

        assertThat(result, containsString("book"));
        assertThat(result, containsString("John"));
        assertThat(result, containsString("userItems"));
    }

    @Test
    public void givenBidirectionRelation_whenDeserializingWithIdentity_thenCorrect() throws JsonProcessingException, IOException {
        String json = "{\"id\":2,\"itemName\":\"book\",\"owner\":{\"id\":1,\"name\":\"John\",\"userItems\":[2]}}";

        Item3 item = new ObjectMapper().readerFor(Item3.class).readValue(json);

        assertEquals(2, item.id);
        assertEquals("book", item.itemName);
        assertEquals("John", item.owner.name);
    }

    /**
     * user @JsonIgnore
     * */
    @Data
    public class User4 {
        public int id;
        public String name;
        @JsonIgnore
        public List<Item4> userItems;

        public User4(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public void addItem(Item4 item) {
            userItems = userItems == null ? new ArrayList<>() : userItems;
            userItems.add(item);
        }
    }

    @AllArgsConstructor
    public class Item4 {
        public int id;
        public String itemName;

        public User4 owner;
    }

    @Test
    public void givenBidirectionRelation_whenUsingJsonIgnore_thenCorrect() throws JsonProcessingException {

        User4 user = new User4(1, "John");
        Item4 item = new Item4(2, "book", user);
        user.addItem(item);

        String result = new ObjectMapper().writeValueAsString(item);

        assertThat(result, containsString("book"));
        assertThat(result, containsString("John"));
        assertThat(result, not(containsString("userItems")));
    }


    /**
     * user @JsonView
     * */
    public static class Views {
        public static class Public {}
        public static class Internal extends Public {}
    }

    @Data
    public class User5 {
        @JsonView(Views.Public.class)
        public int id;
        @JsonView(Views.Public.class)
        public String name;
        @JsonView(Views.Internal.class)
        public List<Item5> userItems;

        public User5(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public void addItem(Item5 item) {
            userItems = userItems == null ? new ArrayList<>() : userItems;
            userItems.add(item);
        }
    }

    @AllArgsConstructor
    public class Item5 {
        @JsonView(Views.Public.class)
        public int id;
        @JsonView(Views.Public.class)
        public String itemName;
        @JsonView(Views.Public.class)
        public User5 owner;
    }

    @Test
    public void givenBidirectionRelation_whenUsingPublicJsonView_thenCorrect() throws JsonProcessingException {
        User5 user = new User5(1, "John");
        Item5 item = new Item5(2, "book", user);
        user.addItem(item);

        String result = new ObjectMapper().writerWithView(Views.Public.class)
                .writeValueAsString(item);

        assertThat(result, containsString("book"));
        assertThat(result, containsString("John"));
        assertThat(result, not(containsString("userItems")));
    }

    @Test
    public void givenBidirectionRelation_whenUsingInternalJsonView_thenException() throws JsonProcessingException {
        User user = new User(1, "John");
        Item item = new Item(2, "book", user);
        user.addItem(item);

        Assertions.assertThrows(JsonMappingException.class, () -> {
            new ObjectMapper()
                    .writerWithView(Views.Internal.class)
                    .writeValueAsString(item);
        });
    }

    /**
     * custom serializer
     * */
    public static class CustomListSerializer extends StdSerializer<List<ItemWithSerializer>> {
        public CustomListSerializer() {
            this(null);
        }
        public CustomListSerializer(final Class<List<ItemWithSerializer>> t) {
            super(t);
        }

        @Override
        public void serialize(final List<ItemWithSerializer> items, final JsonGenerator generator, final SerializerProvider provider) throws IOException, JsonProcessingException {
            final List<Integer> ids = new ArrayList<Integer>();
            for (final ItemWithSerializer item : items) {
                ids.add(item.id);
            }
            generator.writeObject(ids);
        }
    }

    @ToString
    public static class UserWithSerializer {
        public int id;
        public String name;

        @JsonSerialize(using = CustomListSerializer.class)
        @JsonDeserialize(using = CustomListDeserializer.class)
        public List<ItemWithSerializer> userItems;

        public UserWithSerializer() {}

        public UserWithSerializer(final int id, final String name) {
            this.id = id;
            this.name = name;
            userItems = new ArrayList<ItemWithSerializer>();
        }

        public void addItem(final ItemWithSerializer item) {
            userItems.add(item);
        }
    }

    @ToString
    public static class ItemWithSerializer {
        public int id;
        public String itemName;
        public UserWithSerializer owner;

        public ItemWithSerializer() {}

        public ItemWithSerializer(final int id, final String itemName, final UserWithSerializer owner) {
            this.id = id;
            this.itemName = itemName;
            this.owner = owner;
        }
    }

    @Test
    public void givenBidirectionRelation_whenUsingCustomSerializer_thenCorrect() throws JsonProcessingException {
        final UserWithSerializer user = new UserWithSerializer(1, "John");
        final ItemWithSerializer item = new ItemWithSerializer(2, "book", user);
        user.addItem(item);

        final String result = new ObjectMapper().writeValueAsString(item);
        System.out.println(result);

        assertThat(result, containsString("book"));
        assertThat(result, containsString("John"));
        assertThat(result, containsString("userItems"));
    }

    public static class CustomListDeserializer extends StdDeserializer<List<ItemWithSerializer>> {
        public CustomListDeserializer() {
            this(null);
        }
        public CustomListDeserializer(final Class<?> vc) {
            super(vc);
        }

        @Override
        public List<ItemWithSerializer> deserialize(final JsonParser jsonparser, final DeserializationContext context) throws IOException, JsonProcessingException {
            return new ArrayList<ItemWithSerializer>();
        }
    }

    @Test
    public void givenBidirectionRelation_whenUsingCustomDeserializer_thenCorrect() throws JsonProcessingException, IOException {
        final String json = "{\"id\":2,\"itemName\":\"book\",\"owner\":{\"id\":1,\"name\":\"John\",\"userItems\":[2]}}";

        final ItemWithSerializer item = new ObjectMapper().readerFor(ItemWithSerializer.class).readValue(json);
        assertEquals(2, item.id);
        assertEquals("book", item.itemName);
        assertEquals("John", item.owner.name);
    }
}
