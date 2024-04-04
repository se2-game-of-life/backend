package se2.group3.backend.model.cells;

import se2.group3.backend.model.Cell;
import se2.group3.backend.model.Player;

public class SpinToGraduateStopCell extends StopCell {
    public SpinToGraduateStopCell(int position, boolean hasAlternativePath, Cell nextCell1, Cell nextCell2) {
        super(position, hasAlternativePath, nextCell1, nextCell2);
    }

    @Override
    public void performAction(Player player) {
        System.out.println("Landed on a Spin to Graduate cell");
        // TODO: Implement logic for "Spin to graduate" action
    }
}
