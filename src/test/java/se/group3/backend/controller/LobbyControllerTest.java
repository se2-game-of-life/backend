package se.group3.backend.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import se.group3.backend.dto.LobbyDTO;
import se.group3.backend.dto.PlayerDTO;
import se.group3.backend.exceptions.SessionOperationException;
import se.group3.backend.services.LobbyService;

import java.util.HashMap;
import java.util.Map;

class LobbyControllerTest {

    private LobbyService lobbyService;
    private SimpMessagingTemplate messagingTemplate;
    private LobbyController lobbyController;

    @BeforeEach
    void setUp() {
        lobbyService = Mockito.mock(LobbyService.class);
        messagingTemplate = Mockito.mock(SimpMessagingTemplate.class);
        lobbyController = new LobbyController(lobbyService, messagingTemplate);
    }

    @Test
    void createLobby() {
        String playerDTO = "{\"playerName\":\"asd\"}";
        SimpMessageHeaderAccessor headerAccessor = Mockito.mock(SimpMessageHeaderAccessor.class);
        LobbyDTO lobbyDTO = Mockito.mock(LobbyDTO.class);

        Map<String, Object> sessionAttributes = new HashMap<>();
        sessionAttributes.put("uuid", "TestUUID");
        Mockito.when(headerAccessor.getSessionAttributes()).thenReturn(sessionAttributes);

        try {
            Mockito.when(lobbyService.createLobby(Mockito.any(PlayerDTO.class), Mockito.eq(headerAccessor))).thenReturn(lobbyDTO);
        } catch (SessionOperationException e) {
            Assertions.fail(e);
        }

        lobbyController.createLobby(playerDTO, headerAccessor);

        Mockito.verify(messagingTemplate).convertAndSend(Mockito.eq("/topic/lobbies/TestUUID"), Mockito.anyString());
    }

    @Test
    void joinLobby() {
        SimpMessageHeaderAccessor headerAccessor = Mockito.mock(SimpMessageHeaderAccessor.class);
        LobbyDTO lobbyDTO = Mockito.mock(LobbyDTO.class);

        Map<String, Object> sessionAttributes = new HashMap<>();
        sessionAttributes.put("uuid", "TestUUID");
        Mockito.when(headerAccessor.getSessionAttributes()).thenReturn(sessionAttributes);
        Mockito.when(lobbyDTO.getLobbyID()).thenReturn(1L);

        try {
            Mockito.when(lobbyService.joinLobby(Mockito.any(Long.class), Mockito.any(PlayerDTO.class), Mockito.eq(headerAccessor))).thenReturn(lobbyDTO);
        } catch (SessionOperationException e) {
            Assertions.fail(e);
        }

        lobbyController.joinLobby("{\"lobbyID\":1,\"player\":{\"playerName\":\"TestName\"}}", headerAccessor);

        Mockito.verify(messagingTemplate).convertAndSend(Mockito.eq("/topic/lobbies/1"), Mockito.anyString());
    }

    @Test
    void leaveLobby() {
        SimpMessageHeaderAccessor headerAccessor = Mockito.mock(SimpMessageHeaderAccessor.class);
        LobbyDTO lobbyDTO = Mockito.mock(LobbyDTO.class);

        Map<String, Object> sessionAttributes = new HashMap<>();
        sessionAttributes.put("uuid", "TestUUID");
        Mockito.when(headerAccessor.getSessionAttributes()).thenReturn(sessionAttributes);
        Mockito.when(lobbyDTO.getLobbyID()).thenReturn(1L);

        try {
            Mockito.when(lobbyService.leaveLobby(Mockito.eq(headerAccessor))).thenReturn(lobbyDTO);
        } catch (SessionOperationException e) {
            Assertions.fail(e);
        }

        lobbyController.leaveLobby(headerAccessor);
        Mockito.verify(messagingTemplate).convertAndSend(Mockito.eq("/topic/lobbies/1"), Mockito.anyString());
    }
}