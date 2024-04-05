package se2.group3.backend.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class SerializationUtil {
    static ObjectMapper objectMapper;

    public static String jsonStringFromClass(Object object){
        try{
            return objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
