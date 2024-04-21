package se.group3.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.group3.backend.domain.cells.Cell;

public class BoardDTO {

    private final Cell[][] cells;

    @JsonCreator
    public BoardDTO(@JsonProperty("cells") Cell[][] cells) {
        this.cells = cells;
    }

    public Cell[][] getCells() {
        return this.cells;
    }
}
