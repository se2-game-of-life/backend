package se.group3.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import se.group3.backend.domain.Cell;
import se.group3.backend.domain.CellType;
import se.group3.backend.dto.BoardDTO;
import se.group3.backend.repositories.CellRepository;
import se.group3.backend.services.BoardService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class BoardServiceTest {

    private BoardService boardService;
    private CellRepository cellRepository;

    @BeforeEach
    void setUp() {
        cellRepository = Mockito.mock(CellRepository.class);
        boardService = new BoardService(cellRepository);
    }

    @Test
    void fetchBoardData_WhenNoCellsFound() {
        // Arrange
        when(cellRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        BoardDTO boardDTO = boardService.fetchBoardData();

        // Assert
        assertNull(boardDTO.getCells()[0][0]);
    }

    @Test
    void fetchBoardData_WithCells() {
        // Arrange
        List<Cell> cells = new ArrayList<>();
        // Adjust the cells to match the dimensions of the board (17x33)
        cells.add(new Cell(1, CellType.CASH, List.of(), 0, 0));  // Adjust row and col values
        cells.add(new Cell(2, CellType.ACTION, List.of(), 0, 1));  // Adjust row and col values
        when(cellRepository.findAll()).thenReturn(cells);

        // Act
        BoardDTO boardDTO = boardService.fetchBoardData();

        // Assert
        assertEquals(17, boardDTO.getCells().length);
        assertEquals(33, boardDTO.getCells()[0].length);
        assertEquals(CellType.CASH, boardDTO.getCells()[0][0].getType());
        assertEquals(CellType.ACTION, boardDTO.getCells()[0][1].getType());
    }
}
