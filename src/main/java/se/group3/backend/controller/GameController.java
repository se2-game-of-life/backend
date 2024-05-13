package se.group3.backend.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import se.group3.backend.services.BoardService;
import se.group3.backend.services.GameService;
import se.group3.backend.services.LobbyService;
import se.group3.backend.util.SerializationUtil;
import se.group3.backend.util.SessionUtil;
import se.group3.backend.exceptions.SessionOperationException;


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

    @MessageMapping("/game/path")
    public void choosePath(@Payload boolean collegePath, SimpMessageHeaderAccessor headerAccessor) {
        long lobbyUUID;
        String playerUUID;

        try {
            lobbyUUID = SessionUtil.getLobbyID(headerAccessor);
            playerUUID = SessionUtil.getUUID(headerAccessor);
        } catch (SessionOperationException | NullPointerException e) {
            log.error(e.getMessage());
            return;
        }


        try {
            String player = gameService.choosePath(playerUUID, collegePath);
            this.template.convertAndSend(GAME_PATH + lobbyUUID, SerializationUtil.jsonStringFromClass(player));
        } catch (IllegalStateException | JsonProcessingException e) {
            template.convertAndSend(ERROR_PATH + lobbyUUID, e.getMessage());
            template.convertAndSend(ERROR_PATH + playerUUID, e.getMessage());
        }
    }

    @MessageMapping("/game/start")
    public void startGame(SimpMessageHeaderAccessor headerAccessor) {
        long uuid;
        try {
            uuid = SessionUtil.getLobbyID(headerAccessor);
        } catch (SessionOperationException | NullPointerException e) {
            log.error(e.getMessage());
            return;
        }

        try {
            lobbyService.startGame(uuid);
        } catch (IllegalStateException e) {
            template.convertAndSend(ERROR_PATH + uuid, e.getMessage());
        }
    }
}


