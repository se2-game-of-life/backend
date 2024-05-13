package se.group3.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import se.group3.backend.dto.JoinLobbyRequest;
import se.group3.backend.dto.LobbyDTO;
import se.group3.backend.dto.PlayerDTO;
import se.group3.backend.exceptions.SessionOperationException;
import se.group3.backend.services.*;

@Slf4j
@Controller
public class GameOfLifeController {

    //services
    private final LobbyService lobbyService;
    private final GameService gameService;
    private final BoardService boardService;
    private final SimpMessagingTemplate messagingTemplate;
    private final SessionService sessionService;
    private final SerializationService serializationService;

    //path definitions
    private static final String ERROR_PATH = "/topic/errors/";
    private static final String LOBBIES_PATH = "/topic/lobbies/";

    @Autowired
    public GameOfLifeController(SimpMessagingTemplate template, LobbyService lobbyService, BoardService boardService, GameService gameService, SessionService sessionService, SerializationService serializationService) {
        this.lobbyService = lobbyService;
        this.gameService = gameService;
        this.boardService = boardService;
        this.messagingTemplate = template;
        this.sessionService = sessionService;
        this.serializationService = serializationService;
    }

    @MessageMapping("/lobby/create")
    public void createLobby(@Payload String playerName, SimpMessageHeaderAccessor headerAccessor) {
        try {
            LobbyDTO lobby = lobbyService.createLobby(getUUID(headerAccessor), playerName);
            messagingTemplate.convertAndSend(LOBBIES_PATH + getUUID(headerAccessor), SerializationService.jsonStringFromClass(lobby));
        } catch (IllegalStateException | JsonProcessingException | ClassCastException e) {
            messagingTemplate.convertAndSend(ERROR_PATH + getUUID(headerAccessor), e.getMessage());
        }
    }

    @MessageMapping("/lobby/join")
    public void joinLobby(@Payload String joinLobbyRequest, SimpMessageHeaderAccessor headerAccessor) {
        try{
            JoinLobbyRequest request = (JoinLobbyRequest) SerializationService.toObject(joinLobbyRequest, JoinLobbyRequest.class);
            LobbyDTO lobby = lobbyService.joinLobby(request.getLobbyID(), getUUID(headerAccessor), request.getPlayerName());
            messagingTemplate.convertAndSend(LOBBIES_PATH + lobby.getLobbyID(), SerializationService.jsonStringFromClass(lobby));
        } catch (JsonProcessingException | ClassCastException | IllegalStateException e) {
            log.error(e.getMessage());
        }
    }

    @MessageMapping("/lobby/leave")
    public void leaveLobby(SimpMessageHeaderAccessor headerAccessor) {
        try {
            LobbyDTO lobby = lobbyService.leaveLobby(getUUID(headerAccessor));
            messagingTemplate.convertAndSend(LOBBIES_PATH + lobby.getLobbyID(), SerializationService.jsonStringFromClass(lobby));
        } catch (JsonProcessingException | IllegalStateException e) {
            throw new RuntimeException(e);
        }
    }

    @MessageMapping("/lobby/start")
    public void startLobby() {}

    @MessageMapping("/lobby/turn")
    public void handlePlayerTurn() {}

    @MessageMapping("/lobby/choice")
    public void makeChoice() {}

    @MessageMapping("/fetch")
    public void fetchBoard() {}

    private String getUUID(SimpMessageHeaderAccessor headerAccessor) {
        try {
            return sessionService.getUUID(headerAccessor);
        } catch (SessionOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
