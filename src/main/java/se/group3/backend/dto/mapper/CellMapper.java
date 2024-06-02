package se.group3.backend.dto.mapper;

import se.group3.backend.domain.Cell;
import se.group3.backend.dto.CellDTO;

public class CellMapper {

    private CellMapper() {
        throw new UnsupportedOperationException("This class cannot be instantiated!");
    }

    public static CellDTO toCellDTO(Cell cell) {
        return new CellDTO(
                cell.getId(),
                cell.getNumber(),
                cell.getType().toString(),
                cell.getNextCells(),
                cell.getRow(),
                cell.getCol()
        );
    }
}
