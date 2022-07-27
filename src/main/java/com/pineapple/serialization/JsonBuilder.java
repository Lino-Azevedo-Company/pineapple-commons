package com.pineapple.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;

@AllArgsConstructor
public class JsonBuilder {

    private final JsonGenerator generator;
    private Boolean writeNull;

    public JsonBuilder writeStartObject() throws IOException {
        generator.writeStartObject();
        return this;
    }

    public JsonBuilder writeEndObject() throws IOException {
        generator.writeEndObject();
        return this;
    }

    public JsonBuilder writeObjectFieldStart(Enum<?> field) throws IOException {
        if (field != null) {
            generator.writeObjectFieldStart(field.toString());
        } else {
            generator.writeStartObject();
        }

        return this;
    }

    public JsonBuilder writeNumberField(Enum<?> field, BigDecimal value) throws IOException {
        if (value != null) {
            generator.writeStringField(field.toString(), value.toString());
        } else if (writeNull) {
            writeNullField(field);
        }

        return this;
    }

    public JsonBuilder writeNumberField(Enum<?> field, Long value) throws IOException {
        if (value != null) {
            generator.writeNumberField(field.toString(), value);
        } else if (writeNull) {
            writeNullField(field);
        }

        return this;
    }

    public JsonBuilder writeStringField(Enum<?> field, String value) throws IOException {
        if (value != null) {
            generator.writeStringField(field.toString(), value);
        } else if (writeNull) {
            writeNullField(field);
        }

        return this;
    }

    public JsonBuilder writeDateField(Enum<?> field, LocalDateTime value) throws IOException {
        if (value != null) {
            generator.writeNumberField(
                    field.toString(),
                    value.atZone(ZoneId.systemDefault())
                            .toInstant()
                            .toEpochMilli());
        } else if (writeNull) {
            writeNullField(field);
        }

        return this;
    }

    public JsonBuilder writeBooleanField(Enum<?> field, Boolean value) throws IOException {
        if (value != null) {
            generator.writeBooleanField(field.toString(), value);
        } else if (writeNull) {
            writeNullField(field);
        }

        return this;
    }

    public JsonBuilder writeString(String value) throws IOException {
        generator.writeString(value);
        return this;
    }

    public JsonBuilder writeNumber(BigDecimal value) throws IOException {
        generator.writeString(value.toString());
        return this;
    }

    public JsonBuilder writeNumber(Long value) throws IOException {
        generator.writeNumber(value);
        return this;
    }

    public JsonBuilder writeBoolean(Boolean value) throws IOException {
        generator.writeBoolean(value);
        return this;
    }

    private void writeNullField(Enum<?> field) throws IOException {
        generator.writeStringField(field.toString(), "");
    }

    public JsonBuilder writeStartArray() throws IOException {
        generator.writeStartArray();
        return this;
    }

    public JsonBuilder writeEndArray() throws IOException {
        generator.writeEndArray();
        return this;
    }

    public JsonBuilder writeArrayFieldStart(Enum<?> field) throws IOException {
        if (field != null) {
            generator.writeArrayFieldStart(field.toString());
        } else {
            generator.writeStartArray();
        }

        return this;
    }

    public String toString() {
        try {
            generator.flush();
            return generator.getOutputTarget().toString();
        } catch (IOException e) {
            return generator.toString();
        }
    }
}
