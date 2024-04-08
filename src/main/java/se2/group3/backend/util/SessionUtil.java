package se2.group3.backend.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import se2.group3.backend.exceptions.SessionOperationException;

import java.util.Map;

@Slf4j
public class SessionUtil {

    private static final String attributeExtractionFail = "SessionAttributes could not be extracted from session header!";

    private SessionUtil() {
        throw new UnsupportedOperationException("This class cannot be instantiated!");
    }

    public static void putSessionAttribute(SimpMessageHeaderAccessor headerAccessor, String key, Object value) throws SessionOperationException {
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        if(sessionAttributes == null) {
            throw new SessionOperationException(attributeExtractionFail);
        }
        sessionAttributes.put(key, value);
    }

    public static Long getLobbyID(SimpMessageHeaderAccessor headerAccessor) throws SessionOperationException {
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();

        if(sessionAttributes == null) {
            throw new SessionOperationException(attributeExtractionFail);
        }

        Object lobbyID = sessionAttributes.get("lobbyID");
        if(!(lobbyID == null || lobbyID instanceof Long)) {
            throw new SessionOperationException("Session attribute 'lobbyID' is not of type Long!");
        }
        return (Long) lobbyID;
    }

    public static String getUUID(SimpMessageHeaderAccessor headerAccessor) throws SessionOperationException {
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();

        if(sessionAttributes == null) {
            throw new SessionOperationException(attributeExtractionFail);
        }

        Object uuid = sessionAttributes.get("uuid");
        if(!(uuid instanceof String)) {
            throw new SessionOperationException("Session attribute 'uuid' is not of type String!");
        }

        return (String) uuid;
    }
}

