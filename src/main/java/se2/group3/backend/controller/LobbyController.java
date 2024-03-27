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
    public void createLobby(SimpMessageHeaderAccessor headerAccessor) {
        SessionExtractionResult session;
        try {
            session = SessionUtil.extractSessionDetails(headerAccessor);
        } catch (SessionOperationException e) {
            log.error(e.getMessage());
            return;
        }

        Object player = session.sessionAttributes().get("player");
        if(!(player instanceof Player)) {
            //todo: type wrong or null -> send error and make user create a player in the session first
            return;
        }

        Lobby lobby = lobbyService.createLobby((Player) player);
        this.template.convertAndSendToUser(session.sessionID(), "/topic/lobbies", lobby.getId());
    }

    @MessageMapping("/lobby/join")
    public void joinLobby(@Payload JoinLobbyRequest request, SimpMessageHeaderAccessor headerAccessor) {
        SessionExtractionResult session;
        try {
            session = SessionUtil.extractSessionDetails(headerAccessor);
        } catch (SessionOperationException e) {
            log.error(e.getMessage());
            return;
        }



        if(session.sessionAttributes().get("lobbyID") != null) {
            log.info("Cant join new lobby, whilst still in a different lobby!");
            this.template.convertAndSendToUser(session.sessionID(), "/errors", new ErrorResponse("Already in a lobby!"));
            return;
        }

        //todo: do checks on the cast and make sure
        try {
            Lobby lobby = lobbyService.joinLobby(request.getLobbyID(), (Player) session.sessionAttributes().get("player"));
            session.sessionAttributes().put("lobbyID", lobby.getId());
            this.template.convertAndSend("/client/lobbies/" + request.getLobbyID(), new LobbyStateUpdate(lobby));
        } catch (Exception e) {
            log.error("Error adding player to lobby!");
            this.template.convertAndSendToUser(session.sessionID(), "/errors", new ErrorResponse(e.getMessage()));
        }
    }

    @MessageMapping("/lobby/leave")
    public void leaveLobby(SimpMessageHeaderAccessor headerAccessor) throws SessionOperationException {
        SessionExtractionResult session = SessionUtil.extractSessionDetails(headerAccessor);

        Long lobbyID;
        Player player;
        try {
            lobbyID = (Long) session.sessionAttributes().get("lobbyID");
            player = (Player) session.sessionAttributes().get("player");
        } catch (ClassCastException e) {
            log.error("Session attribute is not convertable to correct type!");
            this.template.convertAndSendToUser(session.sessionID(), "/errors", new ErrorResponse("Session attributes have the wrong type!"));
            return;
        }

        if(lobbyID == null || player == null) {
            log.info("Attempting to remove player from a lobby, whilst player is not associated with any lobby!");
            this.template.convertAndSendToUser(session.sessionID(), "/errors", new ErrorResponse("Unable to leave a lobby, because player is not in a lobby!"));
            return;
        }

        session.sessionAttributes().remove("lobbyID");

        if(!lobbyService.leaveLobby(lobbyID, player)) {
            log.error("Trying to leave non-existing Lobby!");
            this.template.convertAndSendToUser(session.sessionID(), "/errors", new ErrorResponse("Unable to leave a lobby, because player is not in a lobby!"));
            return;
        }

        Lobby lobby = lobbyService.getLobby(lobbyID);
        if(lobby != null) {
            this.template.convertAndSend("/client/lobbies/" + lobbyID, new LobbyStateUpdate(lobby));
        }
    }
}
