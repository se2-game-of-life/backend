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

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public  void performAction(Player player){
        throw new UnsupportedOperationException();
    }

    public  <T extends Card> void performAction(Player player, Deck<T> cardDeck){
        throw new UnsupportedOperationException();
    }

    // Shared method for drawing a card and performing its action
    protected <T extends Card> void drawAndPerformCardAction(Player player, Deck<T> cardDeck) {
        T drawnCard = cardDeck.drawCard();
        log.debug("{} draws a card: {}", player.getPlayerName(), drawnCard.getName());
        drawnCard.performAction(player);
    }

}

