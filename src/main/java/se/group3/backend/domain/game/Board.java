package se.group3.backend.domain.game;

import se.group3.backend.domain.player.Player;

import java.util.List;


public class Board {
    private final Cell[][] cells;

    public Board(List<Cell> cells) {
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


    // Override toString() method to print the contents of the board
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Board Contents:\n");
        for (Cell[] cells : cells) {
            for (Cell cell : cells) {
                stringBuilder.append(cell != null ? "# " : "_ ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
