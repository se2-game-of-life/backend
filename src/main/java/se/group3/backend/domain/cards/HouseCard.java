package se.group3.backend.domain.cards;

import se.group3.backend.domain.game.Deck;
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
    public void performAction(Player player) {
        // Implement the action specific to HouseCard
        // You need to implement the logic for the player to choose between two cards here
        Deck<HouseCard> deck = getHouseCardDeck(); // Assuming you have a method to get the house card deck
        HouseCard card1 = deck.drawCard();
        HouseCard card2 = deck.drawCard();

        System.out.println("Choose a house to buy:");
        System.out.println("1. " + card1.getName());
        System.out.println("2. " + card2.getName());

        // Example: Let's assume the player always chooses the first card for simplicity
        HouseCard chosenCard = card1;
        HouseCard unchosenCard = card2;

        if (player.getMoney() >= chosenCard.getPurchasePrice()) {
            player.setMoney(player.getMoney() - chosenCard.getPurchasePrice());
            player.assignHouseCard(chosenCard);
            System.out.println("Congratulations! You have bought the house: " + chosenCard.getName());
        } else {
            System.out.println("Sorry, you don't have enough money to buy this house.");
        }

        deck.addCard(unchosenCard);
    }

    public void sellHouse(Player player, boolean isRedOnWheel) {
        int sellPrice = isRedOnWheel ? redSellPrice : blackSellPrice;

        if (player.getOwnedHouses().contains(this)) {
            player.getOwnedHouses().remove(this);
            player.setMoney(player.getMoney() + sellPrice);
            System.out.println("You have sold the house: " + getName() + " for " + sellPrice);
        } else {
            System.out.println("You don't own this house.");
        }
    }

    @Override
    public String toString() {
        return "HouseCard{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                '}';
    }
    // Method to get the house card deck (replace with your actual implementation)
    private Deck<HouseCard> getHouseCardDeck() {
        // Implement logic to get the house card deck
        return null; // Placeholder, replace with actual implementation
    }
}

