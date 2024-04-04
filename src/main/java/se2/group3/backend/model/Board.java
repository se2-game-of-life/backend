package se2.group3.backend.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<Cell> cells;

    public Board() {
        cells = new ArrayList<>();
    }

    // Add a cell to the board
    public void addCell(Cell cell) {
        cells.add(cell);
    }

    // Move the current player to the next cell based on the spinner result
    public void movePlayer(Player currentPlayer, int steps) {
        int currentPosition = currentPlayer.getCurrentCellPosition();
        int newPosition = (currentPosition + steps) % cells.size(); // TODO: edit moving to account for STOP and PayDay cells
        currentPlayer.setCurrentCellPosition(newPosition);
        Cell currentCell = cells.get(newPosition);
        currentCell.performAction(currentPlayer);
    }
}
