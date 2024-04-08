package se.group3.backend.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

import java.util.HashMap;
import java.util.Map;

class ConnectionControllerTest {

    @Test
    void setPlayerIdentifier() {
        SimpMessageHeaderAccessor headerAccessor = Mockito.mock(SimpMessageHeaderAccessor.class);
        ConnectionController controller = new ConnectionController();

        Map<String, Object> sessionAttributes = new HashMap<>();
        Mockito.when(headerAccessor.getSessionAttributes()).thenReturn(sessionAttributes);

        controller.setPlayerIdentifier("\"Test\"", headerAccessor);

        Assertions.assertEquals("Test", sessionAttributes.get("uuid"));
    }
}