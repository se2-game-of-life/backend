package se2.group3.backend.model.cells;

import se2.group3.backend.model.Cell;
import se2.group3.backend.model.Player;

// Cell for adding a peg to the car
public class AddPegCell extends Cell {
    public AddPegCell(int position) {
        super(position);
    }

    @Override
    public void performAction(Player player) {
        // Add a peg to the player's car
    }
}
