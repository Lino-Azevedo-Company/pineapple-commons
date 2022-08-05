package com.pineapple.commons.serialization.serialize;

import com.pineapple.commons.serialization.JsonBuilder;
import com.pineapple.commons.serialization.wrapper.ListWrapper;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

@Component
public class ListWrapperSerializer<T> extends AbstractJsonSerializer<ListWrapper<T>> {

    @Override
    public void serialize(ListWrapper<T> value, Enum<?> fieldName, JsonBuilder builder) throws IOException {
        builder.writeStartArray();
        writeValues(value.getList(), builder);
        builder.writeEndArray();
    }

    public void serialize(List<T> value, Enum<?> fieldName, JsonBuilder builder) throws IOException {
        builder.writeArrayFieldStart(fieldName);
        if (!CollectionUtils.isEmpty(value)) {
            writeValues(value, builder);
        }
        builder.writeEndArray();
    }

    private void writeValues(List<T> value, JsonBuilder builder) throws IOException {
        for (final T item : value) {
            final AbstractJsonSerializer<T> serializer = (AbstractJsonSerializer<T>) getSerializer(item.getClass());
            serializer.serialize(item, builder);
        }
    }
}
