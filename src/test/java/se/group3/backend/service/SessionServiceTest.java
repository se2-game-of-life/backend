package se.group3.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import se.group3.backend.services.SessionService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class SessionServiceTest {

    private SimpMessageHeaderAccessor headerAccessor;

    private SessionService sessionService;

    @BeforeEach
    void setUp() {
        headerAccessor = Mockito.mock(SimpMessageHeaderAccessor.class);
        sessionService = new SessionService();
    }


    @Test
    void getUUIDMissing() {
        Map<String, Object> sessionAttributes = new HashMap<>();
        sessionAttributes.put("uuid", null);
        when(headerAccessor.getSessionAttributes()).thenReturn(sessionAttributes);
        Exception e = assertThrows(Exception.class, () -> sessionService.getUUID(headerAccessor));

        assertEquals("Session attribute 'uuid' is not of type String or missing from session header!", e.getMessage());
    }

    @Test
    void getUUIDMissingHeader() {
        when(headerAccessor.getSessionAttributes()).thenReturn(null);
        Exception e = assertThrows(Exception.class, () -> sessionService.getUUID(headerAccessor));

        assertEquals("SessionAttributes could not be extracted from session header!", e.getMessage());
    }

    @Test
    void putUUID_headerMissing_test() {
        when(headerAccessor.getSessionAttributes()).thenReturn(null);
        String uuid = "123";

        Exception e = assertThrows(Exception.class, () -> sessionService.putUUID(headerAccessor, uuid));
        assertEquals("SessionAttributes could not be extracted from session header!", e.getMessage());
    }


    @Test
    void getUUID_noString_test() {
        Map<String, Object> sessionAttributes = new HashMap<>();
        sessionAttributes.put("uuid", 1);
        when(headerAccessor.getSessionAttributes()).thenReturn(sessionAttributes);

        Exception e = assertThrows(Exception.class, () -> sessionService.getUUID(headerAccessor));
        assertEquals("Session attribute 'uuid' is not of type String or missing from session header!", e.getMessage());
    }

    @Test
    void putUUID_successfully_test(){
        Map<String, Object> sessionAttributes = new HashMap<>();
        when(headerAccessor.getSessionAttributes()).thenReturn(sessionAttributes);

        String uuid = "123";
        assertDoesNotThrow(() -> sessionService.putUUID(headerAccessor, uuid));

        assertEquals(uuid, sessionAttributes.get("uuid"));
    }

    @Test
    void getUUID_successfully_test(){
        String uuid = "123";
        Map<String, Object> sessionAttributes = new HashMap<>();
        sessionAttributes.put("uuid", uuid);
        when(headerAccessor.getSessionAttributes()).thenReturn(sessionAttributes);

        String result = assertDoesNotThrow(()->sessionService.getUUID(headerAccessor));

        assertEquals(uuid, result);
    }

}