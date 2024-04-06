package se2.group3.backend.domain.cells;

import se2.group3.backend.domain.player.Player;

import java.util.List;

public class GetMarriedStopCell extends StopCell {
    public GetMarriedStopCell(int position, List<Integer> nextCells) {
        super(position, nextCells);
    }

    @Override
    public void performAction(Player player) {
        System.out.println("Landed on a Get Married cell");
        // TODO: Implement logic for "Get married?" action
    }
}
