package se.group3.backend.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import se.group3.backend.exceptions.SessionOperationException;
import se.group3.backend.services.SessionService;

import java.util.HashMap;
import java.util.Map;

class SessionServiceTest {

    private SimpMessageHeaderAccessor headerAccessor;

    @InjectMocks
    private SessionService sessionService;

    @BeforeEach
    void setUp() {
        headerAccessor = Mockito.mock(SimpMessageHeaderAccessor.class);
    }


    @Test
    void getUUIDMissing() {
        Map<String, Object> sessionAttributes = new HashMap<>();
        sessionAttributes.put("uuid", null);
        Mockito.when(headerAccessor.getSessionAttributes()).thenReturn(sessionAttributes);
        Assertions.assertThrows(NullPointerException.class, () -> sessionService.getUUID(headerAccessor));
    }

    @Test
    void getUUIDMissingHeader() {
        Mockito.when(headerAccessor.getSessionAttributes()).thenReturn(null);
        Assertions.assertThrows(NullPointerException.class, () -> sessionService.getUUID(headerAccessor));
    }
}