package se.group3.backend.domain.cells;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import se.group3.backend.domain.game.Deck;
import se.group3.backend.domain.player.Player;
import se.group3.backend.domain.cards.Card;

import java.util.List;
@Slf4j
@Document(collection = "Cells")
public class Cell {
    @Id
    protected String id;
    protected int position;
    private String type;
    protected List<Integer> nextCells;

    public Cell() {
        // Default constructor
    }

    public Cell(int position, List<Integer> nextCells) {
        this.position = position;
        this.nextCells = nextCells;
    }

    public Cell(int position, List<Integer> nextCells, String type) {
        this.position = position;
        this.nextCells = nextCells;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public int getPosition() {
        return position;
    }

    public List<Integer> getNextCells() {
        return nextCells;
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

