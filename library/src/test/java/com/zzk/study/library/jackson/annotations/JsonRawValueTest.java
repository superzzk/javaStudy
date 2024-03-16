package com.zzk.study.library.jackson.annotations;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.ToString;
import org.junit.Test;

/**
 * @author zhangzhongkun02
 * @date 2023/11/15 10:39
 */
public class JsonRawValueTest {
    ObjectMapper om = new ObjectMapper();

    // @JsonRawValue
    @Test
    public void write() throws JsonProcessingException {
        MyBean bean = new MyBean(1, "My bean");
        BeanWithJsonRawValue beanWithJsonRawValue = new BeanWithJsonRawValue();
        String a = om.writeValueAsString(bean);
        beanWithJsonRawValue.setRawValue(a);
        beanWithJsonRawValue.setMyBean(bean);
        System.out.println(om.writerWithDefaultPrettyPrinter().writeValueAsString(beanWithJsonRawValue));
    }

    @Test
    public void read() throws JsonProcessingException {
        String json = "{\n" +
//                "  \"rawValue\" : {\"id\":1,\"name\":\"My bean\"},\n" +
                "  \"myBean\" : {\n" +
                "    \"id\" : 1,\n" +
                "    \"name\" : \"My bean\"\n" +
                "  }\n" +
                "}";
        BeanWithJsonRawValue resultBean = om.readValue(json, BeanWithJsonRawValue.class);
        System.out.println(resultBean);

    }

    @Data
    public static class BeanWithJsonRawValue {
        @JsonRawValue
        String rawValue;

        MyBean myBean;
    }

    @ToString
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
}
