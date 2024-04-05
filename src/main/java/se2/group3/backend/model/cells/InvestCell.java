package se2.group3.backend.model.cells;

import se2.group3.backend.model.Cell;
import se2.group3.backend.model.Player;

import java.util.List;

// Cell for choosing to invest
public class InvestCell extends Cell {
    public InvestCell(int position, List<Integer> nextCells) {
        super(position, nextCells);
    }


    @Override
    public void performAction(Player player) {
        // Implement logic for investment
        int investmentNumber = 0; // TODO: determine the investment number
        player.invest(investmentNumber);
    }
}

