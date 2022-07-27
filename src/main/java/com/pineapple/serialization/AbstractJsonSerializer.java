package com.pineapple.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.NoArgsConstructor;

import java.io.IOException;

@NoArgsConstructor
public abstract class AbstractJsonSerializer<T> extends JsonSerializer<T> {

    public final void serialize(T value,
                                JsonGenerator jsonGenerator,
                                SerializerProvider serializerProvider) throws IOException {
        this.serialize(value, new JsonBuilder(jsonGenerator, true));
    }

    public void serialize(T value, JsonBuilder builder) throws IOException {
        this.serialize(value, null, builder);
    }

    public abstract void serialize(T value, Enum<?> fieldName, JsonBuilder builder) throws IOException;

}
