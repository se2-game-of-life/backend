package se2.group3.backend.model.cells;

import se2.group3.backend.model.Cell;
import se2.group3.backend.model.Player;

import java.util.List;

public abstract class StopCell extends Cell {

    public StopCell(int position, List<Integer> nextCells) {
        super(position, nextCells);
    }


}

