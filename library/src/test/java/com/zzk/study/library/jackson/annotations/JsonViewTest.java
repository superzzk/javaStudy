package com.zzk.study.library.jackson.annotations;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

/**
 * @author zhangzhongkun02
 * @date 2023/11/15 10:52
 */
public class JsonViewTest {

    // @JsonView(Views.Public.class)
    @Test
    public void whenSerializingUsingJsonView_thenCorrect() throws JsonProcessingException {
        Item item = new Item(2, "book", "John");

        String result = new ObjectMapper()
                .writerWithView(Item.Views.Public.class)
                .writeValueAsString(item);
        System.out.println(result);

        assertThat(result, containsString("book"));
        assertThat(result, containsString("2"));
        assertThat(result, not(containsString("John")));
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

        public static class Views {
            public static class Public { }
            public static class Internal extends Public { }
        }
    }
}
