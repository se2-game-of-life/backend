package se.group3.backend.domain.cells;

import se.group3.backend.domain.player.Player;

import java.util.List;

// Cell for choosing to invest
public class InvestCell extends Cell {
    public InvestCell(int position, List<Integer> nextCells) {
        super(position, nextCells);
    }


    @Override
    public void performAction(Player player) {
        // Implement logic for investment
        int investmentNumber = 0;
        // determine the investment number
        throw new UnsupportedOperationException();
    }
}

