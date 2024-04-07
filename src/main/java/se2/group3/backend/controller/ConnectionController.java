package se2.group3.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import se2.group3.backend.exceptions.SessionOperationException;
import se2.group3.backend.util.SessionUtil;

@Slf4j
@Controller
public class ConnectionController {

    /**
     * This method sets the uuid attribute of the header to the client send playerIdentifier.
     * If this is not possible it logs the error and returns.
     * @param playerIdentifier the UUID provided by the client.
     * @param headerAccessor the header used to store the information.
     */
    @MessageMapping("/topic/setIdentifier")
    public void setPlayerIdentifier(@Payload String playerIdentifier, SimpMessageHeaderAccessor headerAccessor) {
        try {
            SessionUtil.putSessionAttribute(headerAccessor, "uuid", playerIdentifier);
        } catch (SessionOperationException e) {
            log.error(e.getMessage());
        }
    }
}