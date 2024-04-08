package se.group3.backend.domain.cells;

import se.group3.backend.domain.player.Player;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class SpinToGraduateStopCell extends StopCell {
    public SpinToGraduateStopCell(int position, List<Integer> nextCells) {
        super(position, nextCells);
    }

    @Override
    public void performAction(Player player) {
        log.debug("Landed on a Spin to Graduate cell");
        // TODO: Implement logic for "Spin to graduate" action
    }
}
