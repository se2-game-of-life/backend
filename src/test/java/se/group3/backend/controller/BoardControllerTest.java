package se.group3.backend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.junit.jupiter.api.Assertions;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import se.group3.backend.domain.game.Cell;
import se.group3.backend.dto.BoardDTO;
import se.group3.backend.services.BoardService;

import java.util.*;


class BoardControllerTest {

    private BoardService boardService;
    private SimpMessagingTemplate messagingTemplate;
    private BoardController boardController;

    @BeforeEach
    void setUp() {
        boardService = Mockito.mock(BoardService.class);
        messagingTemplate = Mockito.mock(SimpMessagingTemplate.class);
        boardController = new BoardController(boardService, messagingTemplate);
    }

    @Test
    void fetchBoardData() {
        SimpMessageHeaderAccessor headerAccessor = Mockito.mock(SimpMessageHeaderAccessor.class);
        BoardDTO boardDTO = createMockBoardDTO(); // Create a mock BoardDTO or use a real one if possible

        Map<String, Object> sessionAttributes = new HashMap<>();
        sessionAttributes.put("uuid", "TestUUID");
        Mockito.when(headerAccessor.getSessionAttributes()).thenReturn(sessionAttributes);

        try {
            Mockito.when(boardService.fetchBoardData()).thenReturn(boardDTO);
        } catch (Exception e) {
            Assertions.fail(e);
        }

        boardController.fetchBoardData(headerAccessor);

        Mockito.verify(messagingTemplate).convertAndSend(Mockito.eq("/topic/board/TestUUID"), Mockito.anyString());
    }

    private BoardDTO createMockBoardDTO() {
        // Create a mock BoardDTO with some cells populated for testing
        // For simplicity, let's create a 3x3 board with some cells
        List<Cell> cells = new ArrayList<>();
        int cellNumber = 1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Cell cell = new Cell(cellNumber++, "actionCell", Collections.singletonList(cellNumber + 1), i, cellNumber);
                cells.add(cell);
            }
        }
        return new BoardDTO(cells);
    }


}
