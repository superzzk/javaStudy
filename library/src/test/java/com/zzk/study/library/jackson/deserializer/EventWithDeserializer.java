package com.zzk.study.library.jackson.deserializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;

public class EventWithDeserializer {
    public String name;
 
    @JsonDeserialize(using = CustomDateDeserializer.class)
    public Date eventDate;
}