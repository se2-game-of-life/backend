package se2.group3.backend.model.cells;

import se2.group3.backend.model.Cell;
import se2.group3.backend.model.Player;

public abstract class StopCell extends Cell {
    private boolean hasAlternativePath;
    private Cell nextCell2; // Only used if hasAlternativePath is true

    /**
     * Constructor for StopCell.
     * @param position The position of the cell on the board.
     * @param hasAlternativePath Indicates whether the cell has an alternative path.
     * @param nextCell1 The next cell to move to if the alternative path is not chosen.
     * @param nextCell2 The next cell to move to if the alternative path is chosen.
     */
    public StopCell(int position, boolean hasAlternativePath, Cell nextCell1, Cell nextCell2) {
        super(position, nextCell1);
        this.hasAlternativePath = hasAlternativePath;
        if (hasAlternativePath) {
            this.nextCell2 = nextCell2;
        }
    }

    /**
     * Checks if the stop cell has an alternative path.
     * @return True if the cell has an alternative path, otherwise false.
     */
    public boolean hasAlternativePath() {
        return hasAlternativePath;
    }

    public abstract void performAction(Player player);
}

