package com.zzk.study.library.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.zzk.study.library.jackson.RecursionTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomListSerializer extends StdSerializer<List<RecursionTest.ItemWithSerializer>> {

    private static final long serialVersionUID = 3698763098000900856L;

    public CustomListSerializer() {
        this(null);
    }

    public CustomListSerializer(final Class<List<RecursionTest.ItemWithSerializer>> t) {
        super(t);
    }

    @Override
    public void serialize(final List<RecursionTest.ItemWithSerializer> items, final JsonGenerator generator, final SerializerProvider provider) throws IOException, JsonProcessingException {
        final List<Integer> ids = new ArrayList<Integer>();
        for (final RecursionTest.ItemWithSerializer item : items) {
            ids.add(item.id);
        }
        generator.writeObject(ids);
    }
}