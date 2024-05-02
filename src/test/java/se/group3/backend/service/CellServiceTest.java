package se.group3.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import se.group3.backend.domain.cells.*;
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
        inputCells.add(new Cell(3, "careerCell", new ArrayList<>(), 0, 2));
        inputCells.add(new Cell(4, "finalRetirementCell", new ArrayList<>(), 0, 3));
        inputCells.add(new Cell(5, "getMarriedStopCell", new ArrayList<>(), 0, 4));
        inputCells.add(new Cell(6, "houseCell", new ArrayList<>(), 0, 5));
        inputCells.add(new Cell(7, "investCell", new ArrayList<>(), 0, 6));
        inputCells.add(new Cell(8, "paydayCell", new ArrayList<>(), 0, 7));
        inputCells.add(new Cell(9, "spinToGraduateStopCell", new ArrayList<>(), 0, 8));
        // Add more test cells for any additional types...

        // Mock the cell repository to return the input cells
        when(cellRepository.findAll()).thenReturn(inputCells);

        // Act
        List<Cell> castedCells = cellService.getAllCells();

        // Assert
        assertEquals(ActionCell.class, castedCells.get(0).getClass());
        assertEquals(AddPegCell.class, castedCells.get(1).getClass());
        assertEquals(CareerCell.class, castedCells.get(2).getClass());
        assertEquals(FinalRetirementCell.class, castedCells.get(3).getClass());
        assertEquals(GetMarriedStopCell.class, castedCells.get(4).getClass());
        assertEquals(HouseCell.class, castedCells.get(5).getClass());
        assertEquals(InvestCell.class, castedCells.get(6).getClass());
        assertEquals(PaydayCell.class, castedCells.get(7).getClass());
        assertEquals(SpinToGraduateStopCell.class, castedCells.get(8).getClass());
        // Add more assertions for any additional types...
    }

}

