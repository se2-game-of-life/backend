package se2.group3.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
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
    public  void performAction(Player player){};
    public  <T extends Card> void performAction(Player player, Deck<T> cardDeck){};

    // Shared method for drawing a card and performing its action
    protected <T extends Card> void drawAndPerformCardAction(Player player, Deck<T> cardDeck) {
        T drawnCard = cardDeck.drawCard();
        System.out.println(player.getPlayerName() + " draws a card: " + drawnCard.getName());
        drawnCard.performAction(player);
    }

}

