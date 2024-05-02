package se.group3.backend.domain.cells;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import se.group3.backend.domain.player.Player;
import se.group3.backend.domain.game.Deck;
import se.group3.backend.domain.cards.Card;

import java.util.List;

@Slf4j
@Document(collection = "Cells")
public class Cell {
    @Id
    protected String id;
    protected int number;
    private String type;
    protected List<Integer> nextCells;
    private int row;
    private int col;

    public Cell(int number, String type, List<Integer> nextCells, int row, int col) {
        this.number = number;
        this.type = type;
        this.nextCells = nextCells;
        this.row = row;
        this.col = col;
    }

    public Cell() {
        // Default constructor
    }


    public String getType() {
        return type;
    }

    public int getNumber() {
        return number;
    }

    public List<Integer> getNextCells() {
        return nextCells;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public String getId() {
        return id;
    }
}

