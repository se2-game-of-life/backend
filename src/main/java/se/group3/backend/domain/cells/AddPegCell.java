package se.group3.backend.domain.cells;

import se.group3.backend.domain.player.Player;

import java.util.List;

// Cell for adding a peg to the car
public class AddPegCell extends Cell {
    public AddPegCell(int number, String type, List<Integer> nextCells, int row, int col) {
        super(number, type, nextCells, row, col);
    }


    @Override
    public void performAction(Player player) {
        // Add a peg to the player's car
    }
}
