package se2.group3.backend.model.cells;


import se2.group3.backend.model.Cell;
import se2.group3.backend.model.Player;

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
