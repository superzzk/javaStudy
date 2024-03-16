package com.zzk.study.library.jackson.annotations;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;


public class JsonPropertyTest {

    // @JsonProperty使用在方法上
    @Test
    public void annotation_on_method() throws IOException {
        BeanWithJsonPropertyOnMethod bean = new BeanWithJsonPropertyOnMethod(1, "My bean");

        String result = new ObjectMapper().writeValueAsString(bean);
        System.out.println(result);

        assertThat(result, containsString("My bean"));
        assertThat(result, containsString("1"));

        BeanWithJsonPropertyOnMethod resultBean = new ObjectMapper().readerFor(BeanWithJsonPropertyOnMethod.class).readValue(result);
        assertEquals("My bean", resultBean.getTheName());
    }

    // @JsonProperty使用在field
    @Test
    public void annotation_on_field() throws IOException {
        BeanWithJsonPropertyOnField bean = new BeanWithJsonPropertyOnField(1, "My bean");

        String result = new ObjectMapper().writeValueAsString(bean);
        System.out.println(result);

        BeanWithJsonPropertyOnField resultBean = new ObjectMapper().readerFor(BeanWithJsonPropertyOnField.class).readValue(result);
        assertEquals("My bean", resultBean.getMyName());
    }

    /**
     * @JsonProperty with access
     * Access.WRITE_ONLY 和 READ_ONLY表示的是对于Object的操作
     */
    @Test
    public void annotation_with_access() throws IOException {
        BeanWithJsonPropertyWithAccess bean = new BeanWithJsonPropertyWithAccess(1, "Write Only", "Read Only");

        String result = new ObjectMapper().writeValueAsString(bean);
        System.out.println(result);
        assertTrue(result.contains("Read Only"));
        assertFalse(result.contains("Write Only"));

        String json = "{\"id\":1,\"readOnly\":\"Read Only\", \"writeOnly\":\"Write Only\"}";
        BeanWithJsonPropertyWithAccess resultBean = new ObjectMapper().readerFor(BeanWithJsonPropertyWithAccess.class).readValue(json);
        System.out.println(resultBean);
        assertEquals(1, resultBean.getId());
        assertEquals("Write Only", resultBean.getWriteOnly());
        assertNull(resultBean.getReadOnly());
    }

    @NoArgsConstructor
    @AllArgsConstructor
    static class BeanWithJsonPropertyOnMethod {
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
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class BeanWithJsonPropertyOnField {
        public int id;
        @JsonProperty("name")
        private String myName;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class BeanWithJsonPropertyWithAccess {
        public int id;
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        private String writeOnly;
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        private String readOnly;
    }
}

