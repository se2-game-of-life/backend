package se.group3.backend.domain.cells;


import se.group3.backend.domain.player.Player;

import java.util.List;

// Cell for final retirement
public class FinalRetirementCell extends Cell {
    public FinalRetirementCell(int position, List<Integer> nextCells) {
        super(position, nextCells);
    }


    @Override
    public void performAction(Player player) {
        // Implement logic for final retirement
    }
}
