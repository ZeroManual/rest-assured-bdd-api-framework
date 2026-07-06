package com.company.api.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public final class JsonUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JsonUtils() {
    }

    public static JsonNode readJsonFromClasspath(String path) {
        try (InputStream input = JsonUtils.class.getClassLoader().getResourceAsStream(path)) {
            if (input == null) {
                throw new IllegalArgumentException("JSON resource not found: " + path);
            }
            return OBJECT_MAPPER.readTree(input);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read JSON resource: " + path, e);
        }
    }

    public static <T> T convert(JsonNode node, Class<T> type) {
        try {
            return OBJECT_MAPPER.treeToValue(node, type);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to convert JSON to " + type.getSimpleName(), e);
        }
    }
}
