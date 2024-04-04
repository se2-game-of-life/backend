package se2.group3.backend.model;

public abstract class Cell {
    protected int position;
    protected Cell nextCell;

    public Cell(int position) {
        this.position = position;
    }

    public Cell(int position, Cell nextCell) {
        this.position = position;
        this.nextCell = nextCell;
    }

    public void setNextCell(Cell nextCell) {
        this.nextCell = nextCell;
    }

    public abstract void performAction(Player player);

}

