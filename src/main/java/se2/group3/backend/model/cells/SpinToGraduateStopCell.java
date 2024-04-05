package se2.group3.backend.model.cells;

import se2.group3.backend.model.Cell;
import se2.group3.backend.model.Player;

import java.util.List;

public class SpinToGraduateStopCell extends StopCell {
    public SpinToGraduateStopCell(int position, List<Integer> nextCells) {
        super(position, nextCells);
    }

    @Override
    public void performAction(Player player) {
        System.out.println("Landed on a Spin to Graduate cell");
        // TODO: Implement logic for "Spin to graduate" action
    }
}
