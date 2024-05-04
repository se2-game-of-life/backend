package se.group3.backend.domain.cells;

import se.group3.backend.domain.player.Player;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class SpinToGraduateStopCell extends StopCell {
    public SpinToGraduateStopCell(int number, String type, List<Integer> nextCells, int row, int col) {
        super(number, type, nextCells, row, col);
    }


    @Override
    public void performAction(Player player) {
        log.debug("Landed on a Spin to Graduate cell");
        // TODO: Implement logic for "Spin to graduate" action
    }
}
