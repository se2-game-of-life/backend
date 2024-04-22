package se.group3.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.group3.backend.domain.cells.Cell;

import java.util.List;

public class BoardDTO {

    private final Cell[][] cells;

    @JsonCreator
    public BoardDTO(@JsonProperty("cells") List<Cell> cells) {
        // Initialize the board with dimensions 17x33
        this.cells = new Cell[17][33];

        // Place cells on the board
        for (Cell cell : cells) {
            int row = cell.getRow();
            int col = cell.getCol();
            this.cells[row][col] = cell;
        }
    }

    public Cell[][] getCells() {
        return cells;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                stringBuilder.append(cell != null ? "# " : "_ ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

}
