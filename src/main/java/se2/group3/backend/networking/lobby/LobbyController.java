package se2.group3.backend.networking.lobby;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import se2.group3.backend.dto.*;

import java.util.Map;

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
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        String sessionID = headerAccessor.getSessionId();
        if(sessionID == null) {
            log.error("Session ID missing!");
            return;
        }
        if(sessionAttributes == null) {
            log.error("Session attributes missing!");
            return;
        }

        //todo: do checks on the cast and make sure
        Lobby lobby = lobbyService.createLobby((Player) sessionAttributes.get("player"));
    }

    @MessageMapping("/lobby/join")
    public void joinLobby(@Payload JoinLobbyRequest request, SimpMessageHeaderAccessor headerAccessor) {
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        String sessionID = headerAccessor.getSessionId();
        if(sessionID == null) {
            log.error("Session ID missing!");
            return;
        }
        if(sessionAttributes == null) {
            log.error("Session attributes missing!");
            return;
        }

        if(sessionAttributes.get("lobbyID") != null) {
            log.info("Cant join new lobby, whilst still in a different lobby!");
            this.template.convertAndSendToUser(sessionID, "/errors", new ErrorResponse("Already in a lobby!"));
            return;
        }

        //todo: do checks on the cast and make sure
        try {
            Lobby lobby = lobbyService.joinLobby(request.getLobbyID(), (Player) sessionAttributes.get("player"));
            sessionAttributes.put("lobbyID", lobby.getId());
            this.template.convertAndSend("/client/lobbies/" + request.getLobbyID(), new LobbyStateUpdate(lobby));
        } catch (Exception e) {
            log.error("Error adding player to lobby!");
            this.template.convertAndSendToUser(sessionID, "/errors", new ErrorResponse(e.getMessage()));
        }
    }

    @MessageMapping("/lobby/leave")
    public void leaveLobby(SimpMessageHeaderAccessor headerAccessor) {
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        String sessionID = headerAccessor.getSessionId();
        if(sessionID == null) {
            log.error("Session ID missing!");
            return;
        }
        if(sessionAttributes == null) {
            log.error("Session attributes missing!");
            return;
        }

        Long lobbyID;
        Player player;
        try {
            lobbyID = (Long) sessionAttributes.get("lobbyID");
            player = (Player) sessionAttributes.get("player");
        } catch (ClassCastException e) {
            log.error("Session attribute is not convertable to correct type!");
            this.template.convertAndSendToUser(sessionID, "/errors", new ErrorResponse("Session attributes have the wrong type!"));
            return;
        }

        if(lobbyID == null || player == null) {
            log.info("Attempting to remove player from a lobby, whilst player is not associated with any lobby!");
            this.template.convertAndSendToUser(sessionID, "/errors", new ErrorResponse("Unable to leave a lobby, because player is not in a lobby!"));
            return;
        }

        sessionAttributes.remove("lobbyID");

        if(!lobbyService.leaveLobby(lobbyID, player)) {
            log.error("Trying to leave non-existing Lobby!");
            this.template.convertAndSendToUser(sessionID, "/errors", new ErrorResponse("Unable to leave a lobby, because player is not in a lobby!"));
            return;
        }

        Lobby lobby = lobbyService.getLobby(lobbyID);
        if(lobby != null) {
            this.template.convertAndSend("/client/lobbies/" + lobbyID, new LobbyStateUpdate(lobby));
        }
    }
}
