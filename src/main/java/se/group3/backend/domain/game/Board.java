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
    // Function to search for a cell by number and return it
    public Cell findCellByNumber(int cellNumber) {
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (cell != null && cell.getNumber() == cellNumber) {
                    return cell;
                }
            }
        }
        // Return null if cell with given number is not found
        return null;
    }

    // Move the current player to the next cell based on the spinner result
    public void movePlayer(Player currentPlayer, int steps) {
        // TODO: use findCellByNumber to get cell row and col from cell number, this is useful to know the coordinates to place the player
        // TODO: edit moving to account for STOP and PayDay cells
        /*
        int currentPosition = currentPlayer.getCurrentCellPosition(); // Cell position is cell.number
        int newPosition = (currentPosition + steps) % cells.size();
        currentPlayer.setCurrentCellPosition(newPosition);
        Cell currentCell = cells.get(newPosition);
        currentCell.performAction(currentPlayer);*/
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
