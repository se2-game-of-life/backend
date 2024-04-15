package se.group3.backend.domain.cells.stopcells;

import se.group3.backend.domain.cells.Cell;

import java.util.List;

public abstract class StopCell extends Cell {

    protected StopCell(int position, List<Integer> nextCells) {
        super(position, nextCells);
    }


}

