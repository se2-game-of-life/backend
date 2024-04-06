package se2.group3.backend.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SerializationUtil {
    private static ObjectMapper objectMapper;

    private SerializationUtil() {
        throw new UnsupportedOperationException("This class cannot be instantiated!");
    }

    public static String jsonStringFromClass(Object object) throws JsonProcessingException {
            return objectMapper.writeValueAsString(object);
    }
}
