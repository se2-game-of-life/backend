package se2.group3.backend.domain.cells;

import lombok.extern.slf4j.Slf4j;
import se2.group3.backend.domain.player.Player;

import java.util.List;

@Slf4j
public class GetMarriedStopCell extends StopCell {
    public GetMarriedStopCell(int position, List<Integer> nextCells) {
        super(position, nextCells);
    }

    @Override
    public void performAction(Player player) {
        log.debug("Landed on a Get Married cell");
    }
}
