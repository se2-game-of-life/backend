package se.group3.backend.domain.cells.stopcells;

import lombok.extern.slf4j.Slf4j;
import se.group3.backend.domain.player.Player;

import java.util.List;

@Slf4j
public class MidLifeStopCell extends StopCell {
    public MidLifeStopCell(int position, List<Integer> nextCells) {
        super(position, nextCells);
    }

    @Override
    public void performAction(Player player) {
        log.debug("Landed on a Mid Life Spin-out cell");
    }
}
