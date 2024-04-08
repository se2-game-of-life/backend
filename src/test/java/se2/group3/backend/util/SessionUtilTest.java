package se2.group3.backend.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import se2.group3.backend.exceptions.SessionOperationException;

import java.util.HashMap;
import java.util.Map;

class SessionUtilTest {

    private SimpMessageHeaderAccessor headerAccessor;

    @BeforeEach
    void setUp() {
        headerAccessor = Mockito.mock(SimpMessageHeaderAccessor.class);
    }

    @Test
    void putSessionAttribute() {
        Map<String, Object> sessionAttributes = new HashMap<>();
        Mockito.when(headerAccessor.getSessionAttributes()).thenReturn(sessionAttributes);

        try {
            SessionUtil.putSessionAttribute(headerAccessor, "TestKey", "TestValue");
            Assertions.assertEquals("TestValue", sessionAttributes.get("TestKey"));
        } catch (SessionOperationException e) {
            Assertions.fail(e);
        }
    }

    @Test
    void putSessionAttributeHeaderMissing() {
        Mockito.when(headerAccessor.getSessionAttributes()).thenReturn(null);
        Assertions.assertThrows(SessionOperationException.class, () -> SessionUtil.putSessionAttribute(headerAccessor, "TestKey", "TestValue"));
    }

    @Test
    void getLobbyID() {
        Map<String, Object> sessionAttributes = new HashMap<>();
        sessionAttributes.put("lobbyID", 1L);
        Mockito.when(headerAccessor.getSessionAttributes()).thenReturn(sessionAttributes);

        try {
            Assertions.assertEquals(1L, SessionUtil.getLobbyID(headerAccessor));
        } catch (SessionOperationException e) {
            Assertions.fail(e);
        }
    }

    @Test
    void getLobbyIDMissing() {
        Map<String, Object> sessionAttributes = new HashMap<>();
        sessionAttributes.put("lobbyID", null);
        Mockito.when(headerAccessor.getSessionAttributes()).thenReturn(sessionAttributes);

        try {
            Long lobbyID = SessionUtil.getLobbyID(headerAccessor);
            Assertions.assertNull(lobbyID);
        } catch (SessionOperationException e) {
            Assertions.fail(e);
        }
    }

    @Test
    void getLobbyIDHeaderMissing() {
        Mockito.when(headerAccessor.getSessionAttributes()).thenReturn(null);
        Assertions.assertThrows(SessionOperationException.class, () -> SessionUtil.getLobbyID(headerAccessor));
    }

    @Test
    void getUUID() {
        Map<String, Object> sessionAttributes = new HashMap<>();
        sessionAttributes.put("uuid", "TestValue");
        Mockito.when(headerAccessor.getSessionAttributes()).thenReturn(sessionAttributes);

        try {
            String uuid = SessionUtil.getUUID(headerAccessor);
            Assertions.assertEquals("TestValue", uuid);
        } catch (SessionOperationException e) {
            Assertions.fail(e);
        }
    }

    @Test
    void getUUIDMissing() {
        Map<String, Object> sessionAttributes = new HashMap<>();
        sessionAttributes.put("uuid", null);
        Mockito.when(headerAccessor.getSessionAttributes()).thenReturn(sessionAttributes);
        Assertions.assertThrows(SessionOperationException.class, () -> SessionUtil.getUUID(headerAccessor));
    }

    @Test
    void getUUIDMissingHeader() {
        Mockito.when(headerAccessor.getSessionAttributes()).thenReturn(null);
        Assertions.assertThrows(SessionOperationException.class, () -> SessionUtil.getUUID(headerAccessor));
    }
}