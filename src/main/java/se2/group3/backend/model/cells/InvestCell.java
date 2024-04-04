package se2.group3.backend.model.cells;

import se2.group3.backend.model.Cell;
import se2.group3.backend.model.Player;

// Cell for choosing to invest
public class InvestCell extends Cell {
    public InvestCell(int position) {
        super(position);
    }

    @Override
    public void performAction(Player player) {
        // Implement logic for investment
        int investmentNumber = 0; // TODO: determine the investment number
        player.invest(investmentNumber);
    }
}

