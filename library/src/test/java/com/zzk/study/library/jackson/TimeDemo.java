package com.zzk.study.library.jackson;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class TimeDemo {
    @Test
    public void date_when_writeValueAsString_will_throw_exception() throws JsonProcessingException {
        final EventWithDate event = new EventWithDate(new Date());

        final ObjectMapper mapper = new ObjectMapper();
        final String result = mapper.writeValueAsString(event);
        System.out.println(result);
    }

    @Test
    public void localDateTime_when_writeValueAsString_will_throw_exception() throws JsonProcessingException {
        final LocalDateTime date = LocalDateTime.of(2014, 12, 20, 2, 30);
        final EventWithLocalDateTime event = new EventWithLocalDateTime(date);

        final ObjectMapper mapper = new ObjectMapper();
        final String result = mapper.writeValueAsString(event);
        System.out.println(result);
    }

    @Test
    public void writeValueAsString1() throws JsonProcessingException {
        final LocalDateTime date = LocalDateTime.of(2014, 12, 20, 2, 30);
        final EventWithLocalDateTime event = new EventWithLocalDateTime(date);

        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        final String result = mapper.writeValueAsString(event);
        System.out.println(result);

        final EventWithLocalDateTimeExtend event2 = new EventWithLocalDateTimeExtend(date);
        final String s = mapper.writeValueAsString(event2);
        System.out.println(s);
    }

    @Test
    public void writeValueAsString2() throws JsonProcessingException {
        String dt = "{\"dateTime\":\"2014-12-20 02:30:00\"}";

        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        System.out.println(mapper.readValue(dt, EventWithLocalDateTimeExtend.class));
    }

    @Test
    public void writeValueAsString3() throws JsonProcessingException {
        final LocalDateTime date = LocalDateTime.of(2014, 12, 20, 2, 30);

        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        final String result = mapper.writeValueAsString(date);
        System.out.println(result);
        assertEquals("\"2014-12-20T02:30:00\"", result);
    }


    @Test
    public void whenSerializingJava8DateAndReadingValue_thenCorrect() throws IOException {
        String stringDate = "\"2014-12-20\"";

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        LocalDate result = mapper.readValue(stringDate, LocalDate.class);
        assertThat(result.toString(), containsString("2014-12-20"));
    }

    @Test
    public void whenSerializingJava8DateAndReadingFromEntity_thenCorrect() throws IOException {
        String json = "{\"name\":\"party\",\"eventDate\":\"20-12-2014\"}";

        ObjectMapper mapper = new ObjectMapper();

        EventWithLocalDate result = mapper.readValue(json, EventWithLocalDate.class);
        assertThat(result.getEventDate().toString(), containsString("2014-12-20"));
    }

    @Data
    @AllArgsConstructor
    public class EventWithDate {
        public Date date;
    }

    @Data
    @AllArgsConstructor
    public class EventWithLocalDateTime {
        public LocalDateTime dateTime;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EventWithLocalDateTimeExtend {
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        public LocalDateTime dateTime;
    }

    public static class EventWithLocalDate {
        public String name;

        @JsonDeserialize(using = LocalDateDeserializer.class)
        @JsonSerialize(using = LocalDateSerializer.class)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        public LocalDate eventDate;

        public EventWithLocalDate() {}

        public EventWithLocalDate(final String name, final LocalDate eventDate) {
            this.name = name;
            this.eventDate = eventDate;
        }

        public LocalDate getEventDate() {
            return eventDate;
        }

        public String getName() {
            return name;
        }
    }
}
