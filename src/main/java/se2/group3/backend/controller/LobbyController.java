package se2.group3.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import se2.group3.backend.dto.*;
import se2.group3.backend.dto.SessionExtractionResult;
import se2.group3.backend.exceptions.SessionOperationException;
import se2.group3.backend.model.Lobby;
import se2.group3.backend.model.Player;
import se2.group3.backend.service.LobbyService;
import se2.group3.backend.util.SessionUtil;

@Slf4j
@Controller
public class LobbyController {

    private final LobbyService lobbyService;
    private final SimpMessagingTemplate template;

    @Autowired
    public LobbyController(LobbyService lobbyService, SimpMessagingTemplate template) {
        this.lobbyService = lobbyService;
        this.template = template;
    }

    @MessageMapping("/lobby/create")
    public void createLobby(@Payload PlayerDTO host, SimpMessageHeaderAccessor headerAccessor) {
        String sessionID;
        try {
            sessionID = SessionUtil.getSessionID(headerAccessor);
        } catch (SessionOperationException e) {
            log.error(e.getMessage());
            return;
        }
        LobbyDTO lobby = lobbyService.createLobby(host, headerAccessor);

        if(lobby == null) {
            this.template.convertAndSendToUser(sessionID, "/errors", new ErrorResponse("Player is already in a lobby or the getting the lobbyID from the session attributes failed!"));
            return;
        }
        this.template.convertAndSendToUser(sessionID, "/lobbies", lobby);
    }

    @MessageMapping("/lobby/join")
    public void joinLobby(@Payload JoinLobbyRequest request, SimpMessageHeaderAccessor headerAccessor) {
        String sessionID;
        try {
            sessionID = SessionUtil.getSessionID(headerAccessor);
        } catch (SessionOperationException e) {
            log.error(e.getMessage());
            return;
        }

        LobbyDTO lobby = lobbyService.joinLobby(request.lobbyID(), request.player(), headerAccessor);
        if(lobby == null) {
            this.template.convertAndSendToUser(sessionID, "/errors", new ErrorResponse("Player could not join lobby!"));
            return;
        }
        this.template.convertAndSend("/lobbies/" + request.lobbyID(), lobby);
    }

    @MessageMapping("/lobby/leave")
    public void leaveLobby(SimpMessageHeaderAccessor headerAccessor) {
        String sessionID;
        try {
            sessionID = SessionUtil.getSessionID(headerAccessor);
        } catch (SessionOperationException e) {
            log.error(e.getMessage());
            return;
        }

        try {
            LobbyDTO lobby = lobbyService.leaveLobby(headerAccessor);
            this.template.convertAndSend("/lobbies/" + lobby.lobbyID(), lobby);
        } catch (IllegalStateException e){
            this.template.convertAndSendToUser(sessionID, "/errors", e.getMessage());
        } catch (SessionOperationException e) {
            log.error(e.getMessage());
        }
    }
}
