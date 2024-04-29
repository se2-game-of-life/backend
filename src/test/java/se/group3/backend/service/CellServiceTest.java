package se.group3.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import se.group3.backend.domain.cells.AddPegCell;
import se.group3.backend.domain.cells.Cell;
import se.group3.backend.domain.cells.ActionCell;
import se.group3.backend.repositories.CellRepository;
import se.group3.backend.services.CellService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CellServiceTest {

    private CellService cellService;
    private CellRepository cellRepository;

    @BeforeEach
    void setUp() {
        cellRepository = Mockito.mock(CellRepository.class);
        cellService = new CellService(cellRepository);
    }

    @Test
    void testCastCellsByType() {
        // Arrange
        List<Cell> inputCells = new ArrayList<>();
        inputCells.add(new Cell(1, "actionCell", new ArrayList<>(), 0, 0));
        inputCells.add(new Cell(2, "addPegCell", new ArrayList<>(), 0, 1));

        // Mock the cell repository to return the input cells
        when(cellRepository.findAll()).thenReturn(inputCells);

        // Act
        List<Cell> castedCells = cellService.getAllCells();

        // Assert
        assertEquals(ActionCell.class, castedCells.get(0).getClass());
        assertEquals(AddPegCell.class, castedCells.get(1).getClass());
    }
}

