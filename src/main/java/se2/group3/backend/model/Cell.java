package se2.group3.backend.model;

import org.springframework.data.annotation.Id;

import java.util.List;

public abstract class Cell {
    @Id
    protected String id;
    protected int position;
    protected List<Integer> nextCells;

    public Cell(int position, List<Integer> nextCells) {
        this.position = position;
        this.nextCells = nextCells;
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

