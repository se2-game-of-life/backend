package se.group3.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import se.group3.backend.services.BoardService;
import se.group3.backend.services.GameService;
import se.group3.backend.services.LobbyService;


@Slf4j
@Controller
public class GameController {
    private final BoardService boardService;
    private final SimpMessagingTemplate template;
    private final GameService gameService;
    private final LobbyService lobbyService;
    private static final String ERROR_PATH = "/topic/errors/";
    private static final String GAME_PATH = "/topic/game/";
    @Autowired
    public GameController(GameService gameService, LobbyService lobbyService, BoardService boardService, SimpMessagingTemplate template) {
        this.boardService = boardService;
        this.gameService = gameService;
        this.lobbyService = lobbyService;
        this.template = template;
    }



}
