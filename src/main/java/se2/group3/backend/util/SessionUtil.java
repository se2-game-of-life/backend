package se2.group3.backend.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import se2.group3.backend.dto.SessionExtractionResult;
import se2.group3.backend.exceptions.SessionOperationException;

import java.util.Map;

@Slf4j
public class SessionUtil {

    public static SessionExtractionResult extractSessionDetails(SimpMessageHeaderAccessor headerAccessor) throws SessionOperationException {
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        String sessionID = headerAccessor.getSessionId();

        if(sessionID == null) {
            throw new SessionOperationException("SessionID could not be extracted from session header!");
        }
        if(sessionAttributes == null) {
            throw new SessionOperationException("SessionAttributes could not be extracted from session header!");
        }

        return new SessionExtractionResult(sessionID, sessionAttributes);
    }

    public static Object putSessionAttribute(SimpMessageHeaderAccessor headerAccessor, String key, Object value) throws SessionOperationException {
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        if(sessionAttributes == null) {
            throw new SessionOperationException("SessionAttributes could not be extracted from session header!");
        }
        return sessionAttributes.put(key, value);
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

    public static String getSessionID(SimpMessageHeaderAccessor headerAccessor) throws SessionOperationException {
        String sessionID = headerAccessor.getSessionId();
        if(sessionID == null) {
            throw new SessionOperationException("SessionID could not be extracted from session header!");
        }
        return sessionID;
    }
}
