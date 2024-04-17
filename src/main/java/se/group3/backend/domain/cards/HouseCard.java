package se.group3.backend.domain.cards;

import se.group3.backend.domain.player.Player;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "HouseCards")
public class HouseCard extends Card {
    private int purchasePrice;
    private int redSellPrice;
    private int blackSellPrice;
    public HouseCard(String name, int purchasePrice, int redSellPrice, int blackSellPrice) {
        super(name);
        this.purchasePrice = purchasePrice;
        this.redSellPrice = redSellPrice;
        this.blackSellPrice = blackSellPrice;
    }

    public int getPurchasePrice() {
        return purchasePrice;
    }

    public int getRedSellPrice() {
        return redSellPrice;
    }

    public int getBlackSellPrice() {
        return blackSellPrice;
    }

    @Override
    public void performAction(Player player, Deck<HouseCard> deck) {
        // Draw two cards from the deck
        HouseCard card1 = deck.drawCard();
        HouseCard card2 = deck.drawCard();

        // TODO: get chosen card from frontend project
        System.out.println("Choose a house to buy:");
        System.out.println("1. " + card1);
        System.out.println("2. " + card2);

        // Assume player chooses the first card for simplicity
        HouseCard chosenCard = card1;
        HouseCard unchosenCard = card2;

        // Remember the player's choice
        player.assignHouseCard(chosenCard);

        // Return the unchosen card to the deck
        deck.addCard(unchosenCard);
    }

    @Override
    public String toString() {
        return "HouseCard{" +
                "id='" + getId() + '\'' +
                "name='" + getName() + '\'' +
                '}';
    }
}
