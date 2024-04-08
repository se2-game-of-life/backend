package se2.group3.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import se2.group3.backend.dto.*;
import se2.group3.backend.exceptions.SessionOperationException;
import se2.group3.backend.services.LobbyService;
import se2.group3.backend.util.SerializationUtil;
import se2.group3.backend.util.SessionUtil;

@Slf4j
@Controller
public class LobbyController {

    private final LobbyService lobbyService;
    private final SimpMessagingTemplate template;

    private static final String ERROR_PATH = "/topic/errors/";
    private static final String LOBBIES_PATH = "/topic/lobbies/";

    @Autowired
    public LobbyController(LobbyService lobbyService, SimpMessagingTemplate template) {
        this.lobbyService = lobbyService;
        this.template = template;
    }

    /**
     * Handles incoming create lobby requests, which are evaluated in the {@link LobbyService}.
     * Takes in a headerAccessor to get access to the session information.
     * If the uuid can't be extracted from the headerAccessor,
     * logs an error and terminates as no connection to the client can be made.
     * Otherwise, returns a {@link LobbyDTO} of the new lobby state to "/lobbies/"
     * or an error message (which might include an error due to serialization) to "/errors", which are both only directed to the requester.
     * @param playerDTO A String which has the information on the player creating the new lobby.
     * @param headerAccessor The header accessor which contains session information.
     */
    @MessageMapping("/lobby/create")
    public void createLobby(@Payload String playerDTO, SimpMessageHeaderAccessor headerAccessor) {
        String uuid;
        try {
            uuid = SessionUtil.getUUID(headerAccessor);
        } catch (SessionOperationException e) {
            log.error(e.getMessage());
            return;
        }

        try {
            LobbyDTO lobby = lobbyService.createLobby((PlayerDTO) SerializationUtil.toObject(playerDTO, PlayerDTO.class), headerAccessor);
            this.template.convertAndSend(LOBBIES_PATH + uuid, SerializationUtil.jsonStringFromClass(lobby));
        } catch (IllegalStateException | JsonProcessingException | ClassCastException e) {
            this.template.convertAndSend(ERROR_PATH + uuid, new ErrorResponse(e.getMessage()));
        } catch (SessionOperationException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Handles incoming join lobby requests, which are evaluated in the {@link LobbyService}.
     * Takes in a headerAccessor to get access to the session information.
     * If the uuid can't be extracted from the headerAccessor,
     * logs an error and terminates as no connection to the client can be made.
     * Otherwise, returns a {@link LobbyDTO} of the new lobby state to "/lobbies/{lobbyID}"
     * or an error message (which might include an error due to serialization) to "/errors" for the user trying to join.
     * @param joinLobbyRequest A String which has the information on the player and the lobbyID.
     * @param headerAccessor The header accessor which contains session information.
     */
    @MessageMapping("/lobby/join")
    public void joinLobby(@Payload String joinLobbyRequest, SimpMessageHeaderAccessor headerAccessor) {
        String uuid;
        try {
            uuid = SessionUtil.getUUID(headerAccessor);
        } catch (SessionOperationException e) {
            log.error(e.getMessage());
            return;
        }

        try {
            JoinLobbyRequest request = (JoinLobbyRequest) SerializationUtil.toObject(joinLobbyRequest, JoinLobbyRequest.class);
            LobbyDTO lobby = lobbyService.joinLobby(request.getLobbyID(), request.getPlayer(), headerAccessor);
            this.template.convertAndSend(LOBBIES_PATH + lobby.getLobbyID(), SerializationUtil.jsonStringFromClass(lobby));
        } catch (IllegalStateException | JsonProcessingException | ClassCastException e){
            this.template.convertAndSend(ERROR_PATH + uuid, e.getMessage());
        } catch (SessionOperationException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Handles incoming leave lobby requests, which are evaluated in the {@link LobbyService}.
     * Takes in a headerAccessor to get access to the session information.
     * If the uuid can't be extracted from the headerAccessor,
     * logs an error and terminates as no connection to the client can be made.
     * Otherwise, returns a {@link LobbyDTO} of the new lobby state to "/lobbies/{lobbyID}"
     * or an error message (which might include an error due to serialization) to "/errors" for the user trying to leave.
     * @param headerAccessor The header accessor which contains session information.
     */
    @MessageMapping("/lobby/leave")
    public void leaveLobby(SimpMessageHeaderAccessor headerAccessor) {
        String uuid;
        try {
            uuid = SessionUtil.getUUID(headerAccessor);
        } catch (SessionOperationException e) {
            log.error(e.getMessage());
            return;
        }

        try {
            LobbyDTO lobby = lobbyService.leaveLobby(headerAccessor);
            this.template.convertAndSend(LOBBIES_PATH + lobby.getLobbyID(), SerializationUtil.jsonStringFromClass(lobby));
        } catch (IllegalStateException | JsonProcessingException e){
            this.template.convertAndSend(ERROR_PATH + uuid, e.getMessage());
        } catch (SessionOperationException e) {
            log.error(e.getMessage());
        }
    }
}
