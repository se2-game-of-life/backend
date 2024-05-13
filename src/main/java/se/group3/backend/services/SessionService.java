package se.group3.backend.services;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;
import se.group3.backend.exceptions.SessionOperationException;

import java.util.Map;

@Service
public class SessionService {

    public void putUUID(SimpMessageHeaderAccessor headerAccessor, String uuid) throws SessionOperationException {
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        if(sessionAttributes == null) {
            throw new SessionOperationException("SessionAttributes could not be extracted from session header!");
        }
        sessionAttributes.put("uuid", uuid);
    }

    public String getUUID(SimpMessageHeaderAccessor headerAccessor) throws SessionOperationException {
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        if(sessionAttributes == null) {
            throw new SessionOperationException("SessionAttributes could not be extracted from session header!");
        }
        Object uuid = sessionAttributes.get("uuid");
        if(!(uuid instanceof String)) {
            throw new SessionOperationException("Session attribute 'uuid' is not of type String or missing from session header!");
        }
        return (String) uuid;
    }
}
