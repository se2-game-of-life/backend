package se2.group3.backend.domain.cells;

import java.util.List;

public abstract class StopCell extends Cell {

    protected StopCell(int position, List<Integer> nextCells) {
        super(position, nextCells);
    }


}

