package se.group3.backend.controller;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import se.group3.backend.dto.LobbyDTO;
import se.group3.backend.services.GameService;


import java.util.HashMap;
import java.util.Map;


import static org.mockito.Mockito.*;


class GameControllerTest {
    private GameController gameController;
    private SimpMessagingTemplate messagingTemplate;
    private GameService gameService;

    @BeforeEach
    void setUp() {
        gameService = mock(GameService.class);
        messagingTemplate = mock(SimpMessagingTemplate.class);
        gameController = new GameController(gameService, null, null, messagingTemplate);
    }

    @Test
    void choosePathTest(){
        SimpMessageHeaderAccessor headerAccessor = mock(SimpMessageHeaderAccessor.class);
        LobbyDTO lobbyDTO = mock(LobbyDTO.class);
        Map<String, Object> sessionAttributes = new HashMap<>();
        sessionAttributes.put("uuid", "TestUUID");
        sessionAttributes.put("lobbyID", 1L);
        Mockito.when(headerAccessor.getSessionAttributes()).thenReturn(sessionAttributes);

        when(lobbyDTO.getLobbyID()).thenReturn(1L);
        String player = "Player1";

        when(gameService.choosePath("TestUUID", true)).thenReturn(player);

        gameController.choosePath(true, headerAccessor);

        verify(messagingTemplate).convertAndSend("/topic/game/1", "\"Player1\"");
    }






    @AfterEach
    void breakDown(){
        gameService = null;
        messagingTemplate = null;
        gameController =null;
    }
}
