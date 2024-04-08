package se.group3.backend.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SerializationUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private SerializationUtil() {
        throw new UnsupportedOperationException("This class cannot be instantiated!");
    }

    public static String jsonStringFromClass(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public static <T> Object toObject(String message, Class<T> messageType) throws JsonProcessingException {
        return objectMapper.readValue(message, messageType);
    }
}
