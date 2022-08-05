package com.pineapple.commons.serialization.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.pineapple.commons.serialization.JsonBuilder;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.Map;

@NoArgsConstructor
public abstract class AbstractJsonSerializer<T> extends JsonSerializer<T> {

    @Autowired
    private ApplicationContext context;


    public AbstractJsonSerializer<?> getSerializer(Class<?> type) {
        return ((Map<Class<?>, AbstractJsonSerializer<?>>) context.getBean("serializerMap"))
                .get(type);
    }

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
