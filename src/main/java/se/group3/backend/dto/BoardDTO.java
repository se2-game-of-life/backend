package se.group3.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BoardDTO {

    private final CellDTO[][] cells;

    @JsonCreator
    public BoardDTO(@JsonProperty("cells") List<CellDTO> cells) {
        // Initialize the board with dimensions 17x33
        this.cells = new CellDTO[17][33];

        // Place cells on the board
        for (CellDTO cell : cells) {
            int row = cell.getRow();
            int col = cell.getCol();
            this.cells[row][col] = cell;
        }
    }

    public CellDTO[][] getCells() {
        return cells;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (CellDTO[] row : cells) {
            for (CellDTO cell : row) {
                stringBuilder.append(cell != null ? "# " : "_ ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

}
