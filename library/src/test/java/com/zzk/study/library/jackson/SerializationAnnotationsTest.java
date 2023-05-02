package com.zzk.study.library.jackson;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.zzk.study.library.jackson.serializer.EventWithSerializer;
import com.zzk.study.library.jackson.serializer.EventWithoutSerializer;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;



/**
 * <ol>
 * <li> @JsonAnyGetter annotation allows the flexibility of using a Map field as standard properties.</li>
 * <li> @JsonGetter annotation is an alternative to the @JsonProperty annotation to mark a method as a getter method.</li>
 * <li> @JsonPropertyOrder annotation to specify the order of properties on serialization.</li>
 * <li> @JsonRawValue</li>
 * <li> @JsonValue indicates a single method that the library will use to serialize the entire instance.</li>
 * <li> @JsonSerialize</li>
 * </ol>
 * */
public class SerializationAnnotationsTest {

    // @JsonAnyGetter
    @Test
    public void whenSerializingUsingJsonAnyGetter_thenCorrect() throws JsonProcessingException {

        ExtendableBean bean = new ExtendableBean("My bean");
        bean.add("attr1", "val1");
        bean.add("attr2", "val2");

        String result = new ObjectMapper().writeValueAsString(bean);
        System.out.println(result);

        assertThat(result, containsString("attr1"));
        assertThat(result, containsString("val1"));
    }

    // @JsonGetter
    @Test
    public void whenSerializingUsingJsonGetter_thenCorrect() throws JsonProcessingException {

        MyBean bean = new MyBean(1, "My bean");

        String result = new ObjectMapper().writeValueAsString(bean);
        System.out.println(result);
        assertThat(result, containsString("hi:My bean"));
        assertThat(result, containsString("1"));
    }

    // @JsonPropertyOrder({ "name", "id" })
    @Test
    public void whenSerializingUsingJsonPropertyOrder_thenCorrect() throws JsonProcessingException {
        MyBean2 bean = new MyBean2(1, "My bean");

        String result = new ObjectMapper().writeValueAsString(bean);
        System.out.println(result);

        assertThat(result, containsString("My bean"));
        assertThat(result, containsString("1"));
    }

    // @JsonRawValue
    @Test
    public void whenSerializingUsingJsonRawValue_thenCorrect() throws JsonProcessingException {
        RawBean bean = new RawBean("My bean", "{\"attr\":false}");

        String result = new ObjectMapper().writeValueAsString(bean);
        System.out.println(result);
        assertThat(result, containsString("My bean"));
        assertThat(result, containsString("{\"attr\":false}"));

        RawBean2 bean2 = new RawBean2("My bean", "{\"attr\":false}");

        result = new ObjectMapper().writeValueAsString(bean2);
        System.out.println(result);
    }

    // @JsonValue
    @Test
    public void whenSerializingUsingJsonValue_thenCorrect() throws JsonProcessingException {
        String enumAsString = new ObjectMapper().writeValueAsString(TypeEnumWithValue.TYPE1);
        System.out.println(enumAsString); // "Type A"
        assertThat(enumAsString, is("\"Type A\""));
    }

    // @JsonRootName(value = "user")
    @Test
    public void whenSerializingUsingJsonRootName_thenCorrect() throws JsonProcessingException {
        UserWithRoot user = new UserWithRoot(1, "John");

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        String result = mapper.writeValueAsString(user);
        System.out.println(result); // {"user":{"id":1,"name":"John"}}

        assertThat(result, containsString("John"));
        assertThat(result, containsString("user"));
    }

    // @JsonSerialize(using = CustomDateSerializer.class)
    @Test
    public void whenSerializingUsingJsonSerialize_thenCorrect() throws JsonProcessingException, ParseException {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

        String toParse = "20-12-2014 02:30:00";
        Date date = df.parse(toParse);
        EventWithSerializer event = new EventWithSerializer("party", date);

        String result = new ObjectMapper().writeValueAsString(event);
        System.out.println(result);
        assertThat(result, containsString(toParse));

        EventWithoutSerializer event2 = new EventWithoutSerializer("party", date);

        String result2 = new ObjectMapper().writeValueAsString(event2);
        System.out.println(result2);
    }

    public class ExtendableBean {
        public String name;
        private Map<String, String> properties = new HashMap<>();

        @JsonAnyGetter(enabled = true)
        public Map<String, String> getProperties() {
            return properties;
        }

        public ExtendableBean(String name) {
            this.name = name;
        }

        public void add(String key, String value){
            properties.put(key, value);
        }
    }

    public class MyBean {
        public int id;
        private String name;

        @JsonGetter("name")
        public String getTheName() {
            return "hi:"+name;
        }

        public MyBean(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    @JsonPropertyOrder({ "name", "id" })
    //@JsonPropertyOrder(alphabetic=true)
    public class MyBean2 {
        public int id;
        public String name;

        public MyBean2(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    class RawBean {
        public String name;

        @JsonRawValue
        public String json;

        public RawBean(String name, String json) {
            this.name = name;
            this.json = json;
        }
    }

    class RawBean2 {
        public String name;
        public String json;

        public RawBean2(String name, String json) {
            this.name = name;
            this.json = json;
        }
    }

    public enum TypeEnumWithValue {
        TYPE1(1, "Type A"), TYPE2(2, "Type 2");

        private Integer id;
        private String name;

        // standard constructors
        TypeEnumWithValue(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        @JsonValue
        public String getName() {
            return name;
        }
    }

    @JsonRootName(value = "user")
    class UserWithRoot {
        public int id;
        public String name;

        public UserWithRoot(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}

