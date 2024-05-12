package se.group3.backend.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import se.group3.backend.services.GameService;


public class GameControllerTest {
    private GameController gameController;
    private SimpMessagingTemplate messagingTemplate;
    private GameService gameService;

    @BeforeEach
    void setUp() {
        gameService = Mockito.mock(GameService.class);
        messagingTemplate = Mockito.mock(SimpMessagingTemplate.class);
        gameController = new GameController(gameService, null, null, messagingTemplate);
    }

    @AfterEach
    void breakDown(){
        gameService = null;
        messagingTemplate = null;
        gameController =null;
    }
}
