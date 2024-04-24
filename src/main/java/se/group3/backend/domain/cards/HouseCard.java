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
        HouseCard card1 = deck.drawCard();
        HouseCard card2 = deck.drawCard();

        System.out.println("Choose a house to buy:");
        System.out.println("1. " + card1);
        System.out.println("2. " + card2);

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
        int sellPrice = isRedOnWheel ? getRedSellPrice() : getBlackSellPrice();
        
        int index = -1;
        for (int i = 0; i < player.getOwnedHouses().size(); i++) {
            if (player.getOwnedHouses().get(i).equals(this)) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            player.getOwnedHouses().remove(index);
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
                "name='" + getName() + '\'' +
                '}';
    }
}
