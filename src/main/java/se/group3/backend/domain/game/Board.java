package se.group3.backend.domain.game;

import se.group3.backend.domain.cells.Cell;
import se.group3.backend.domain.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<Cell> cells;

    public Board() {
        cells = new ArrayList<>();
    }

    public Board(List<Cell> boardCells) {
        cells = new ArrayList<>(boardCells);
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

    // Override toString() method to print the contents of the board
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Board Contents:\n");
        for (int i = 0; i < cells.size(); i++) {
            stringBuilder.append("Cell ").append(i).append(": ").append(cells.get(i)).append("\n");
        }
        return stringBuilder.toString();
    }
}
