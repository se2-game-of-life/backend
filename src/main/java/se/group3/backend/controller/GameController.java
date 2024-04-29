package se.group3.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import se.group3.backend.services.GameService;
import se.group3.backend.services.LobbyService;

@Slf4j
@Controller
public class GameController {

    private final GameService gameService;
    private final LobbyService lobbyService;
    private final SimpMessagingTemplate template;

    @Autowired
    public GameController(GameService gameService, LobbyService lobbyService, SimpMessagingTemplate template) {
        this.gameService = gameService;
        this.lobbyService = lobbyService;
        this.template = template;
    }

    @MessageMapping("/game/start")
    public void startGame() {
        //get lobby and player id from session header
    }

    @MessageMapping("/game/path")
    public void choosePath() {
        //get lobby and player id from session header
    }

    @MessageMapping("/game/action")
    public void chooseAction() {
        //get lobby and player id from session header
    }

    @MessageMapping("/game/move")
    public void handleMove() {
        //get lobby and player id from session header
    }
}
