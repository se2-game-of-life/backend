package se.group3.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.group3.backend.domain.cells.Cell;

import java.util.List;

public class BoardDTO {

    private final Cell[][] board;

    @JsonCreator
    public BoardDTO(@JsonProperty("cells") List<Cell> cells) {
        // Initialize the board with dimensions 17x33
        this.board = new Cell[17][33];

        // Place cells on the board
        for (Cell cell : cells) {
            int row = cell.getRow();
            int col = cell.getCol();
            this.board[row][col] = cell;
        }
    }

    public Cell[][] getBoard() {
        return board;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Cell[] row : board) {
            for (Cell cell : row) {
                sb.append(cell != null ? cell.getType() : "0");
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
