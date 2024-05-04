package se.group3.backend.domain.cells;

import lombok.extern.slf4j.Slf4j;
import se.group3.backend.domain.player.Player;

import java.util.List;

@Slf4j
public class GetMarriedStopCell extends StopCell {
    public GetMarriedStopCell(int number, String type, List<Integer> nextCells, int row, int col) {
        super(number, type, nextCells, row, col);
    }


    @Override
    public void performAction(Player player) {
        log.debug("Landed on a Get Married cell");
    }
}
