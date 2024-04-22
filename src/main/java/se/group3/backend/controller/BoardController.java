package se.group3.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import se.group3.backend.dto.BoardDTO;
import se.group3.backend.services.BoardService;
import se.group3.backend.util.SessionUtil;
import se.group3.backend.exceptions.SessionOperationException;
import se.group3.backend.util.SerializationUtil;

@Slf4j
@Controller
public class BoardController {

    private final BoardService boardService;
    private final SimpMessagingTemplate template;

    private static final String ERROR_PATH = "/topic/errors/";
    private static final String BOARD_PATH = "/topic/board/";

    @Autowired
    public BoardController(BoardService boardService, SimpMessagingTemplate template) {
        this.boardService = boardService;
        this.template = template;
    }

    /**
     * Handles incoming fetch board requests.
     * Fetches board data from {@link BoardService} and sends it to the client.
     */
    @MessageMapping("/board/fetch")
    public void fetchBoardData(SimpMessageHeaderAccessor headerAccessor) {
        String uuid;
        try {
            uuid = SessionUtil.getUUID(headerAccessor);
        } catch (SessionOperationException e) {
            log.error(e.getMessage());
            return;
        }

        try {
            BoardDTO boardDTO = boardService.fetchBoardData();
            String jsonBoardDTO = SerializationUtil.jsonStringFromClass(boardDTO);
            this.template.convertAndSend(BOARD_PATH + uuid, jsonBoardDTO);
        } catch (Exception e) {
            this.template.convertAndSend(ERROR_PATH + uuid, e.getMessage());
        }
    }
}
