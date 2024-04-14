package se.group3.backend.dto.mapper;

import se.group3.backend.domain.cells.Cell;
import se.group3.backend.dto.CellDTO;



public class CellMapper {
    private CellMapper() {
        throw new UnsupportedOperationException("This class cannot be instantiated!");
    }

    public static Cell toCellModel(Cell cell) {
        return new Cell(cell.getPosition(), cell.getNextCells());
    }
    public static CellDTO toCellDTO(Cell cell) {
        return new CellDTO(cell.getId(), cell.getPosition(), cell.getType(), cell.getNextCells());
    }
}
