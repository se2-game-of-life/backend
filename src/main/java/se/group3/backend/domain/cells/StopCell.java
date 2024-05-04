package se.group3.backend.domain.cells;

import java.util.List;

public abstract class StopCell extends Cell {

    protected StopCell(int number, String type, List<Integer> nextCells, int row, int col) {
        super(number, type, nextCells, row, col);
    }



}

