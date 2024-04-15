package se.group3.backend.domain.cells.stopcells;

import lombok.extern.slf4j.Slf4j;
import se.group3.backend.domain.player.Player;

import java.util.List;

@Slf4j
public class RetireEarlyStopCell extends StopCell {
    public RetireEarlyStopCell(int position, List<Integer> nextCells) {
        super(position, nextCells);
    }

    @Override
    public void performAction(Player player) {
        log.debug("Landed on a Retire Early cell");
    }
}
