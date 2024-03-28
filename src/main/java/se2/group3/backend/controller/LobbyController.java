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

    /**
     * Handles incoming create lobby requests, which are evaluated in the {@link LobbyService}.
     * Takes in a headerAccessor to get access to the session information.
     * If the sessionID can't be extracted from the headerAccessor,
     * logs an error and terminates as no connection to the client can be made.
     * Otherwise, returns a {@link LobbyDTO} of the new lobby state to "/lobbies/"
     * or an error message to "/errors", which are both only directed to the requester.
     * @param host A {@link PlayerDTO} which has the information on the player creating the new lobby.
     * @param headerAccessor The header accessor which contains session information.
     */
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

    /**
     * Handles incoming join lobby requests, which are evaluated in the {@link LobbyService}.
     * Takes in a headerAccessor to get access to the session information.
     * If the sessionID can't be extracted from the headerAccessor,
     * logs an error and terminates as no connection to the client can be made.
     * Otherwise, returns a {@link LobbyDTO} of the new lobby state to "/lobbies/{lobbyID}"
     * or an error message to "/errors" for the user trying to join.
     * @param request A {@link JoinLobbyRequest} DTO which has the information on the player and the lobbyID.
     * @param headerAccessor The header accessor which contains session information.
     */
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

    /**
     * Handles incoming leave lobby requests, which are evaluated in the {@link LobbyService}.
     * Takes in a headerAccessor to get access to the session information.
     * If the sessionID can't be extracted from the headerAccessor,
     * logs an error and terminates as no connection to the client can be made.
     * Otherwise, returns a {@link LobbyDTO} of the new lobby state to "/lobbies/{lobbyID}"
     * or an error message to "/errors" for the user trying to leave.
     * @param headerAccessor The header accessor which contains session information.
     */
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
