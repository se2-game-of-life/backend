package se.group3.backend.dto.mapper;

import se.group3.backend.domain.cells.Cell;
import se.group3.backend.dto.BoardDTO;

public class BoardMapper {
    private BoardMapper() {
        throw new UnsupportedOperationException("This class cannot be instantiated!");
    }

    public static BoardDTO toBoardDTO(Cell[][] cells) {
        return new BoardDTO(cells);
    }

    // If you need to convert from DTO to domain model, you can add a method for that too
    // For simplicity, let's assume you only need conversion from domain model to DTO in this case
}
