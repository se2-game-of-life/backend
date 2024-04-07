package se2.group3.backend.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import se2.group3.backend.exceptions.SessionOperationException;

import java.util.Map;

@Slf4j
public class SessionUtil {

    private SessionUtil() {
        throw new UnsupportedOperationException("This class cannot be instantiated!");
    }

    public static void putSessionAttribute(SimpMessageHeaderAccessor headerAccessor, String key, Object value) throws SessionOperationException {
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        if(sessionAttributes == null) {
            throw new SessionOperationException("SessionAttributes could not be extracted from session header!");
        }
        sessionAttributes.put(key, value);
    }

    public static Long getLobbyID(SimpMessageHeaderAccessor headerAccessor) throws SessionOperationException {
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();

        if(sessionAttributes == null) {
            throw new SessionOperationException("SessionAttributes could not be extracted from session header!");
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
            throw new SessionOperationException("SessionAttributes could not be extracted from session header!");
        }

        Object uuid = sessionAttributes.get("uuid");
        if(!(uuid == null || uuid instanceof String)) {
            throw new SessionOperationException("Session attribute 'lobbyID' is not of type Long!");
        }
        return (String) uuid;
    }
}

