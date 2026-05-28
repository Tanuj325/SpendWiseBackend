package org.example.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class JacksonConfig {

    @Bean
    public SimpleModule objectIdModule() {
        SimpleModule module = new SimpleModule("ObjectIdModule");

        // ObjectId → plain hex string  e.g. "64abc123def456789012abcd"
        module.addSerializer(ObjectId.class, new JsonSerializer<ObjectId>() {
            @Override
            public void serialize(ObjectId value, JsonGenerator gen, SerializerProvider serializers)
                    throws IOException {
                gen.writeString(value.toHexString());
            }
        });

        // hex string → ObjectId  (handles requests that include an id field)
        module.addDeserializer(ObjectId.class, new JsonDeserializer<ObjectId>() {
            @Override
            public ObjectId deserialize(JsonParser p, DeserializationContext ctxt)
                    throws IOException {
                String text = p.getText();
                if (text == null || text.isBlank()) return null;
                try {
                    return new ObjectId(text);
                } catch (IllegalArgumentException e) {
                    return null; // ignore malformed id, service will assign a new one
                }
            }
        });

        return module;
    }
}