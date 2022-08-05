package com.pineapple.commons.serialization.deserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.pineapple.commons.serialization.exception.InvalidJsonException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

@NoArgsConstructor
public abstract class AbstractJsonDeserializer<T> extends JsonDeserializer<T> {


    @Autowired
    private ApplicationContext context;

    public AbstractJsonDeserializer<?> getDeserializer(Class<?> type) {
        return ((Map<Class<?>, AbstractJsonDeserializer<?>>) context.getBean("deserializerMap"))
                .get(type);
    }

    public final T deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        ObjectCodec objectCodec = parser.getCodec();
        JsonNode node = objectCodec.readTree(parser);

        try {
            return this.deserialize(node);
        } catch (RuntimeException e) {
            throw new InvalidJsonException("Json inválido", e);
        }
    }

    public abstract T deserialize(JsonNode node) throws IOException;

    protected String getStringFieldValue(JsonNode node, Enum<?> field) {
        return isNotEmpty(node, field) ?
                node.get(field.toString()).textValue() :
                null;
    }

    protected Long getLongFieldValue(JsonNode node, Enum<?> field) {
        return isNotEmpty(node, field) ?
                node.get(field.toString()).longValue() :
                null;
    }


    protected BigDecimal getBigDecimalFieldValue(JsonNode node, Enum<?> field) {
        return isNotEmpty(node, field) ?
                new BigDecimal(node.get(field.toString()).asText()) :
                null;
    }

    protected Boolean getBooleanFieldValue(JsonNode node, Enum<?> field) {
        return isNotEmpty(node, field) ?
                node.get(field.toString()).asBoolean() :
                null;
    }

    protected LocalDateTime getLocalDateTimeFieldValue(JsonNode node, Enum<?> field) {
        return isNotEmpty(node, field) ?
                Instant.ofEpochMilli(getLongFieldValue(node, field))
                        .atZone(ZoneId.systemDefault()).toLocalDateTime() :
                null;
    }

    protected boolean isArray(JsonNode node, Enum<?> field) {
        return node.has(field.toString()) && node.hasNonNull(field.toString()) &&
                node.get(field.toString()).isArray();
    }

    protected <E extends Enum<E>> E getEnumFieldValue(JsonNode node, Enum<?> field, Class<E> enumeration) {
        return Enum.valueOf(enumeration, getStringFieldValue(node, field));
    }

    private Boolean isNotEmpty(JsonNode node, Enum<?> field) {
        return node.has(field.toString()) &&
                node.hasNonNull(field.toString()) &&
                !(node.get(field.toString()).asText().isEmpty());
    }
}
