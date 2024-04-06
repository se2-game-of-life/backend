package se2.group3.backend.domain.cells;

import se2.group3.backend.domain.player.Player;

import java.util.List;

// Cell for adding a peg to the car
public class AddPegCell extends Cell {
    public AddPegCell(int position, List<Integer> nextCells) {
        super(position, nextCells);
    }

    @Override
    public void performAction(Player player) {
        // Add a peg to the player's car
    }
}
